package com.lcg.sample.operate;

/**
 * @author linchuangang
 * @create 2019-09-06 15:27
 **/
public class OperateTest {

    public static void main(String[]args){
        boolean flag = true && false;
        if (flag || !flag){
            System.out.println("true");
        }

        try {

            Integer a = null;
            int b = a * 2;

            int[]c = new int[]{1,2};
            System.out.println(c[2]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            System.out.println("多个异常同时捕捉处理");
        }
    }

}
