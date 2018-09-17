package com.wumin.boot152.common.web;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.wumin.boot152.common.entity.query.ResultData;
import com.wumin.boot152.common.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设置Exception的处理方式
 */
@ControllerAdvice
public class DefaultExceptionResolver extends ExceptionHandlerExceptionResolver {

    public static final Logger logger = LoggerFactory.getLogger(DefaultExceptionResolver.class);

    /**
     * 判断一个请求是不是ajax的
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || StringUtils.contains(request.getHeader("Content-Type"),
                MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 业务异常处理方法
     *
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    String handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultData error = new ResultData();
        if (exception instanceof ServiceException) { // 如果是业务异常日志输出异常编码与异常信息
            Integer code = ((ServiceException) exception).getErrorCode();
            error.setStatus(code == null ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : code);
            logger.warn("业务异常，异常编码：{}，异常信息：{}", ((ServiceException) exception).getErrorCode(), exception.getMessage());
        } else { // 否则为http的500
            error.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("handleException: ", exception);
        }
        if (StringUtils.isNoneBlank(exception.getMessage())) {
            error.setMessage(exception.getMessage());
        } else {
            error.setMessage("操作异常,请重新操作!");
        }
        request.setAttribute("exception", exception.getClass().toString());
        request.setAttribute("message", exception.getMessage());
        return resultHandler(request, response, error);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    String handleUnauthenticatedException(UnauthenticatedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultData error = new ResultData();
        error.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        error.setData(exception.getMessage());
        request.setAttribute("exception", exception.getClass().toString());
        request.setAttribute("message", exception.getMessage());
        return resultHandler(request, response, error, "login");
    }

    private String resultHandler(HttpServletRequest request, HttpServletResponse response, ResultData error, String... page) throws IOException {
        if (isAjaxRequest(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(error));
            out.flush();
            out.close();
            return null;
        }
        if (ArrayUtils.isNotEmpty(page)) {
            return page[0];
        } else {
            return "error/500";
        }
    }

    /**
     * 未授权异常处理
     *
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(UnauthorizedException.class)
    String handleUnauthorizedException(UnauthorizedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultData error = new ResultData();
        // 直接返回http状态401
        error.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        error.setData("此操作无权限！");
        request.setAttribute("exception", exception.getClass().getName());
        request.setAttribute("message", exception.getMessage());
        return resultHandler(request, response, error);
    }

    /**
     * 如果传入参数有date类型的会进行注入
     * @param binder
     */
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String text) throws IllegalArgumentException {
                // try to parse date time format first, then date format
                DateFormat longDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                Date value = null;
                try {
                    value = longDf.parse(text);
                } catch (ParseException e) {
                    if (value == null) {
                        DateFormat shortDf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            value = shortDf.parse(text);
                        } catch (ParseException e1) {
                            logger.warn(String.format("时间类型参数绑定失败[%s]", text), e1);
                        }
                    }
                }

                this.setValue(value);
            }

            public String getAsText() {
                DateFormat longDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                return longDf.format((Date) this.getValue());
            }
        });
    }

}
