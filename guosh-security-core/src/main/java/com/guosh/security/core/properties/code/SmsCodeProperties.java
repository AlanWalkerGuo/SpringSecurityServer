package com.guosh.security.core.properties.code;


public class SmsCodeProperties {

    //默认验证码位数
    private int length = 6;
    //默认验证码失效时间
    private int expreIn= 120;
    //哪些url地址需要验证码拦截
    private String url;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpreIn() {
        return expreIn;
    }

    public void setExpreIn(int expreIn) {
        this.expreIn = expreIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
