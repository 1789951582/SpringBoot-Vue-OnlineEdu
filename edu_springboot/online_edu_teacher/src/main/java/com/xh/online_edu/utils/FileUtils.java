package com.xh.online_edu.utils;

import com.xh.online_edu.common.exception.StatusFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class FileUtils {
    //nginx静态文件夹
    @Value("${nginx.staticPath}")
    private String staticPath;
    // 临时文件存储目录
    @Value("${tempPath}")
    private String tempPath;

    //存储方法
    public File saveFile(MultipartFile raw,String fileName) throws StatusFailException {
        try {
            File dir = new File(tempPath);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file=new File(tempPath+File.separator+fileName);
            raw.transferTo(file);
            return file;
        }catch (IOException e){
            throw new StatusFailException("保存文件失败");
        }
    }

    //移动方法
    public void moveFileToStaticDir(File sourceFile) throws StatusFailException {
        File targetDir = new File(staticPath);
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            log.error("无法创建静态文件目录"+sourceFile);
            throw new StatusFailException("无法创建静态文件目录");
        }
        File file=new File(targetDir, sourceFile.getName());
        try{
            Files.move(sourceFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            log.error("执行移动文件失败"+e.getMessage());
            throw new StatusFailException("执行移动文件失败,请稍后重试");
        }
    }

    //删除方法
    @Async
    public void deleteFile(Long fileId) throws StatusFailException {

        File staticDir = new File(staticPath);
        if (!staticDir.exists() || !staticDir.isDirectory()) {
            log.error("静态文件夹不存在：{}", staticPath);
        }

        // 构建匹配模式：fileId.任意后缀
        final String pattern = "^" + fileId + "\\..+$";
        File[] matchedFiles = staticDir.listFiles((dir, name) -> name.matches(pattern));

        if (matchedFiles == null || matchedFiles.length == 0) {
            log.error("未找到对应文件：fileId={}", fileId);
        }

        for (File file : matchedFiles) {
            if (!file.delete()) {
                log.error("文件删除失败：{}", file.getAbsolutePath());
            }
        }
    }
}

//    @Autowired
//    FileConversionUtils fileConversionUtils;

//    public File saveOfficeFile(MultipartFile raw, Long fileId,String fileExtension) throws StatusFailException, IOException {
//        // 1. 保存上传的临时文件
//        File originalFile=saveFile(raw,tempPath,fileId+fileExtension);
//        File convertFile=fileConversionUtils.convertOfficeToPdf(originalFile,fileId);
//        originalFile.delete();
//        return moveFile(convertFile);
//    }
//
//    public File saveOtherFile(MultipartFile raw,Long fileId,String fileExtension) throws IOException {
//        return saveFile(raw,staticPath,fileId+fileExtension);
//    }



//        if (!allDeleted) {
//            throw new StatusFailException("部分文件删除失败");
//        }
//    }


//    public void saveOtherFile(MultipartFile raw,Long fileId,String fileExtension) throws StatusFailException{
//        File dir = new File(staticPath);
//        if (!dir.exists()){
//            dir.mkdir();
//        }
//        File file=new File(staticPath+File.separator+fileId+fileExtension);
//        try {
//            raw.transferTo(file);
//        }catch (Exception e) {
//            throw new StatusFailException("存储文件失败");
//        }
//        redisUtils.set("saveIsOk"+fileId,true,300);
//    }
