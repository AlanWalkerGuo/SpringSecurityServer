package com.guosh.demo.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //System.out.println("TimeFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("TimeFilter doFilter");
        long start=new Date().getTime();
        //是否继续执行
        filterChain.doFilter(servletRequest,servletResponse);
        //System.out.println("耗时"+(new Date().getTime()-start));
    }

    @Override
    public void destroy() {
       // System.out.println("TimeFilter destroy");
    }
}
