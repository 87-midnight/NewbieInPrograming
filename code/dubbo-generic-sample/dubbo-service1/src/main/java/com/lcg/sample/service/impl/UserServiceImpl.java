package com.lcg.sample.service.impl;

import com.lcg.sample.service.UserService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author linchuangang
 * @create 2019-09-27 6:32 PM
 **/
@Service
public class UserServiceImpl implements UserService{
    @Override
    public Long save() {
        return System.currentTimeMillis();
    }
}
