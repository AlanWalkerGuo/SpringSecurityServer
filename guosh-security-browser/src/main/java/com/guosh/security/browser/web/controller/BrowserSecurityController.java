package com.guosh.security.browser.web.controller;

import com.guosh.security.browser.domain.SimpleResponse;
import com.guosh.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    private RequestCache requestCache=new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 需要身份认证时跳转地址
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest=requestCache.getRequest(request,response);
        if(savedRequest!=null){
            //查询上个页面地址
            String targetUrl = savedRequest.getRedirectUrl();
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                logger.info("跳转登陆页面{}",securityProperties.getBrowser().getLoginPage());
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问服务需要身份认证,请引导用户到登陆页");
    }
}
