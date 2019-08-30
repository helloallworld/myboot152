//package com.wumin.boot152.nginxTest.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@WebFilter(filterName = "myFilter" ,urlPatterns = "/*")
//public class MyFliter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("test Filter");
//        filterChain.doFilter(servletRequest,servletResponse);
//        System.out.println("after");
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
