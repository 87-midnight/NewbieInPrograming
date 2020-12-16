package com.lcg.observer;

public interface Student {

    Subject type();

    void response(String command);

    enum Subject{
        math,english
    }
}
