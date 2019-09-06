package com.lcg.sample.clazz;

/**
 * @author linchuangang
 * @create 2019-09-06 19:36
 **/
public class ClazzTest {

    public static void main(String...args){
        //实例化部分为匿名内部类
        Classroom classroom = new Classroom() {
            public void beginClass() {
                System.out.println("the class is beginning");
            }
        };
    }
}
