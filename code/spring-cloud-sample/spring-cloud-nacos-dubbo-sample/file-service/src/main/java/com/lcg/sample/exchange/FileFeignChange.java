package com.lcg.sample.exchange;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author linchuangang
 * @createTime 2020/10/25
 **/
@FeignClient(value = "system-service-feign")
public interface FileFeignChange {

    @GetMapping(value = "/save")
    String saveUser(@RequestParam String id);
}
