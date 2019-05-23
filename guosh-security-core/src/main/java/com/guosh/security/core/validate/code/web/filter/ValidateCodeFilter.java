package com.guosh.security.core.validate.code.web.filter;

import com.guosh.security.core.properties.SecurityProperties;
import com.guosh.security.core.validate.code.image.ImageCode;
import com.guosh.security.core.validate.code.web.controller.ValidateCodeController;
import com.guosh.security.core.validate.code.web.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    //登陆失败的处理器
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    private Set<String>urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher=new AntPathMatcher();

    //初始化加载
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //把要拦截的分割转换数组
        if(StringUtils.isNotBlank(securityProperties.getCode().getImage().getUrl())){
            String[]configUrls=StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
            for (String configUrl:configUrls) {
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String codeInRequest=ServletRequestUtils.getStringParameter(request,"imageCode");
        //验证码不等于null再进行验证前端需要处理登陆失败再传验证码
        if(StringUtils.isNotBlank(codeInRequest)) {
            boolean action = false;
            //用antPathMatcher工具栏判断路径是否符合
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getServletPath())) {
                    action = true;
                }
            }

            //必须是登陆请求并且是post请求
            if (action) {
                try {
                    //校验验证码
                    validate(new ServletWebRequest(request));
                } catch (ValidateCodeException e) {
                    authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        //从session获取验证码
        ImageCode codeInSession=(ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
        //从请求里获取验证码
        String codeInRequest=ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");

        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if(codeInSession == null){
            throw new ValidateCodeException("验证码不存在");
        }
        //判断验证码时间是否过期
        if(codeInSession.isExpried()){
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已经过期");
        }
        //比对验证码
        if(!StringUtils.equalsIgnoreCase(codeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }


    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
