package com.guosh.security.core.validate.code;


import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {

    //验证码图片
    private BufferedImage image;
    //随机数
    private String code;
    //失效时间
    private LocalDateTime exireTime;

    public ImageCode(BufferedImage image, String code, int exireIn) {
        this.image = image;
        this.code = code;
        //当前时间加上过期秒数
        this.exireTime = LocalDateTime.now().plusSeconds(exireIn);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExireTime() {
        return exireTime;
    }

    public void setExireTime(LocalDateTime exireTime) {
        this.exireTime = exireTime;
    }
}
