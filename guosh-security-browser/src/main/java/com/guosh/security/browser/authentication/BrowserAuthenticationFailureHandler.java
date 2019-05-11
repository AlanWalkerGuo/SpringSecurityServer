package com.guosh.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guosh.security.core.properties.LoginType;
import com.guosh.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    //自定义登陆失败
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("登陆失败");
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            //401状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            //转json字符串返回
            response.getWriter().write(objectMapper.writeValueAsString(e));
        }else{
            super.onAuthenticationFailure(request,response,e);
        }

    }
}
