package com.wumin.boot152.nginxTest.controller;

import com.wumin.boot152.nginxTest.services.AddPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nginx")
public class NginxTestController {

    @Autowired
    AddPropertiesService addPropertiesService;
    @GetMapping("test")
    public String test(){
        return "in nginx test1";
    }
    @GetMapping("addProperties")
    public void add(String url){
        addPropertiesService.addProperties(url);
    }
    @GetMapping("showUrl")
    public String show(){
        return addPropertiesService.showUrl();
    }
    @GetMapping("showUrl2")
    public String show2(){
        return addPropertiesService.showUrl2();
    }
}
