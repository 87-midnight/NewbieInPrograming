package com.lcg.sample.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linchuangang
 * @createTime 2020/10/24
 **/
@RestController
public class FileController {

    @GetMapping(value = "/file")
    public String getFileName(String key){
        return System.currentTimeMillis()+","+key;
    }
}
