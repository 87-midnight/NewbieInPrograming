package com.lcg.sample.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linchuangang
 * @createTime 2020/10/24
 **/
@RestController
public class UserController {

    @GetMapping(value = "/save")
    public String save(String id){
        return "system-application: save: "+id;
    }
}
