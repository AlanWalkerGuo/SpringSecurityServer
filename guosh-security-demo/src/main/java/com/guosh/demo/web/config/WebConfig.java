package com.guosh.demo.web.config;

import com.guosh.demo.web.filter.TimeFilter;
import com.guosh.demo.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TimeInterceptor timeInterceptor;

    //过滤器
    @Bean
    public FilterRegistrationBean timeFilterRegistration(){
        FilterRegistrationBean registration=new FilterRegistrationBean();
        TimeFilter timeFilter=new TimeFilter();
        registration.setFilter(timeFilter);
        //filter作用的地址
        List<String>urls=new ArrayList<>();
        urls.add("/user/*");
        registration.setUrlPatterns(urls);
        return  registration;
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }
}
