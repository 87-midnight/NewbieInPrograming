package com.lcg.sample;

public class Application {

    public static void main(String...args){
        LettuceTemplate template = new LettuceTemplate();
        template.getConnection("localhost",6379).setHash("te","tt","va",111);
        template.close();
    }
}
