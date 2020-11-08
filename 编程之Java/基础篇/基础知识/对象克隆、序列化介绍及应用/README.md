### 对象克隆与序列化

示例：[点我查看](../../../../code/clone-serializable-sample)

对象克隆实现方式：

##### 实现Cloneable接口并重写Object类中的clone()方法；

###### 深拷贝

深拷贝与浅拷贝的区别在于，目标类里是否包含了成员对象a且类A是否实现了cloneable接口，浅拷贝不需要考虑实现。深拷贝则在自身的clone方法
里调用A的clone方法，赋值给自身的成员变量a，这样就达到了深拷贝目的。

human.java

定义一个成员对象HumanBeing,HumanBeing实现cloneable。

在Human的clone方法里，设置being的值为HumanBeing调用clone方法返回的对象。

```java
public class Human implements Cloneable {

    private String name;
    private HumanBeing being;


    public HumanBeing getBeing() {
        return being;
    }

    public void setBeing(HumanBeing being) {
        this.being = being;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Human clone = (Human) super.clone();
        //深度拷贝，当human里包含成员对象时，则该对象的类需实现cloneable接口，重写clone方法
        clone.being = (HumanBeing) being.clone();
        return clone;
    }

    public static class HumanBeing implements Cloneable{
        private Integer age;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
```

###### 浅拷贝

浅拷贝，附带成员对象或者不附带。如果附带了成员对象，则该成员类定义不实现cloneable。目标类的clone方法直接
调用父类clone方法即可，不需要额外处理成员对象。

Female.java

定义成员对象GIrl

Female本身实现cloneable接口，重写clone方法，啥都不处理。
```java
public class Female implements Cloneable {

    private String id;

    private Girl girl;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static class Girl{
        private String feature;

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }
    }
}
```


###### 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆

参与的角色：

- ByteArrayOutputStream

作用：用于实例化ObjectOutputStream，存储对象流

- ObjectOutputStream

用于写入流，将对象写入字节数组流

- ByteArrayInputStream

读取输出流的字节数组，准备就绪

- ObjectInputStream

用于流读取，从ByteArrayInputStream里读取字节数组，自身调用readObject返回一个深度克隆的对象。


```java
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
```

`序列化、反序列化示例`

```java
public class SerializeTest {

    @Getter
    @Setter
    public static class User implements Serializable{
        private String name;
        private String address;
    }
    @Getter
    @Setter
    public static class Student implements Serializable{
        private String sn;
        private User info;

    }

    public static void main(String[] args)throws Exception {
        Student origin = new Student();
        origin.setSn("123456");
        User info = new User();
        info.setName("tom");
        info.setAddress("test");
        origin.setInfo(info);
        try(
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            ) {
            //序列化，写入字节数组输出流
            objectOutputStream.writeObject(origin);
            
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            //反序列化，从字节数组输入流读取对象的字节数组，转换为一个新的对象，深拷贝
            Student copy = (Student) objectInputStream.readObject();
            System.out.println("地址比较结果："+ Objects.equals(origin,copy));
        }catch (Exception var1){
            var1.printStackTrace();
        }


    }
}

```

### 序列化框架介绍

https://blog.csdn.net/qq_34173549/article/details/79661939


### 为什么要实现Serializable

>总的就是说安全性问题，具体原因见解释：假如没有一个接口（即没有Serializable来标记是否可以序列化），让所有对象都可以序列化。那么所有对象通过序列化存储到硬盘上后，都可以在序列化得到的文件中看到属性对应的值（后面将会通过代码展示）。所以最后为了安全性（即不让一些对象中私有属性的值被外露），不能让所有对象都可以序列化。要让用户自己来选择是否可以序列化，因此需要一个接口来标记该类是否可序列化。

### 思考

**进行序列化反序列化时，实现Serializable的类的成员类是否可以不用实现Serializable？**

>- 否，必须实现。
>- 如果成员类不实现，则会报错，java.io.NotSerializableException


**如果一个对象需要序列化，但是又有一些私密信息不想持久化呢？**
>使用static、transient