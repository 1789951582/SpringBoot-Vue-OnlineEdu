package com.xh.online_edu.manager;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.ResourceMapper;
import com.xh.online_edu.pojo.po.EduResource;
import com.xh.online_edu.utils.FileConversionUtils;
import com.xh.online_edu.utils.RedisUtils;
import com.xh.online_edu.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

@Slf4j
@Component
public class UploadManager {
    private static final Set<String> OFFICE_EXTS = Set.of(".ppt", ".pptx", ".doc", ".docx");
    private static final Set<String> IMAGE_EXTS = Set.of(".jpg", ".png", ".webp");

    //nginxUrl路径
    @Value("${nginx.url}")
    private String url;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    FileConversionUtils fileConversionUtils;
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    RedisUtils redisUtils;

    @Async
    public void UploadHandle(File tempFile, EduResource resource, String fileExt) {
        Long resourceId=resource.getResourceId();
        try{
            // 根据文件类型处理
            if (OFFICE_EXTS.contains(fileExt)) {
                officeFileHandle(tempFile,resourceId);
                resource.setResourceUrl(url+'/'+resourceId+".pdf");
                resource.setTypeId(2);
            } else if (".mp4".equalsIgnoreCase(fileExt)) {
                otherFileHandle(tempFile,resourceId);
                resource.setResourceUrl(url+'/'+resourceId+fileExt);
                resource.setTypeId(1);
            }else if (IMAGE_EXTS.contains(fileExt)){
                otherFileHandle(tempFile,resourceId);
                resource.setResourceUrl(url+'/'+resourceId+fileExt);
                resource.setTypeId(4);
            } else {
                errHandle("不支持的文件格式: " + fileExt,resourceId);
            }
        }catch (StatusFailException e){
            errHandle(e.getMessage(),resourceId);
            return;
        }
        resourceMapper.insert(resource);
        redisUtils.set("saveIsOk:" + resourceId,Result.successResponse(true),300);
    }

    private void officeFileHandle(File tempFile,Long fileId) throws StatusFailException {
        //对于office文件，转换为pdf文件后移动到静态文件夹
        File convertFile=fileConversionUtils.convertOfficeToPdf(tempFile,fileId);
        if (convertFile == null || !convertFile.exists()) {
            log.error("找不到转换后的文件"+convertFile.getAbsolutePath());
            errHandle("找不到转换后的文件",fileId);
            return;
        }
        fileUtils.moveFileToStaticDir(convertFile);
        tempFile.delete();
    }

    private void otherFileHandle(File tempFile,Long fileId) throws StatusFailException {
        //其他文件则直接移动到静态文件夹
        fileUtils.moveFileToStaticDir(tempFile);
    }

    private void errHandle(String message,Long fileId){
        redisUtils.set("saveIsOk:"+fileId, Result.FailResponse(message),300);
    }

}
