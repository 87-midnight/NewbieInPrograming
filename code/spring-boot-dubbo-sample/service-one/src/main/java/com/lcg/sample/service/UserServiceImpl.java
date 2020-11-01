package com.lcg.sample.service;

import com.lcg.sample.common.UserService;
import org.apache.dubbo.config.annotation.Service;

import java.util.UUID;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getUserName() {
        return "user-"+ UUID.randomUUID().toString();
    }
}
