package com.guosh.security.core.validate.code;


import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidateCode implements Serializable {
    //随机数
    private String code;
    //失效时间
    private LocalDateTime exireTime;

    public ValidateCode(String code, int exireIn) {
        this.code = code;
        //当前时间加上过期秒数
        this.exireTime = LocalDateTime.now().plusSeconds(exireIn);
    }

    public ValidateCode(String code, LocalDateTime exireTime) {
        this.code = code;
        //当前时间加上过期秒数
        this.exireTime = exireTime;

    }
    //判断时间是否过期
    public boolean isExpried(){
        return LocalDateTime.now().isAfter(exireTime);
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
