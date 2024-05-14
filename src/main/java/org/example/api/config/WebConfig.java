package org.example.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.interceptor.UserLoginInterceptor;
import org.example.api.josn.JacksonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Autowired
    private UserLoginInterceptor userLoginInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**");
    }
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper((ObjectMapper)new JacksonObjectMapper());
        converters.add(0, converter);
    }
}
