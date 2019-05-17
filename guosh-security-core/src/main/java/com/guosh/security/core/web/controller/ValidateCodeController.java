package com.guosh.security.core.web.controller;


import com.guosh.security.core.validate.code.image.ImageCode;
import com.guosh.security.core.validate.code.ValidateCode;
import com.guosh.security.core.validate.code.ValidateCodeGenerator;
import com.guosh.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@RestController
public class ValidateCodeController {


    public static final String SESSION_KEY="SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();


    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    //发送短信
    @Autowired
    private SmsCodeSender smsCodeSender;

    //图形验证码
    @RequestMapping(value = "/code/image",method = RequestMethod.GET)
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码对象
        ImageCode imageCode= (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        //将ImageCode对象保存在session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        //返回到页面
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    //短信验证码
    @RequestMapping(value = "/code/sms",method = RequestMethod.GET)
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        //生成验证码对象
        ValidateCode validateCode=smsCodeGenerator.generate(new ServletWebRequest(request));
        //将validateCode对象保存在session中
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,validateCode);
        String mobile= ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        //发送短信
        smsCodeSender.send(mobile,validateCode.getCode());

    }




}
