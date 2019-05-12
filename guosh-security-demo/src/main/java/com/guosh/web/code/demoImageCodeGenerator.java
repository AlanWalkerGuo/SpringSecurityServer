package com.guosh.web.code;

import com.guosh.security.core.validate.code.ImageCode;
import com.guosh.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 从类可以重写生成验证码的逻辑替换默认逻辑
 */
//@Component(value = "imageCodeGenerator")
public class demoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.printf("自定义验证码");
        return null;
    }
}