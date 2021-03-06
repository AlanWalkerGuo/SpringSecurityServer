package com.guosh.security.core.properties.social;

/**
 * @Author: Guosh
 * @Date: 2019-05-30 14:36
 */
public class SocialProperties {

    //回调路径
    private String filtertProcessesUrl="/auth";

    //QQ登陆
    private QQProperties qq=new QQProperties();
    //微信登陆
    private WinxinProperties weixin=new WinxinProperties();


    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFiltertProcessesUrl() {
        return filtertProcessesUrl;
    }

    public void setFiltertProcessesUrl(String filtertProcessesUrl) {
        this.filtertProcessesUrl = filtertProcessesUrl;
    }

    public WinxinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WinxinProperties weixin) {
        this.weixin = weixin;
    }
}
