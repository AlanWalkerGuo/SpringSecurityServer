package com.guosh.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Author: Guosh
 * @Date: 2019-05-30 16:10
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {
    private String filtertProcessesUrl;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public MySpringSocialConfigurer(String filtertProcessesUrl) {
        this.filtertProcessesUrl = filtertProcessesUrl;
    }

    /**
     * 第三方认证回调路径
     * @param object
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter= (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filtertProcessesUrl);
        //App opnId登陆
        if (socialAuthenticationFilterPostProcessor != null) {
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }
}
