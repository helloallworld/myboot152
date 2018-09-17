package com.wumin.boot152.controller;

import com.wumin.boot152.common.entity.query.ResultData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("service1")
public class MyController {
    private Logger logger= LoggerFactory.getLogger(MyController.class);
    @Autowired
    private OkHttpClient client;

    @GetMapping("test")
    public ResultData test() throws IOException {
        logger.info("in service1");
        Request request = new Request.Builder().url("http://localhost:9999/service2/test").build();
        Response response = client.newCall(request).execute();
        return ResultData.ok();
    }
}
