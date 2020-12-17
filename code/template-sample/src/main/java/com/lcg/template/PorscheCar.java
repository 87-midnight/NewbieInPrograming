package com.lcg.template;

public class PorscheCar extends CarMaintain{
    @Override
    public void check() {
        System.out.println("保时捷检查");
    }

    @Override
    public void correct() {
        System.out.println("保时捷纠正参数");
    }

    @Override
    public void wash() {
        System.out.println("保时捷清洗");
    }
}
