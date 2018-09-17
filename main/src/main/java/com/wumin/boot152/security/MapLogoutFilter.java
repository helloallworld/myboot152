package com.wumin.boot152.security;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class MapLogoutFilter extends LogoutFilter {
    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
//        System.out.println("==============logout================");
        if (isAjaxRequest((HttpServletRequest) request)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject j=new JSONObject();
            j.put("status","200");
            j.put("message","退出成功!");
            j.put("success",true);
            out.println(j.toJSONString());
            out.flush();
            out.close();
        } else {
            super.issueRedirect(request, response, redirectUrl);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
