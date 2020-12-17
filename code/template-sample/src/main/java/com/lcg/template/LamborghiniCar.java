package com.lcg.template;

public class LamborghiniCar extends CarMaintain{
    @Override
    public void check() {
        System.out.println("兰博基尼检查");
    }

    @Override
    public void correct() {
        System.out.println("兰博基尼纠正参数");
    }

    @Override
    public void wash() {
        System.out.println("兰博基尼清洗");
    }
}
