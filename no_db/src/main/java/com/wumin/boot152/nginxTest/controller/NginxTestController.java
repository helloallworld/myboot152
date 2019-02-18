package com.wumin.boot152.nginxTest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nginx")
public class NginxTestController {

    @GetMapping("test")
    public String test(){
        return "in nginx test1";
    }
}
