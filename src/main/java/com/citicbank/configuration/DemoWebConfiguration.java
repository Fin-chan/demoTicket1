package com.citicbank.configuration;

import com.citicbank.interceptor.LoginRequiredInterceptor;
import com.citicbank.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by FIN on 2016/12/10.
 */
@Component
public class DemoWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);//登录拦截器
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/like");//权限拦截器
        super.addInterceptors(registry);
    }
}
