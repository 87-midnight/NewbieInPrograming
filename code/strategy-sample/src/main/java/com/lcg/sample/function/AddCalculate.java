package com.lcg.sample.function;

import java.util.List;

public class AddCalculate implements Calculate {
    @Override
    public int calculate(List<Integer> args) {
        int b=0;
        for (Integer arg : args) {
            int a = arg;
            b += a;
        }
        return b;
    }
}
