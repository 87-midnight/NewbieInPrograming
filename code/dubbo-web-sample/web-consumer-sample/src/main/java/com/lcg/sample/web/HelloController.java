package com.lcg.sample.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/echo")
    public Object echo(){
        return ResponseEntity.ok();
    }
}
