package com.lcg.sample.clone.deep;

import java.io.*;

/**
 * 序列化方式深度克隆
 * @author linchuangang
 * @create 2019-09-06 17:21
 **/
public class SerializeClone {

    public static People deepClone(People people){
        People clone = null;
        try(
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream)){
            outputStream.writeObject(people);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
            clone = (People) inputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return clone;
    }
}
