package com.yuesheng.pm.config;

import com.yuesheng.pm.interceptor.ApiInterceptor;
import com.yuesheng.pm.interceptor.WxPublicAccountInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public ApiInterceptor getApi(){
        return new ApiInterceptor();
    }

    @Bean
    public WxPublicAccountInterceptor getWxApi(){
        return new WxPublicAccountInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(getApi())
                .excludePathPatterns("/api/**");

        registry.addInterceptor(getWxApi())
                .addPathPatterns("/**");

    }
}
