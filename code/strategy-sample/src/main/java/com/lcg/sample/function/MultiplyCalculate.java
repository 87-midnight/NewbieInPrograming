package com.lcg.sample.function;

import java.util.List;

public class MultiplyCalculate implements Calculate {
    @Override
    public int calculate(List<Integer> args) {
        int a = 1;
        for (Integer arg : args){
            a *= arg;
        }
        return a;
    }
}
