package com.lcg.observer;

public class MathStudent implements Student{
    @Override
    public void response(String command) {
        System.out.println(command);
    }

    @Override
    public Subject type() {
        return Subject.math;
    }
}
