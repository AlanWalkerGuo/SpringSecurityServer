package com.guosh.security.core.social.qq.api;

import java.io.IOException;

/**
 * @Author: Guosh
 * @Date: 2019-05-29 11:42
 */
public interface QQ {

    /**
     * 获取用户信息
     * @return
     */
    public QQUserInfo getUserInfo() throws IOException;
}
