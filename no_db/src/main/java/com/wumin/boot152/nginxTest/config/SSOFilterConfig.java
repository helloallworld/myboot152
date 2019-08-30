package com.wumin.boot152.nginxTest.config;

import com.ifly.qxb.uap.client.web.filter.SSOFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class SSOFilterConfig {


    /**
     * 添加其他jar包中的filter，并配置相应属性
     * @return
     */
    @Bean
    public FilterRegistrationBean addMyFilter(){
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        SSOFilter ssoFilter=new SSOFilter();
        registrationBean.setFilter(ssoFilter);
        Map<String,String> map=new HashMap<>();
        map.put("configPath","/sso/sso.properties");
        registrationBean.setInitParameters(map);
        //registrationBean.addUrlPatterns("/*");
       // registrationBean.setName("ssoFilter");
        return registrationBean;
    }
}
