package com.guosh.security.core.social.weixin.api;



/**
 * @Author: Guosh
 * @Date: 2019-06-03 09:46
 */
public interface Weixin {
    /**
     * 获取用户信息
     * @return
     */
    public WeixinUserInfo getUserInfo(String openId);
}
