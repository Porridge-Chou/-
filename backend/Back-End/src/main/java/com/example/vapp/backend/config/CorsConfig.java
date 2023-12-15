package com.example.vapp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOriginPatterns("*"). //允许跨域的域名，可以用*表示允许任何域名使用
//                allowedOrigins("*").    //在Springboot2.4对应Spring5.3后在设置allowCredentials(true)的基础上不能直接使用通配符设置allowedOrigins，而是需要指定特定的URL。如果需要设置通配符，需要通过allowedOriginPatterns指定
        allowedMethods("GET", "POST", "DELETE", "PUT"). //允许任何方法（post、get等）
                allowedHeaders("*"). //允许任何请求头
                allowCredentials(true). //带上cookie信息
                exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果

    }
}
