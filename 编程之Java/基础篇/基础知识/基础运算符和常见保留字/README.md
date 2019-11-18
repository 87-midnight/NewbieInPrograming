### 基础运算符和保留字

常见的基础运算符和符号、关键字等代表含义

示例代码：[点我](../../../../code/operate-sample)

#### &&

逻辑运算符，and。当其中一个为false，必定结果为false

#### ||

逻辑运算符，or。当其中一个为true，结果必定为true

```java
boolean a = true;
boolean b = false;
boolean result = a || b;//true
boolean result1 = a && b;//false
```

#### instanceof

instanceof 严格来说是Java中的一个双目运算符，用来测试一个对象是否为一个类的实例。

包含情况：
1. bj 必须为引用类型，不能是基本类型

```java
int i = 0;
System.out.println(i instanceof Integer);//编译不通过
System.out.println(i instanceof Object);//编译不通过
Integer a = 1;
System.out.println(a instanceof Integer);//编译通过
```

2. obj 为 null

在 JavaSE规范 中对 instanceof 运算符的规定就是：如果 obj 为 null，那么将返回 false。

3. obj 为 class 类的实例对象

```java
Integer integer = new Integer(1);
System.out.println(integer instanceof  Integer);//true
```

4. obj 为 class 接口的实现类

```java
ArrayList arrayList = new ArrayList();
System.out.println(arrayList instanceof List);//true
List list = new ArrayList();
System.out.println(list instanceof ArrayList);//true
```

5. obj 为 class 类的直接或间接子类

```java
public class Person {
 
}
public class Man extends Person{
     
    public static void main(String...args){
        Person p1 = new Person();
        Person p2 = new Man();
        Man m1 = new Man();
        System.out.println(p1 instanceof Man);//false
        System.out.println(p2 instanceof Man);//true
        System.out.println(m1 instanceof Man);//true
    }
}
```

#### |

什么叫多重捕获MultiCatch？一段代码可能引起多个异常，这时可以定义两个或更多的catch子句来处理这种情况，每个子句捕获一种类型的异常。

multi-catch语法时的异常不能有相交. 如IOException是Exception的子类, 所以以后用 | 分隔开的异常不能有父子关系.

```java
try {

            Integer a = null;
            int b = a * 2;
            
            int[]c = new int[]{1,2};
            System.out.println(c[2]);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            System.out.println("多个异常同时捕捉处理");
        }
```

#### ^
#### &
#### &lt;,&lt;=,&gt;=,&gt;,==

#### ++,+=,-=,*=

++，自增运算符。不是线程安全的操作。它涉及到多个指令，如读取变量值，
增加，然后存储回内存，这个过程可能会出现多个线程交差。

#### &lt;&lt;
#### &gt;&gt;

### 保留字

#### switch

switch 分支处理,支持String类型。

###### 第一种情况，每个case处理后跳出

```java
public static void switchTest1(final int a){
        switch (a){
            case 1:
                System.out.println("变量为1");
                break;
            case 2:
                System.out.println("变量为2");
                break;
        }
    }
```
用户输入1，则程序输出“变量为1”

###### 第二种情况，每个case处理后不跳出

```java
public static void switchTest2(final int a){
        switch (a){
            case 1:
                System.out.println("变量为1,不跳出");
            case 2:
                System.out.println("变量为2，继续执行");
            case 3:
                System.out.println("变量为3，继续执行");
        }
    }
```
用户输入1，程序会输出
```cmd
变量为1,不跳出
变量为2，继续执行
变量为3，继续执行
```
用户输入2，程序会输出
```cmd
变量为2，继续执行
变量为3，继续执行
```
用户输入3，程序会输出
```cmd
变量为3，继续执行
```

用户输入4，程序不输出任何字符串。因为case里没有任何一个满足。

故，在case没有break的情况下，只要当switch里面的变量等于某一个case的值，则会从该case开始，往后的<br>
所有case都会被执行。