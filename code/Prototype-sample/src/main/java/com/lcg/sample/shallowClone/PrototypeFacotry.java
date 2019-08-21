package com.lcg.sample.shallowClone;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PrototypeFacotry {

    private CommonService echo;
    private CommonService print;
    private ConcurrentMap<String,CommonService> objectMap = new ConcurrentHashMap<String, CommonService>();

    public PrototypeFacotry(){}

    public PrototypeFacotry(boolean enableDefautlVariable){
        this.echo = new EchoServiceImpl("default value");
        this.print = new PrintServiceImpl();
    }

    public PrototypeFacotry(CommonService echo, CommonService print) {
        this.echo = echo;
        this.print = print;
    }

    public void addProtoObject(final String key,final CommonService commonService){
        objectMap.putIfAbsent(key,commonService);
    }

    public CommonService getCopyFromHashMap(final String key){
        return (CommonService) objectMap.get(key).clone();
    }

    public <T extends CommonService> CommonService getCopyOfMemberVariable(Class<T> clazz){
        if (clazz == echo.getClass()){
            return (CommonService) echo.clone();
        }else if (clazz == print.getClass()){
            return (CommonService)print.clone();
        }
        return null;
    }
}
