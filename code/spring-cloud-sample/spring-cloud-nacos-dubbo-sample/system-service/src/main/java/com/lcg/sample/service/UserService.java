package com.lcg.sample.service;

import com.lcg.sample.BaseUserService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author linchuangang
 * @createTime 2020/10/24
 **/
@Service
public class UserService implements BaseUserService {

    @Override
    public String get(String username) {
        return "system-application: "+ username +", "+System.currentTimeMillis();
    }
}
