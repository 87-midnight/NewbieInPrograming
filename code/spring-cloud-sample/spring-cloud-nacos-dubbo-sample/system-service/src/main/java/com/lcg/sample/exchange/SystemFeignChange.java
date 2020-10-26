package com.lcg.sample.exchange;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author linchuangang
 * @createTime 2020/10/25
 **/
@FeignClient(value = "system-service-feign")
public interface SystemFeignChange {

    @GetMapping(value = "/file")
    String getFile(@RequestParam String key);
}
