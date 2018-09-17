package com.wumin.boot152.cxf.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * webservice接口实现
 */
@Service
@WebService(serviceName = "MyWebServiceServiceImp",portName = "MyWebServiceService",targetNamespace = "http://service.cxf.exchange.wumin.com",endpointInterface = "com.wumin.boot152.cxf.service.MyWebServiceService")
public class MyWebServiceServiceImp implements MyWebServiceService {
    @Override
    public String getName(String name) {
        System.out.println("hello"+name);
        return "hello"+name;
    }
}
