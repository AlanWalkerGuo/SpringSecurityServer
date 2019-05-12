package com.guosh.security.core.properties;


public class ImageCodeProperties {
    //默认验证码宽度
    private int width = 67;
    //默认验证码高度
    private int height = 23;
    //默认验证码位数
    private int length = 4;
    //默认验证码失效时间
    private int expreIn= 60;
    //哪些url地址需要验证码拦截
    private String url;
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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
