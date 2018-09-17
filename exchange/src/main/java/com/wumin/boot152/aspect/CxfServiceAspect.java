package com.wumin.boot152.aspect;

import com.wumin.boot152.common.entity.DataMongoDBLogger;
import com.wumin.boot152.common.entity.query.ResultData;
import com.wumin.boot152.common.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建  调用其他人webService接口时的日志记录,存入mongo
 */
@Component
@Aspect
@Order(1)
public class CxfServiceAspect {
    @Resource
    private MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CxfServiceAspect.class);

    @Around("(execution(* com.wumin.exchange.cxf.service.*.*(..)))")
    public Object aroundServiceMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        DataMongoDBLogger dbLogger = new DataMongoDBLogger();
        try {
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
            Signature s = proceedingJoinPoint.getSignature();
            MethodSignature ms = (MethodSignature) s;
            Method m = ms.getMethod();
            clazzName = clazzName.substring(clazzName.lastIndexOf('.') + 1) + "." + m.getName();
            dbLogger.setDescription(clazzName);

            Object[] param = proceedingJoinPoint.getArgs();
            if (param.length > 1) {
                Map<String, Object> map = new HashMap<>();
                for (Object object : param) {
                    String name = object.getClass().getName();
                    map.put(name.substring(name.lastIndexOf('.') + 1), object);
                }
                dbLogger.setData(map);
            } else {
                dbLogger.setData(param[0]);
            }

            dbLogger.setCreateTime(new Date());
            dbLogger.setType("1");
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            String message = "操作失败，请确认传入正确的参数！";
            if (e instanceof ServiceException) {
                logger.error(e.getMessage(), e);
                message = e.getMessage();
            } else {
                logger.error("WebService接口异常", e);
            }
            dbLogger.setResult(message);
            mongoTemplate.save(dbLogger);

            ResultData resultData = new ResultData();
            resultData.setStatus(500);
            resultData.setData(message);
            return resultData;
        }
        dbLogger.setResult(result);
        mongoTemplate.save(dbLogger);
        return result;
    }

}