package com.lcg.proxy.cglib;

/**
 * @author linchuangang
 * @createTime 2020/11/11
 **/
public class UserService {

    public void getUser(String name){
        System.out.println("获取用户名："+name);
    }

    public final void insert(){
        System.out.println("xxxx insert test");
    }
}
