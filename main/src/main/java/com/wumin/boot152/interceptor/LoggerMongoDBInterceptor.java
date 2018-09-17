package com.wumin.boot152.interceptor;


import com.wumin.boot152.common.entity.User;
import com.wumin.boot152.common.entity.mongo.MongoDBLogger;
import com.wumin.boot152.common.util.HttpRequestUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 添加用户操作行为
 */
public class LoggerMongoDBInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log= LoggerFactory.getLogger(LoggerMongoDBInterceptor.class);
    private MongoTemplate mongoTemplate;

    public LoggerMongoDBInterceptor(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //如果方法上有@LoggerMongoDB这个注解，则进行下面的处理
            LoggerMongoDB annotation = method.getAnnotation(LoggerMongoDB.class);
            if (annotation != null) {
                User user = (User) SecurityUtils.getSubject().getPrincipal();
                MongoDBLogger mongoDBLogger = new MongoDBLogger();
                mongoDBLogger.setCreateTime(new Date());
                mongoDBLogger.setUserId(user.getId().toString());
                mongoDBLogger.setUserName(user.getUsername());
                mongoDBLogger.setIp(HttpRequestUtil.getClintIp(request));
                mongoDBLogger.setUrl(HttpRequestUtil.getUrl(request));
                mongoDBLogger.setDescription(annotation.value() + "--" + annotation.description());
                mongoTemplate.save(mongoDBLogger);
                log.info("注解拦截成功，保存数据到mongo");
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}