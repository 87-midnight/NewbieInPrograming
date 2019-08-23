package com.lcg.sample.function;

import java.util.List;

@FunctionalInterface
public interface Calculate {

    int calculate(List<Integer>args);
}
