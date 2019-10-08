package com.lcg.sample.service.impl;

import com.lcg.sample.service.UserService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<Long> getList(String username) {
        return new ArrayList<Long>(){{add(System.currentTimeMillis());}};
    }
}
