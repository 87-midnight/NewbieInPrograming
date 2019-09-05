package com.lcg.sample.datatype;

public class longAndbooleanTest {

    public static boolean a2;

    public boolean a3;

    public int a4;


    public static void main(String[]args){
        long a = 152012L;
        long b = 12L;

        double c = 5.23;
        double d = a + c;//double精度最高，故a+c的运算后数值类型为double
        System.out.println(d);

        long f = a * b;
        System.out.println(f);

        boolean a1 = true;
        System.out.println(a1);

        System.out.println(a2);//默认值是false，当声明为静态变量
        longAndbooleanTest test = new longAndbooleanTest();
        System.out.println(test.a3);//默认值是false，当声明为成员变量
    }
}
