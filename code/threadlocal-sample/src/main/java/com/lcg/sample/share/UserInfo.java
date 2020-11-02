package com.lcg.sample.share;

import java.io.Serializable;

/**
 * @author linchuangang
 * @createTime 2020/11/2
 **/
public class UserInfo implements Serializable {

    private String name;
    private String password;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
