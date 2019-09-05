### 数据类型和基础运算

#### 数据类型

##### 基础数据类型

七个基础数据类型：byte,short,int,float,double,char,boolean

在java中，基本数据类型的字节数，最大值和最小值一览表。

|数据类型|字节数|二进制位数|范围|规律|
|:---:|:---:|:---:|:---:|:---:|
|byte	|1	|8 	|-128～127	|-2^7～2^7-1
|short	|2 	 |16	|-32768～32767	|-2^15～2^15-1
|int	|4 	|32 	|-2147483648～2147483647	|-2^31～2^31-1
|long	|8 	|64 	|-9223372036854775808 ~ 9223372036854775807	|-2^63～2^63-1
|float	|4 	|32 	|1.4E-45~3.4028235E38|	 
|double	|8 	|64 	|4.9E-324~1.7976931348623157E308|	 
|char	|2 	|16 	|0～65535 	|0~2^16-1
|boolean |1 |8 	|true或false|	true或false

代码示例：[点我查看](../../../code/basic-arithm-data-type-sample)

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
    ```java
    short a =1;
    a = a+1;
    System.out.print(a);
    ```
    - 原因
    
    精度小于int的数值运算时会被自动转换为int进行计算，a+1得到的是int类型数值，无法赋值给short类型的a变量
    
    - 正确的写法
    ```java
    short a = 1;
    a = (short)(a+1);
    System.out.print(a);
    ```
- 第二种情况
    - 正确
    ```java
    short b = 1;
    b += 1;
    System.out.println(b);
    ```
    - 原因
    
    ***java语言规范中关于复合赋值的解释是这样的：E1 op=E2等价于
    E1=(T)(E1 op E2),<br>这里的T是E1的数据类型，这个复合赋值是自带了隐式的强制类型转换的。***

- 第三种情况
    - 错误例子
    ```java
    short c = 1;
    short d = 1;
    short e = d+c;
    System.out.println(e);
    ```
    - 原因
    
    精度小于int的数值运算时会被自动转换为int进行计算，d+c得到的是int类型数值，无法赋值给short类型的e变量
    
    - 正确示例
    
    ```java
    short c = 1;
    short d = 1;
    short e = (short)(d+c);
    System.out.println(e);
    ```
###### char

char 在java中是2个字节。java采用unicode，2个字节（16位）来表示一个字符,包含了所有的ascii字符，本意代表述职，顾可以进行数值的运算。

char使用示例

基础运算和转换

```java
char a = '你';
System.out.println(a);//输出：你

char b = 65;
System.out.println(b);//输出：A

char c = (char) (a+b+100);//输出：倅
System.out.println(c);
```

一段话拆分成单个字符

```java
char[]chars = "你好呀，好久不见".toCharArray();
for (char d:chars){
   System.out.println(d);
}
```
###### float

float占用4个字节。

float的精度是由尾数的位数来决定的。浮点数在内存中是按科学计数法来存储的，其整数部分始终是一个隐含着的“1”，由于它是不变的，故不能对精度造成影响。

float：2^23 = 8388608，一共七位，这意味着最多能有7位有效数字，但绝对能保证的为6位，也即float的精度为6~7位有效数字；

***注：浮点型如果不加F则默认为double型***。

***运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型***

示例：见double的示例

###### double

double 占用8个字节,默认有小数点的数值是double类型


double的精度是由尾数的位数来决定的。浮点数在内存中是按科学计数法来存储的，其整数部分始终是一个隐含着的“1”，由于它是不变的，故不能对精度造成影响。

double：2^52 = 4503599627370496，一共16位，同理，double的精度为15~16位。

示例：

```java
float a = 3.14f;
float a1 = 1.222f;
float a2 = a + a1;
System.out.println(a2);

double b = 3.2245;
double c = a + b; //运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型
System.out.println(c);

float d = (float) (a + b);//本来运算后的数值类型是double，故需要强制转换为float类型
ystem.out.println(d);

int e = 1;
float f = (e + a)/2;//运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型
System.out.println(f);
        
double f1 = e + b*2;//运算的结果的数据类型，是参与运算的所有数值中最高精度的数据类型
System.out.println(f1);
```

###### long

***注：long型后如果不加L则默认为int型***

###### boolean

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