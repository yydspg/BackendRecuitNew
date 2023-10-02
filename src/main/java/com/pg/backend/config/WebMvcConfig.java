package com.pg.backend.config;

import com.pg.backend.interceptor.JwtInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Slf4j
@Configuration
public class WebMvcConfig {
    @Resource
    private JwtInterceptor jwtInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("Start registering  custom interceptor...");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }
}
