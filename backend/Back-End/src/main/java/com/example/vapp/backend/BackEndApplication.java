package com.example.vapp.backend;

import com.example.vapp.backend.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.vapp.backend.mapper")
public class BackEndApplication extends SpringBootServletInitializer {

    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BackEndApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
