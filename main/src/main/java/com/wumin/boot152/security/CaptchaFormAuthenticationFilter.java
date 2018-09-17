package com.wumin.boot152.security;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.wumin.boot152.common.entity.Permissions;
import com.wumin.boot152.common.entity.User;
import com.wumin.boot152.service.PermissionsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * shiro 的filter 做一些登陆成功或失败的数据处理
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    private PermissionsService permissionsService;

    public CaptchaFormAuthenticationFilter(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }
    /**
     * 所有请求都会经过的方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                //调用认证，不覆写的话默认的token是AuthenticationToken，并在这里面进行subject.login方法
                //建议覆写
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                // allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                        + "Authentication url [" + getLoginUrl() + "]");
            }
            if (!isAjaxRequest((HttpServletRequest) request)) {// 不是ajax请求
                saveRequestAndRedirectToLogin(request, response);
            } else {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                HttpServletResponse r = (HttpServletResponse) response;
                r.setStatus(HttpStatus.OK.value());
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("{\"success\":false,\"message\":\"login\",\"status\":401}");
                out.flush();
                out.close();
            }
            return false;


        }
    }
    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /*
     *  主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject, ServletRequest request, ServletResponse response)
            throws Exception {
//在这里写登入成功的处理，如需要在登陆成功后返回的数据
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        User user = (User) subject.getPrincipal();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //获取权限
        initPermissions(user);
        if (!isAjaxRequest((HttpServletRequest) request)) {// 不是ajax请求
            issueSuccessRedirect(request, response);
        } else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            JSONObject j = new JSONObject();
            initJson(subject, user, j);
            out.println(j.toJSONString());
            out.flush();
            out.close();
        }
        return false;
    }

    void initPermissions(User user) {
        Session session = SecurityUtils.getSubject().getSession();
        List<Permissions> permissionsList = permissionsService.getPermissionById(user.getId());
        session.setAttribute("permissions", permissionsList);
    }

    void initJson(Subject subject, User user, JSONObject j) {
        j.put("status", "200");
        j.put("message", "登入成功");
        j.put("success", true);
        user.setPassword(null);
        user.setSalt(null);
        Session session = subject.getSession();
        List<Permissions> list = (List<Permissions>) session.getAttribute("permissions");
        j.put("permissions", list);
        j.put("user", user);

    }

    /**
     * 主要是处理登入失败的方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
                                     AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
            if (!isAjaxRequest((HttpServletRequest) request)) {// 不是ajax请求
                setFailureAttribute(request, e);
                return true;
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter out = response.getWriter();
                String message = e.getClass().getSimpleName();
                String res = "";
                if (e.getCause() != null) {
                    res = e.getCause().getClass().getSimpleName();
                }
                if ("IncorrectCredentialsException".equals(message)) {
                    out.println("{\"success\":false,\"message\":\"密码错误\",\"status\":500}");
                } else if ("UnknownAccountException".equals(message)) {
                    out.println("{\"success\":false,\"message\":\"账号不存在\",\"status\":500}");
                } else if ("MoreUsersException".equals(message) || res.equals("MoreUsersException")) {
                    out.println("{\"success\":false,\"message\":\"多个用户名,请联系重置\",\"status\":500}");
                } else if ("IncorrectCaptchaException".equals(message)) {
                    out.println("{\"success\":false,\"message\":\"验证码不正确\",\"status\":500}");
                } else {
                    out.println("{\"success\":false,\"message\":\"未知错误\",\"status\":500}");
                }
                out.flush();
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return false;
        }
}

