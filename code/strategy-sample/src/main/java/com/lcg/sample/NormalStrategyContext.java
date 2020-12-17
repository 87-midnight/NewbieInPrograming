package com.lcg.sample;

import com.lcg.sample.function.AddCalculate;
import com.lcg.sample.function.Calculate;
import com.lcg.sample.function.MultiplyCalculate;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NormalStrategyContext {

    private Map<String, Calculate> map = new ConcurrentHashMap<>();

    public void store(){
        map.putIfAbsent("add",new AddCalculate());
        map.putIfAbsent("multiply",new MultiplyCalculate());
    }

    public Integer calculate(String key,Integer...args){
        return map.getOrDefault(key,new AddCalculate()).calculate(Arrays.asList(args));
    }
}
