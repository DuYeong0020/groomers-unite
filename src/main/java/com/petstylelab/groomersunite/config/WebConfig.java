package com.petstylelab.groomersunite.config;

import com.petstylelab.groomersunite.common.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login",
                        "/users/email-verification",
                        "/users/recovery-verification",
                        "/users/email-verification/confirm",
                        "/users/recovery-verification/confirm",
                        "/users/password",
                        "/users/login-id");
    }
}
