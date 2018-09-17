package com.wumin.boot152.ons.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TestOnsServiceImp implements TestOnsService {
    private static Logger logger= LoggerFactory.getLogger(TestOnsServiceImp.class);
    @Override
    public Object doTest(Integer id) {
        //业务处理，调用别人的webService接口的业务逻辑
        logger.info("处理中");
        return null;
    }

    @Override
    public Object doMapTest(Integer id, String type) {
        //业务处理，调用别人的webService接口的业务逻辑
        logger.info("mapTag处理中");
        return null;
    }
}
