package com.lcg.sample.service.impl;

import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

public class CommonServiceImpl implements GenericService {
    @Override
    public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
        if (s.equals("print")) {
            StringBuilder builder = new StringBuilder();
            builder.append("the parameter list:[");
            for (Object obj : objects){
                builder.append(obj);
            }
            builder.append("]");
            return builder.toString();
        }
        return 0;
    }
}
