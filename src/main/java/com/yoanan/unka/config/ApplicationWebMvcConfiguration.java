package com.yoanan.unka.config;

import com.yoanan.unka.web.interceptors.LogInterceptor;
import com.yoanan.unka.web.interceptors.HomeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;
    private final HomeInterceptor homeInterceptor;


    public ApplicationWebMvcConfiguration(LogInterceptor logInterceptor, HomeInterceptor homeInterceptor) {
        this.logInterceptor = logInterceptor;
        this.homeInterceptor = homeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/courses/**", "/profile");
        registry.addInterceptor(homeInterceptor).addPathPatterns("/home");
    }


}


