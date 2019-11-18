### 内部类分类及使用

示例：[点我查看](../../../../../code/oo-sample)

内部类包含了嵌套内部类、匿名内部类,静态内部类。

具体示例位于com.lcg.sample.clazz包下。

#### 嵌套内部类

示例：

InnerClass定义了一个Student的成员变量

Student类位于InnerClass内部，使用protect修饰类访问权限，定义public的空构造函数。

InnerClass定义一个方法用于返回一个Student的实例化对象，由于Student无法在外部使用。

```java
public class InnerClass {
    
    private Student student;
    
    public Student newStudent(long userId){
            Student student = new Student();
            student.setUserId(userId);
            return student;
        }
        
        class Student{
            private Long userId;
    
            public Long getUserId() {
                return userId;
            }
    
            public void setUserId(Long userId) {
                this.userId = userId;
            }
        }
}
```

位于com.lcg.sample.clazz.ext下的SubClass继承了InnerClass，由于InnerClass里
的Student修饰为protect，可以在子类访问到。Student定义了public的构造方法，故可以在
子类使用。如果构造方法不定义为public，则无法使用。

```java
public class SubClass extends InnerClass {
    
    public void test(){
        Student student = new Student();
    }
}
```

#### 静态内部类

示例：

InnerClass定义了一个Teacher的成员变量

Teacher类位于InnerClass内部，修饰为静态，且为public

```java
public class InnerClass {
    
    private Teacher teacher;
    
    public static class Teacher{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
```

#### 匿名内部类

通常是抽象类或接口类。

定义一个接口类Classroom

```java
public interface Classroom {
    
    void beginClass();
}
```

使用时，则实例化的部分称为匿名内部类

```java
public class ClazzTest {
    
    public static void main(String...args){
        //实例化部分为匿名内部类
        Classroom classroom = new Classroom() {
            public void beginClass() {
                System.out.println("the class is beginning");
            }
        };
    }
}
```

