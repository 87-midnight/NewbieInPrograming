package com.lcg.template;

public abstract class CarMaintain {

    public void maintain(){
        check();
        correct();
        wash();
    }

    public abstract void check();
    public abstract void correct();
    public abstract void wash();

}
