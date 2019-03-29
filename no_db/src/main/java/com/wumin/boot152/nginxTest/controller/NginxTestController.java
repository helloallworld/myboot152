package com.wumin.boot152.nginxTest.controller;

import com.wumin.boot152.nginxTest.entity.RequestParam;
import com.wumin.boot152.nginxTest.services.AddPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("nginx")
public class NginxTestController {

    @Autowired
    AddPropertiesService addPropertiesService;
    @RequestMapping(value = "/test")
    public String test( String val){
        System.out.println(val);
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
    @PostMapping("postTest")
    public String postTest(RequestParam param,HttpServletRequest request){
        System.out.println(param);
        return "ok";
    }
}
