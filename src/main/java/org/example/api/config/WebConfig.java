package org.example.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.example.api.interceptor.UserLoginInterceptor;
import org.example.api.josn.JacksonObjectMapper;
import org.example.api.properties.UploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Log4j2
public class WebConfig extends WebMvcConfigurationSupport {
    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Autowired
    private UploadProperties uploadProperties;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//       拦截除登录，注册之外
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login",
                                     "/user/signup",
                                     "/user/sendIdentifyingCode",
                                     "/user/isExistedForUserWithUserName/**",
                                     "/user/verifyIdentity");
    }
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper((ObjectMapper)new JacksonObjectMapper());
        converters.add(0, converter);
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").
                addResourceLocations("file:"+System.getProperty("user.dir")+"\\"+uploadProperties.getTargetDirectory()+"\\");
    }
}
