package com.lcg.sample.method;

/**
 * @author linchuangang
 * @create 2019-09-06 20:23
 **/
public class BaseService {

    private String username;

    public BaseService(){

    }

    /**
     * 构造方法重载
     * @param username
     */
    public BaseService(String username) {
        this.username = username;
    }

    public void save(){
        System.out.println("baseService invoke save method:"+username);
    }

    public void print(int a){
        System.out.println("baseService print method:"+a);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
