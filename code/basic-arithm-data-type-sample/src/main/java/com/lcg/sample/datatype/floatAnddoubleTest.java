package com.lcg.sample.datatype;

public class floatAnddoubleTest {

    public static void main(String[]args){
        float a = 3.14f;
        float a1 = 1.222f;
        float a2 = a + a1;
        System.out.println(a2);

        double b = 3.2245;
        double c = a + b; //运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型
        System.out.println(c);

        float d = (float) (a + b);//本来运算后的数值类型是double，故需要强制转换为float类型
        System.out.println(d);

        int e = 1;
        float f = (e + a)/2;//运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型
        System.out.println(f);

        double f1 = e + b*2;//运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型
        System.out.println(f1);
    }
}
