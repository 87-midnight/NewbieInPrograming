package com.lcg.sample.keepSign;

/**
 * @author linchuangang
 * @create 2019-09-06 15:27
 **/
public class SignTest {

    public static void main(String...args) {
        switchTest1(1);
        switchTest2(2);
    }

    public static void switchTest1(final int a){
        switch (a){
            case 1:
                System.out.println("变量为1");
                break;
            case 2:
                System.out.println("变量为2");
                break;
        }
    }

    public static void switchTest2(final int a){
        switch (a){
            case 1:
                System.out.println("变量为1,不跳出");
            case 2:
                System.out.println("变量为2，继续执行");
            case 3:
                System.out.println("变量为3，继续执行");
        }
    }
}
