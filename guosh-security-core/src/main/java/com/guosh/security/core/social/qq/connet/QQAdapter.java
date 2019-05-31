package com.guosh.security.core.social.qq.connet;

import com.guosh.security.core.social.qq.api.QQ;
import com.guosh.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Author: Guosh
 * @Date: 2019-05-29 18:06
 */
public class QQAdapter implements ApiAdapter<QQ> {

    //测试Api是否可用
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo=api.getUserInfo();
        //名称
        values.setDisplayName(userInfo.getNickname());
        //头像
        values.setImageUrl(userInfo.getFigureurl_qq());
        //主页
        values.setProfileUrl(null);
        //openId
        values.setProviderUserId(userInfo.getOpenId());

    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {
        //发送消息更新动态
    }
}
