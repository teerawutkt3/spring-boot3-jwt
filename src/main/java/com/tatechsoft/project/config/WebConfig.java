package com.tatechsoft.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${allow.origin.host1}")
    private String allowOriginHost1;

    @Value("${allow.origin.host2}")
    private String allowOriginHost2;

    @Value("${allow.origin.host3}")
    private String allowOriginHost3;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowOriginHost1, allowOriginHost2, allowOriginHost3)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}