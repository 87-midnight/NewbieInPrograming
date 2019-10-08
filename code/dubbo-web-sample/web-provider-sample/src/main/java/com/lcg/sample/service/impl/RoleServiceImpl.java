package com.lcg.sample.service.impl;

import com.lcg.sample.service.RoleService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<String> getRoleName() {
        return new ArrayList<String>(){{add("lcg");}};
    }
}
