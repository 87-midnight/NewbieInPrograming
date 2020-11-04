package com.lcg.sample.serializable;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Objects;

/**
 * @author linchuangang
 * @create 2019-09-06 19:17
 **/
public class SerializeTest {

    @Getter
    @Setter
    public static class User implements Serializable{
        private String name;
        private String address;
    }
    @Getter
    @Setter
    public static class Student implements Serializable{
        private String sn;
        private User info;

    }

    public static void main(String[] args)throws Exception {
        Student origin = new Student();
        origin.setSn("123456");
        User info = new User();
        info.setName("tom");
        info.setAddress("test");
        origin.setInfo(info);
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            ) {
            //序列化，写入字节数组输出流
            objectOutputStream.writeObject(origin);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            //反序列化，从字节数组输入流读取对象的字节数组，转换为一个新的对象，深拷贝
            Student copy = (Student) objectInputStream.readObject();
            System.out.println("地址比较结果："+ Objects.equals(origin,copy));
        }catch (Exception var1){
            var1.printStackTrace();
        }


    }
}
