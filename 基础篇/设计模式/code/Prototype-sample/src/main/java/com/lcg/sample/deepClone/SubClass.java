package com.lcg.sample.deepClone;

public class SubClass implements Cloneable{

    private String msg;

    public SubClass(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
