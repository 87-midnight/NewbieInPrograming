package com.lcg.sample.clone.deep;

import java.io.Serializable;

/**
 * 序列化克隆使用对象
 * @author linchuangang
 * @create 2019-09-06 17:22
 **/
public class People implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
