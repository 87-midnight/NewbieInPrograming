package com.lcg.proxy.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author linchuangang
 * @createTime 2020/11/11
 **/
public class CgTest {

    public static void main(String[] args) {
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code");
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(UserService.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new CustomerMethodInterceptor());
        UserService userService = (UserService) enhancer.create();
        userService.getUser("sss");

    }
}
