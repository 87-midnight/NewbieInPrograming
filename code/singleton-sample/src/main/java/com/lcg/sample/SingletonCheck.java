package com.lcg.sample;

/**
 * @description 双重检查，线程安全，无反射、序列化等缺陷
 * @author linchuangang
 * @create 2020/12/17 11:13
 **/
public class SingletonCheck {

    private static volatile SingletonCheck instance = null;

    private SingletonCheck(){}

    public static SingletonCheck getInstance(){
        synchronized (SingletonCheck.class){
            if (instance == null){
                instance = new SingletonCheck();
            }
        }
        return instance;
    }

    public void test(){

    }
}
