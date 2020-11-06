### 文件操作示例

示例：[点我查看](../../../code/file-sample)

#### 传统IO操作

位于io包下

##### 文件的创建、删除

##### 文件的读写

##### 文件的查找

#### NIO操作

位于nio包下
##### 文件的创建、删除

```java
public void createFile(String fullPathFileName) throws IOException {
        Files.createFile(Paths.get(fullPathFileName));
    }

    public void deleteFile(String fullPathFileName)throws Exception{
        Files.deleteIfExists(Paths.get(fullPathFileName));
    }
```
##### 文件的读写

```java
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
```
##### 文件夹的遍历

```java
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
```