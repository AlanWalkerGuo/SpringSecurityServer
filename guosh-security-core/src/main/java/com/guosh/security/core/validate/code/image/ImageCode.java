package com.guosh.security.core.validate.code.image;


import com.guosh.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

    //验证码图片
    private BufferedImage image;


    public ImageCode(BufferedImage image, String code, int exireIn) {
        super(code,exireIn);
        this.image = image;

    }

    public ImageCode(BufferedImage image, String code, LocalDateTime exireTime) {
        super(code,exireTime);
        this.image = image;

    }
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
