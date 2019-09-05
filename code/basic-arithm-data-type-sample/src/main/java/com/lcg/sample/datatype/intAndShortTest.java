package com.lcg.sample.datatype;

public class intAndShortTest {

    public static void main(String[]args){
        short a = 1;
        a = (short)(a+1);
        System.out.print(a);

        short b = 1;
        b += 1;
        System.out.println(b);

        short c = 1;
        short d = 1;
        short e = (short)(d+c);
        System.out.println(e);
    }
}
