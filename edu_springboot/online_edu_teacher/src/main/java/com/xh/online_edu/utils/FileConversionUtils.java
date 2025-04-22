package com.xh.online_edu.utils;

import com.xh.online_edu.common.exception.StatusFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileConversionUtils {

    // 临时文件存储目录
    @Value("${tempPath}")
    private String tempPath;

    public File convertOfficeToPdf(File originalFile,Long fileId) throws StatusFailException {
//        准备输出目录
        File outputDir = new File(tempPath + File.separator + "pdf_output");
        if (!outputDir.exists()) outputDir.mkdirs();
//        构建命令行指令
        try{
            int exitCode = new ProcessBuilder(
                    "soffice",
                    "--headless",
                    "--convert-to", "pdf",
                    originalFile.getAbsolutePath(),
                    "--outdir", outputDir.getAbsolutePath()
            )
                    .inheritIO()
                    .start()
                    .waitFor();

            if (exitCode != 0) {
                log.error("fileId:"+fileId+"文件转换失败，错误码: " + exitCode);
                throw new StatusFailException("文件转换失败");
            }
            return new File(outputDir,fileId+".pdf");
        }catch (IOException | InterruptedException e){
            log.error("fileId:"+fileId+",文件转换处理失败"+e.getMessage());
            throw new StatusFailException("文件转换处理失败");
        }
    }
}