package com.lcg.sample.jdk;


public class PrintService implements Runnable {

    private String type;

    public PrintService(String type) {
        this.type = type;
    }

    public void print(){
        System.out.println(type+",time:"+System.currentTimeMillis());
    }

    public void run() {
        print();
    }
}
