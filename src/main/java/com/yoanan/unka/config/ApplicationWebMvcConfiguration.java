package com.yoanan.unka.config;

import com.yoanan.unka.web.interceptors.LogInterceptor;
import com.yoanan.unka.web.interceptors.GreetingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;
    private final GreetingInterceptor greetingInterceptor;


    public ApplicationWebMvcConfiguration(LogInterceptor logInterceptor, GreetingInterceptor greetingInterceptor) {
        this.logInterceptor = logInterceptor;
        this.greetingInterceptor = greetingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/courses/**", "/profile");
        registry.addInterceptor(greetingInterceptor).addPathPatterns("/home");
    }


}


