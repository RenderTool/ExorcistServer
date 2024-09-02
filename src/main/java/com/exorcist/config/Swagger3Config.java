package com.exorcist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Exorcist后台管理API")
                .description("这是一个游戏后台API服务页面")
                .version("0.0.1"));
    }
}