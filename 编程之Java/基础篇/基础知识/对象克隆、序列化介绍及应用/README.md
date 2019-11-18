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

### 序列化、反序列化