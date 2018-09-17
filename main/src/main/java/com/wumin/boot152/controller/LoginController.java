package com.wumin.boot152.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    /**
     * @Title: login
     * @Description: TODO(用户登录)
     * @param @param request
     * @param @param username 用户名
     * @param @param password 密码
     * @param @return 参数
     * @return ResultData 返回类型
     */
//    @PostMapping("/login")
//    public ResultData login(HttpServletRequest request, String username, String password) {
//        ResultData resultData = new ResultData();
//        resultData.setStatus(200);
//        resultData.setMessage("已用登陆!");
//        return resultData;
//    }
//    @GetMapping("/login")
//    public ResultData loginGet(HttpServletRequest request, String username, String password) {
//        ResultData resultData = new ResultData();
//        resultData.setStatus(401);
//        resultData.setMessage("未登录!");
//        return resultData;
//    }
@PostMapping("/login")
public String login(HttpServletRequest request, String username, String password) {
    String resultData = "已登陆";
    return resultData;
}
    @GetMapping("/login")
    public String loginGet(HttpServletRequest request, String username, String password) {
        String resultData = "未登录!";
        return resultData;
    }
}
