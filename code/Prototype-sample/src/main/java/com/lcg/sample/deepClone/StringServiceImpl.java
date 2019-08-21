package com.lcg.sample.deepClone;

import com.lcg.sample.shallowClone.CommonService;

public class StringServiceImpl implements CommonService {
    private SubClass subClass;

    public SubClass getSubClass() {
        return subClass;
    }

    public void setSubClass(SubClass subClass) {
        this.subClass = subClass;
    }

    public void print() {
        System.out.println("hello,string");
    }

    public Object clone() {
        StringServiceImpl obj = null;
        try {
            obj = (StringServiceImpl) super.clone();
            obj.subClass = (SubClass) this.subClass.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
