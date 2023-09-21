//package com.ggw.discount.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.ggw.discount.common.BaseContext;
//import com.ggw.discount.common.R;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
//public class LoginCheckFilter implements Filter {
//    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String requestURI = request.getRequestURI();
//        String[] urlPatterns = {"/user/signup", "/user/login", "/user/logout", "/back/**", "/front/**", "/common/**"};
//        boolean isAllowPassing = isAllowPassing(requestURI, urlPatterns);
//        if (isAllowPassing) {
//            log.info("Allow pass: {}", requestURI);
//            filterChain.doFilter(request, response);
//            return;
//        }
//        if (request.getSession().getAttribute("user") != null) {
//            log.info("Already logged in, userID is {}", request.getSession().getAttribute("user"));
//            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
//            filterChain.doFilter(request, response);
//            return;
//        }
//        log.info("Haven't logged in: {}", requestURI);
//        response.getWriter().write(JSON.toJSONString(R.error("Haven't login")));
//
//    }
//
//    private boolean isAllowPassing(String requestURI, String[] urlPatterns) {
//        for (String pattern : urlPatterns) {
//            boolean match = PATH_MATCHER.match(pattern, requestURI);
//            if (match) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
