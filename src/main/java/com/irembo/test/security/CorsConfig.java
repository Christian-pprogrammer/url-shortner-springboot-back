package com.irembo.test.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Allow all origins (you can restrict it to specific domains)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowedHeaders("*"); // Allow all headers
    }
}
