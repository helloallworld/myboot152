package com.wumin.boot152.controller;


import com.wumin.boot152.common.entity.query.ResultData;
import com.wumin.boot152.service.UseKeyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("key/getInfo")
public class UseKeyController {
@Resource
    UseKeyService useKeyService;
@PostMapping("/query")
    public ResultData query(String param,String id){
    return new ResultData(useKeyService.getInfo(param,id));
}
}
