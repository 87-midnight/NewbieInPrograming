package com.lcg.sample;

/**
 * @description 静态内部类加载方式，存在反射攻击或者反序列化攻击
 * @author linchuangang
 * @create 2020/12/17 11:14
 **/
public class SingletonClassLoader {

    private static class SingletonHolder{
        private static final SingletonClassLoader instance = new SingletonClassLoader();
    }
    private SingletonClassLoader(){}

    public static SingletonClassLoader getInstance(){
        return SingletonHolder.instance;
    }

    public void test(){

    }
}
