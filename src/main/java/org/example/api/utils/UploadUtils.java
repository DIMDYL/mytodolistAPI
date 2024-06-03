package org.example.api.utils;

import lombok.Data;
import org.example.api.properties.UploadProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
public class UploadUtils {

    public UploadUtils(UploadProperties properties){
        this.uploadProperties = properties;
    }
    private UploadProperties uploadProperties;

    /**
     * 上传文件
     *     ①存储位置:   project/img/
     *     ②文件名:    4为随机数+完整文件名
     * @param  file
     * @return 文件名
     * @throws IOException
     */
    public String upload(MultipartFile file) throws IOException {
        String fileName = ((int)Math.floor((Math.random()*1000)))+file.getOriginalFilename();
        String targetPath = System.getProperty("user.dir")+"\\"+uploadProperties.getTargetDirectory()+"\\"+fileName;
        FileOutputStream outputStream = new FileOutputStream(targetPath);

        InputStream inputStream = file.getInputStream();
        byte[] data = new byte[1024];
        int l = 0;

        while((l = inputStream.read(data)) != -1){
            outputStream.write(data,0,l);
        }
        inputStream.close();
        outputStream.close();

        return fileName;
    }
}
