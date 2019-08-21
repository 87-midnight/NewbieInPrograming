package com.lcg.sample;

/**
 * 饿汉式单例
 */
public class SingletonHunger {

    private static SingletonHunger instance = new SingletonHunger();

    private SingletonHunger(){}

    public static SingletonHunger getInstance(){
        return instance;
    }
}
