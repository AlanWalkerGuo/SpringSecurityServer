package com.guosh.security.app.social;

import com.guosh.security.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: Guosh
 * @Date: 2019-06-20 15:02
 */
//@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
    //当第三方登陆用户需要注册的时候不再跳注册页面
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "sociaSecurityConfig")) {
            MySpringSocialConfigurer configurer = (MySpringSocialConfigurer)bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
