package com.wumin.boot152.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @WebService 声明为webservice接口
 */
@WebService
public interface MyWebServiceService {
    /**
     * webservice 接口方法
     * @param name
     * @return
     */
    @WebMethod(operationName = "getName")
    String getName(String name);
}
