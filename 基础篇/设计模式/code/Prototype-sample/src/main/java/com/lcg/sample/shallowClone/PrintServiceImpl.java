package com.lcg.sample.shallowClone;

import com.lcg.sample.deepClone.SubClass;

public class PrintServiceImpl implements CommonService {

    private SubClass subClass;

    public SubClass getSubClass() {
        return subClass;
    }

    public void setSubClass(SubClass subClass) {
        this.subClass = subClass;
    }

    public void print() {
        System.out.println("hello,print");
    }

    @Override
    public Object clone()  {
        PrintServiceImpl obj = null;
        try {
            obj = (PrintServiceImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
