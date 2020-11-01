package com.lcg.sample.service;

import com.lcg.sample.common.DeviceService;
import org.apache.dubbo.config.annotation.Service;

import java.util.UUID;

/**
 * @author linchuangang
 * @createTime 2020/11/1
 **/
@Service
public class DeviceServiceImpl implements DeviceService {
    @Override
    public String getDeviceName() {
        return "device-"+ UUID.randomUUID().toString();
    }
}
