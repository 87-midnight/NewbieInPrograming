package com.lcg.sample.service.impl;

import com.lcg.sample.service.ClassService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Random;

@Service
public class ClassServiceImpl implements ClassService {
    @Override
    public Integer saveClass(Integer a) {
        return new Random().nextInt(99999+a);
    }
}
