package com.wumin.boot152.controller;

import com.wumin.boot152.common.entity.query.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service2")
public class MyController2 {
    private Logger logger= LoggerFactory.getLogger(MyController2.class);
    @GetMapping("test")
    public ResultData test(){
        logger.info("in service2");
        return ResultData.ok();
    }
}
