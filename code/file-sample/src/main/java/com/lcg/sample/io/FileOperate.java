package com.lcg.sample.io;

import java.io.*;

/**
 * @author linchuangang
 * @create 2019-09-06 21:58
 **/
public class FileOperate {

    public void createFile(String fullPathFileName) throws IOException {
        File file = new File(fullPathFileName);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    public void deleteFile(String fullPathFileName)throws Exception{
        File file = new File(fullPathFileName);
        if (file.exists()){
            file.delete();
        }
    }

    /**
     * 按行读取文件内容
     * @param fullPathFileName
     * @throws Exception
     */
    public void readAndWriteByLine(String fullPathFileName)throws Exception{
        //读取文件(字符流)
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fullPathFileName),"GBK"));
       //BufferedReader in = new BufferedReader(new FileReader("d:\\1.txt")));
       //写入相应的文件
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fullPathFileName+"_"+System.currentTimeMillis()),"GBK"));
        //BufferedWriter out = new BufferedWriter(new FileWriter("d:\\2.txt"))；
        //读取数据
        //循环取出数据
        String str = null;
        while ((str = in.readLine()) != null) {
            System.out.println(str);
            //写入相关文件
            out.write(str);
            out.newLine();
        }
        //把缓存在内存中残留的数据压入文件中，并清除缓存
        out.flush();
        //关闭流
        in.close();
        out.close();
    }

    public void readAndWriteByByte(String fullPathFileName)throws Exception{
        //读取文件(字节流)
        Reader in = new InputStreamReader(new FileInputStream(fullPathFileName),"GBK");
       //写入相应的文件
        PrintWriter out = new PrintWriter(new FileWriter(System.currentTimeMillis()+"_"+fullPathFileName));
        //读取数据
        //循环取出数据
        byte[] bytes = new byte[1024];
        int len = -1;
        while ((len = in.read()) != -1) {
            System.out.println(len);
            //写入相关文件
            out.write(len);
        }
        //把缓存在内存中残留的数据压入文件中，并清除缓存
        out.flush();
        //关闭流
        in.close();
        out.close();
    }
}
