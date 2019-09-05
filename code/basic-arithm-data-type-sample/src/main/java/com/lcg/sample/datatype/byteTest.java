package com.lcg.sample.datatype;


import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class byteTest {

    public static void main(String[]args){
        byte a = (byte) 127;
        System.out.println(a+10);

        byte[] bytes = "hello,java".getBytes();
        for (byte b : bytes)System.out.println(b);

        try (InputStream in = new URL("http://www.baidu.com").openStream()){
            int length = in.available();
            byte[] content = new byte[length];
            in.read(content);
            System.out.println(new String(content, StandardCharsets.UTF_8));
        }catch (Exception var){
            var.printStackTrace();
        }
    }
}
