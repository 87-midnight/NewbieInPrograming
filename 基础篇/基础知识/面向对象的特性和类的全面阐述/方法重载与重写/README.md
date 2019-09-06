### 方法重载与重写

#### 构造方法重载

特性：

- 构造方法没有返回类型
- 参数列表不一致
- 方法名与类名一致


```java
public class BaseService {

    private String username;

    public BaseService(){

    }

    /**
     * 构造方法重载
     * @param username
     */
    public BaseService(String username) {
        this.username = username;
    }

    public void save(){
        System.out.println("baseService invoke save method:"+username);
    }

    public void print(int a){
        System.out.println("baseService print method:"+a);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
```
执行结果：

```java
public class MethodTest {

    public static void main(String[]args){
        BaseService baseService = new BaseService("jack");
        baseService.save();//输出baseService invoke save method:jack
        baseService.print(1);//输出baseService print method:1
    }
}
```
#### 成员方法重载

特性：
- 与父类的方法名一致
- 返回类型和参数个数一致
- 方法体可以不一致

示例：

BaseService.java类

```java
public class BaseService {

    /**
    * 用于作比较
    **/
    public void print(int a){
            System.out.println("baseService print method:"+a);
    }
}
```
重写save方法后的UserService类

```java
public class UserService extends BaseService {

    /**
     * 重载方法，返回类型不能作为重载的判断依据。
     * 参数列表个数或顺序不一致作为判断重载与重写的依据。
     * @param a
     * @param b
     */
    public void print(int a,int b){
        System.out.println("userService print method:{"+a+","+b+"}");
    }
}

```
执行结果：
```java
public class MethodTest {

    public static void main(String[]args){
        UserService userService = new UserService();
        userService.save();//输出userService invoke save method
        //重载后的方法很明显区分于父类的print方法
        userService.print(2,3);//输出userService print method:{2,3}

    }
}
```
#### 成员方法重写

特性：
- 与父类的方法名一致
- 返回类型没有识别判断的作用
- 方法体可以不一致
- 参数列表个数一致，个数或者顺序不一致都可以作为区分重写的依据

示例：

BaseService.java类

```java
public class BaseService {

    /**
    * 待重写的方法
    **/
    public void save(){
        System.out.println("baseService invoke save method:"+username);
    }
}
```
重写save方法后的UserService类

```java
public class UserService extends BaseService {

    /**
     * 重写方法
     */
    @Override
    public void save() {
        System.out.println("userService invoke save method");
    }

}

```

执行结果：
```java
public class MethodTest {

    public static void main(String[]args){
        UserService userService = new UserService();
        //重写后的save方法
        userService.save();//输出userService invoke save method
        //重载的print方法
        userService.print(2,3);//输出userService print method:{2,3}

        BaseService baseService1 = new UserService();
        baseService1.save();//输出userService invoke save method
        baseService1.print(4);//输出baseService print method:4
    }
}
```