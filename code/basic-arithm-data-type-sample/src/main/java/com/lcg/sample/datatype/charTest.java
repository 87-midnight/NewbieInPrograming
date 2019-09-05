package com.lcg.sample.datatype;

public class charTest {

    public static void main(String[]args){
        char a = '你';
        System.out.println(a);

        char b = 65;
        System.out.println(b);

        char c = (char) (a+b+100);
        System.out.println(c);

        char[]chars = "你好呀，好久不见".toCharArray();
        for (char d:chars){
            System.out.println(d);
        }
    }
}
