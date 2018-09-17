package com.wumin.boot152.controller;

import com.wumin.boot152.common.entity.User;
import com.wumin.boot152.common.entity.query.QueryParams;
import com.wumin.boot152.common.entity.query.ResultData;
import com.wumin.boot152.common.redis.template.RedisClientTemplate;
import com.wumin.boot152.interceptor.LoggerMongoDB;
import com.wumin.boot152.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.management.Query;

@RestController
@RequestMapping("myCode")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisClientTemplate redisClientTemplate;
    @GetMapping("getUser")
    public ResultData getUser(QueryParams params){
       return new ResultData(userService.getUserByUsername((String) params.getQuery().get("username")));
    }
    @LoggerMongoDB("操作getUser2")
    @PostMapping("getUser2")
    public ResultData getUser2(User user){
        redisClientTemplate.set("boot152:getUser2","value打开了房间里的",1);
       return new ResultData(userService.getUserByUsername(user.getUsername()));
    }
}
