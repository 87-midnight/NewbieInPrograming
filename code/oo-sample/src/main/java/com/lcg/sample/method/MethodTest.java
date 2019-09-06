package com.lcg.sample.method;

/**
 * @author linchuangang
 * @create 2019-09-06 20:33
 **/
public class MethodTest {

    public static void main(String[]args){
        BaseService baseService = new BaseService("jack");
        baseService.save();//输出baseService invoke save method:jack
        baseService.print(1);//输出baseService print method:1

        UserService userService = new UserService();
        userService.save();//输出userService invoke save method
        userService.print(2,3);//输出userService print method:{2,3}

        BaseService baseService1 = new UserService();
        baseService1.save();//输出userService invoke save method
        baseService1.print(4);//输出baseService print method:4
    }
}
