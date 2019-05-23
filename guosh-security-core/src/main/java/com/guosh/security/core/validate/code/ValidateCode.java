package com.guosh.security.core.validate.code;


import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidateCode implements Serializable {
    //随机数
    private String code;
    //失效时间
    private LocalDateTime expireTime;

    public ValidateCode(String code, int exireIn) {
        this.code = code;
        //当前时间加上过期秒数
        this.expireTime = LocalDateTime.now().plusSeconds(exireIn);
    }

    public ValidateCode(String code, LocalDateTime exireTime) {
        this.code = code;
        //当前时间加上过期秒数
        this.expireTime = exireTime;

    }
    //判断时间是否过期
    public boolean isExpried(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
