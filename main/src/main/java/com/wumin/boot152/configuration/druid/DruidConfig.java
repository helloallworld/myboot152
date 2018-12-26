package com.wumin.boot152.configuration.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * druid连接池黑白名单，用户名密码（设置后只有指定ip可以通过druid的页面查看数据库的一些状态）
 *
 * 使用tkmybatis时，tkmybatis会使用Druid的连接池获取连接。
 */
@Configuration
public class DruidConfig {
    //白名单
    @Value("${druidAllowIp}")
    private String druidAllowIp;
    //黑名单
    @Value("${druidDenyIp}")
    private String druidDenyIp;
    @Value("${druidLoginUsername}")
    private String druidLoginUsername;
    @Value("${druidLoginPassword}")
    private String druidLoginPassword;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_ALLOW, druidAllowIp);
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_DENY, druidDenyIp);
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_USERNAME, druidLoginUsername);
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_PASSWORD, druidLoginPassword);
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_REMOTE_ADDR, "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}