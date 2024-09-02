package com.exorcist.config;

import com.exorcist.interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        "/swagger-ui/**",          // 排除 Swagger UI 相关路径
                        "/webjars/**",             // 排除 WebJars 相关路径
                        "/doc.html/**",            // 排除文档相关路径
                        "/v2/**",                  // 排除 Swagger 2 相关路径
                        "/v3/**",                  // 排除 Swagger 3 相关路径
                        "/swagger-ui.html/**",     // 排除 Swagger UI 旧版路径
                        "/api/user/login",         // 排除 用户登录接口
                        "/api/version/**",         // 排除 版本获取信息
                        "/api/user/register",      // 排除 用户注册接口
                        "/templates/**",
                        "/favicon.ico",
                        "/api/version/getLatestVersion"
                );
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
