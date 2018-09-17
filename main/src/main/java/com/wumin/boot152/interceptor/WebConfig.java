package com.wumin.boot152.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    @Resource
    private MongoTemplate mongoTemplate;

    private ApplicationContext applicationContext;

    public WebConfig() {
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //暂不使用
      /*  registry.addResourceHandler("/static*//**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
         registry.addResourceHandler("/templates*//**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

         super.addResourceHandlers(registry);  */
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：/myCode/**都拦截判断,拦截后用LoggerMongoDBInterceptor中的处理方式处理
        registry.addInterceptor(new LoggerMongoDBInterceptor(mongoTemplate)).addPathPatterns("/myCode/**");

        super.addInterceptors(registry);
    }

}