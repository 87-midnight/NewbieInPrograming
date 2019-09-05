### 数据类型和基础运算

#### 数据类型

##### 基础数据类型

七个基础数据类型：byte,short,int,float,double,char,boolean

###### byte

1 byte = 8 bit，即一个字节有8位。byte常用于网络、文件流传输，编解码，进制转换。

在计算机数值表示范围里，1byte表示-128~127。

byte示例

```java
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
```
###### int和short

short占用两个字节，取值范围-32768~32767。int占用4个字节，取值范围为-2147483648~2147483647。

unsigned short取值范围0~65535，unsigned int取值范围0~4294967295。

int和short运算示例：

- 第一种情况
    - 错误
    ```cmd
    short a =1;
    a = a+1;
    System.out.print(a);
    ```
    - 原因
    
    精度小于int的数值运算时会被自动转换为int进行计算，a+1得到的是int类型数值，无法赋值给short类型的a变量
    
    - 正确的写法
    ```cmd
    short a = 1;
    a = (short)(a+1);
    System.out.print(a);
    ```
- 第二种情况
    - 正确
    ```cmd
    short b = 1;
    b += 1;
    System.out.println(b);
    ```
    - 原因
    
    ***java语言规范中关于复合赋值的解释是这样的：E1 op=E2等价于
    E1=(T)(E1 op E2),<br>这里的T是E1的数据类型，这个复合赋值是自带了隐式的强制类型转换的。***

- 第三种情况
    - 错误例子
    ```cmd
    short c = 1;
    short d = 1;
    short e = d+c;
    System.out.println(e);
    ```
    - 原因
    
    精度小于int的数值运算时会被自动转换为int进行计算，d+c得到的是int类型数值，无法赋值给short类型的e变量
    
    - 正确示例
    
    ```cmd
    short c = 1;
    short d = 1;
    short e = (short)(d+c);
    System.out.println(e);
    ```
- char
- float
- double
- boolean

##### 常见封装类型
- Integer
- Double
- Byte
- Boolean
- String
- StringBuilder
- StringBuffer
- Number
- BigDecimal

#### 基础运算符

常见的基础运算符和符号、关键字，方法等代表含义

1. &&

2. ||

3. instanceof

4. |
5. ^
6. &
7. &lt;,&lt;=,&gt;=,&gt;,==,equals()
8. +=,-=,*=
9. &lt;&lt;
10. &gt;&gt;