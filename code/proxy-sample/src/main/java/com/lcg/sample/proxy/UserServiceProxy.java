package com.lcg.sample.proxy;

public class UserServiceProxy implements UserService {

    private UserServiceImpl userService;

    public UserServiceProxy(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void saveUser() {
        beforeSave();
        this.userService.saveUser();
    }

    private void beforeSave(){
        System.out.println("do something before save user record");
    }
}
