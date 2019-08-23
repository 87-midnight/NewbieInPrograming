package com.lcg.sample;

import com.lcg.sample.proxy.UserServiceImpl;
import com.lcg.sample.proxy.UserServiceProxy;

public class Test {

    public static void main(String...args){
        UserServiceImpl userService = new UserServiceImpl();
        UserServiceProxy proxy = new UserServiceProxy(userService);
        proxy.saveUser();
    }
}
