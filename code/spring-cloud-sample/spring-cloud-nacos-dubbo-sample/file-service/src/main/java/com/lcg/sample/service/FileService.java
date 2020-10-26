package com.lcg.sample.service;

import com.lcg.sample.BasePrintService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author linchuangang
 * @createTime 2020/10/24
 **/
@Service
public class FileService implements BasePrintService {
    @Override
    public String print(String param) {
        return "file-application: "+ param +", "+System.currentTimeMillis();
    }
}
