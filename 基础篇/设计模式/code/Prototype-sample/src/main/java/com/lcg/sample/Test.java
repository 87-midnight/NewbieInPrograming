package com.lcg.sample;

import com.lcg.sample.deepClone.StringServiceImpl;
import com.lcg.sample.deepClone.SubClass;
import com.lcg.sample.shallowClone.CommonService;
import com.lcg.sample.shallowClone.EchoServiceImpl;
import com.lcg.sample.shallowClone.PrintServiceImpl;
import com.lcg.sample.shallowClone.PrototypeFacotry;

public class Test {

    public static void main(String...args){
        //原型模式，基于浅拷贝
        PrototypeFacotry factory = new PrototypeFacotry();
        factory.addProtoObject("echo",new EchoServiceImpl("测试"));
        CommonService service1 = factory.getCopyFromHashMap("echo");
        service1.print();
        EchoServiceImpl service2 = new EchoServiceImpl("测试1");
        service2.print();
        service2.setContent("测试3");
        EchoServiceImpl service3 = (EchoServiceImpl) service2.clone();
        service3.print();

        PrintServiceImpl service4 = new PrintServiceImpl();
        service4.setSubClass(new SubClass("消息4"));
        PrintServiceImpl service5 = (PrintServiceImpl) service4.clone();
        //浅拷贝,成员对象指向引用
        System.out.println("浅拷贝result:"+(service4.getSubClass() == service5.getSubClass()));

        StringServiceImpl service6 = new StringServiceImpl();
        service6.setSubClass(new SubClass("消息5"));
        StringServiceImpl service7 = (StringServiceImpl) service6.clone();
        System.out.println("深拷贝对象比较结果:"+(service6.getSubClass() == service7.getSubClass()));
        System.out.println("深拷贝对象的成员变量比较结果:"+(service6.getSubClass().getMsg().equals(service7.getSubClass().getMsg())));
    }
}
