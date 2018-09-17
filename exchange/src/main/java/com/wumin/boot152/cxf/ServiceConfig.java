package com.wumin.boot152.cxf;

import com.wumin.boot152.cxf.service.MyWebServiceService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

@Configuration
public class ServiceConfig {
    @Resource
    private AuthInterceptor authInterceptor;
    @Resource
    @Lazy
    private MyWebServiceService myWebServiceService;

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/demo/*");
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public Endpoint endpointMy(){
        EndpointImpl endpoint=new EndpointImpl(springBus(),myWebServiceService);
        //增加密码拦截
        endpoint.getInInterceptors().add(authInterceptor);
        endpoint.publish("/myWebService");
        return endpoint;
    }
}
