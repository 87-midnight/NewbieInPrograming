package com.lcg.sample.nio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * @author linchuangang
 * @create 2019-09-06 22:02
 **/
public class FilesOperate {


    public void createFile(String fullPathFileName) throws IOException {
        Files.createFile(Paths.get(fullPathFileName));
    }

    public void deleteFile(String fullPathFileName)throws Exception{
        Files.deleteIfExists(Paths.get(fullPathFileName));
    }

    public void readAndWrite(String fullPathFileName)throws Exception{
        //读文件
        BufferedReader reader = Files.newBufferedReader(Paths.get(fullPathFileName), StandardCharsets.UTF_8);
        //写文件
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(fullPathFileName+"_"+System.currentTimeMillis()), StandardCharsets.UTF_8);
        String str = null;
        while((str = reader.readLine()) != null){//循环读
            System.out.println(str);
            writer.write(str);//每读到一行，就写入一行
            writer.newLine();
        }
        reader.close();
        writer.flush();
        writer.close();
    }

    public File find(String path,String targetName)throws Exception{
        FilesVisitor filesVisitor = new FilesVisitor();
        Files.walkFileTree(Paths.get(path),filesVisitor);
        return filesVisitor.getTargetFile().toFile();
    }

    public static class FilesVisitor extends SimpleFileVisitor<Path>{

        private List<Path> list;
        private String targetName;
        private Path targetFile;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            String path = file.toFile().getAbsolutePath();
            list.add(file);
            if (path.matches(targetName)){
                targetFile = file;
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Path> getList() {
            return list;
        }

        public void setList(List<Path> list) {
            this.list = list;
        }

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public Path getTargetFile() {
            return targetFile;
        }

        public void setTargetFile(Path targetFile) {
            this.targetFile = targetFile;
        }
    }
}
