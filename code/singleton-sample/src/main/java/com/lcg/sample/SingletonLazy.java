package com.lcg.sample;

/**
 * 懒汉式单例
 */
public class SingletonLazy {

    //保证 instance 在所有线程中同步
    private static volatile SingletonLazy instance=null;

    private SingletonLazy(){}

    public static synchronized SingletonLazy getInstance(){
        if (instance == null){
            instance = new SingletonLazy();
        }
        return instance;
    }
}
