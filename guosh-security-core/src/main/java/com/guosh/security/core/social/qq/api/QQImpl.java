package com.guosh.security.core.social.qq.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @Author: Guosh
 * @Date: 2019-05-29 13:25
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    private Logger logger= LoggerFactory.getLogger(getClass());

    /**
     * 获取openId
     */
    private static final String URL_GET_OPENID="https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取用户信息
     */
    private static final String URL_GET_USERINFO="https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";


    //QQ互联应用id
    private String appId;

    //QQ号码
    private String openId;

    private ObjectMapper objectMapper=new ObjectMapper();


    public QQImpl(String accessToken,String appId){
        /**
         * TokenStrategy.ACCESS_TOKEN_PARAMETER 表示将accessToken放到传入参数里面
         */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId=appId;

        String url=String.format(URL_GET_OPENID,accessToken);
        String result=getRestTemplate().getForObject(url,String.class);
        logger.info(result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        logger.info("openid"+openId);

    }

    /**
     * 获取用户信息
     * @return
     * @throws IOException
     */
    @Override
    public QQUserInfo getUserInfo()  {
        String url=String.format(URL_GET_USERINFO,appId,openId);
        String result=getRestTemplate().getForObject(url,String.class);
        logger.info(result);
        QQUserInfo qqUserInfo=null;
        try {
             qqUserInfo=objectMapper.readValue(result,QQUserInfo.class);
             qqUserInfo.setOpenId(openId);
             return qqUserInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败");
        }

    }
}
