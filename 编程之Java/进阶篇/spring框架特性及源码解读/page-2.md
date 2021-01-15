### spring 常见用法整理

#### 获取jar或war包内的资源

记录一下三种getResourceAsStream使用方式

Class.getResourceAsStream("")
从当前类的所在包下获取资源

Class.getResourceAsStream("/")
从classpath下获取资源，maven项目下，resources目录下的文件默认打包到classpath下

ClassLoader.getResourceAsStream()
不能以"/"开头，默认从classpath下获取

#### 下载文件或二进制流方式读取

```java
@RestController
public class ResourceController {

    @GetMapping(value = "/file")
    public void onlineReadFile(HttpServletResponse response) throws IOException {
        //以二进制的方式写入IO流，可支持大部分的文件类型的在线预览，比如PDF，文本类，图片类，视频类，但如果文件太大，前端页面请求会很慢，通常会使用静态资源代理，譬如nginx
        InputStream inputStream = this.getClass().getResourceAsStream("/xx.pdf");
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/application.yml");
        ClassPathResource resource = new ClassPathResource("/xx.pdf");

        response.getOutputStream().write(resource.getInputStream().readAllBytes());

    }

    public ResponseEntity onlineReadFile1(){
        InputStreamResource resource = new InputStreamResource(this.getClass().getResourceAsStream("/xx.pdf"));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
    }

    public void downloadFile(HttpServletResponse response)throws Exception{
        InputStream inputStream = this.getClass().getResourceAsStream("/xx.pdf");
        response.setCharacterEncoding("utf-8");
        //fileName使用这种方式可以解决浏览器下载的文件名乱码问题
        //TODO swagger调试时不生效
        String fileName = new String(("文件_"+System.currentTimeMillis()+".pdf").getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
        response.setHeader( "Content-Disposition","attachment;filename="+fileName);
        response.setHeader( "Cache-Control","no-cache,no-store,must-revalidate" );
        response.setHeader( "Pragma","no-cache" );
        response.setHeader( "Expires","0" );
        byte[] bytes = new byte[2048];
        while ((inputStream.read(bytes))!= -1 ){
            response.getOutputStream().write(bytes);
        }
        inputStream.close();
    }

    public ResponseEntity downloadFile1()throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;;){
            if (i == 1000){
                break;
            }
            sb.append(UUID.randomUUID().toString()).append("\n");
            i++;
        }
        outputStream.write(sb.toString().getBytes());
        outputStream.flush();
        byte[]bytes = outputStream.toByteArray();
        outputStream.close();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));

        String fileName = new String(("文件_"+System.currentTimeMillis()+".txt").getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1);
        HttpHeaders headers = new HttpHeaders();
        headers.add ( "Content-Disposition","attachment;filename="+fileName);
        headers.add ( "Cache-Control","no-cache,no-store,must-revalidate" );
        headers.add ( "Pragma","no-cache" );
        headers.add ( "Expires","0" );
        return ResponseEntity.ok().headers(headers).contentLength(bytes.length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }
}
```

#### 静态资源访问

spring-boot配置

```yaml
# 配置静态资源访问前缀
spring.mvc.static-path-pattern: /static/**
  # 配置静态资源路径，默认配置失效
spring.resources.static-locations:
    - classpath:/index
```

spring-boot默认的访问路径包含：

    "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/
    

假设src/main/resources目录下有以下的目录结构
```
- src
-  main
-   resources
-    index
-     a.txt
-    sheet
-     b.js
```

只能访问index目录下的a.txt文件，`http://localhost:8080/static/a.txt`


静态资源URL转义问题：

测试spring boot静态资源Tomcat代理后的访问路径是否会被转义，看看配置是否生效
    https://blog.csdn.net/chenzz2560/article/details/105357001
    https://blog.csdn.net/ColdFireMan/article/details/86552242