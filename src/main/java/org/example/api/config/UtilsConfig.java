package org.example.api.config;

import org.example.api.properties.UploadProperties;
import org.example.api.utils.UploadUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {


    /**
     * 上传文件.工具类
     * @param properties
     * @return
     */
    @Bean
    public UploadUtils uploadImageUtils(UploadProperties properties){
        return new UploadUtils(properties);
    }
}
