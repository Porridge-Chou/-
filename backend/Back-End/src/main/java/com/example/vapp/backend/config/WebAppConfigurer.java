package com.example.vapp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置虚拟路径映射
 */

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/swiper/**").addResourceLocations("file:D:\\download\\");
        registry.addResourceHandler("/image/organization/**").addResourceLocations("file:E:\\volunteer\\organizationImgs\\");
        registry.addResourceHandler("/image/organizationDiploma/**").addResourceLocations("file:E:\\volunteer\\organizationDiplomaImgs\\");

    }
}
