## 适配器模式

- 结构
    - 目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或接口。
    - 适配者（Adaptee）类：它是被访问和适配的现存组件库中的组件接口。
    - 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。

- 应用
    1. 系统需要使用现有的类，而此类的接口不符合系统的需要。
    
    2. 想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类，包括一些可能在将来引进的类一起工作，这些源类不一定有一致的接口。
    
    3. 通过接口转换，将一个类插入另一个类系中。
 
### 示例

Foreigner为目标接口
```java
public interface Foreigner {

    void chatInEnglish();
}
```
EnglishClassroom为客户端，持有Foreigner接口标准
```java
public class EnglishClassroom {

    private Foreigner foreigner;

    public void setForeigner(Foreigner foreigner) {
        this.foreigner = foreigner;
    }

    public void communicateInEnglish(){
        foreigner.chatInEnglish();
    }
}
```

ForeignerStudent为Foreigner的实现类，符合EnglishClassroom的标准，
```java
public class ForeignerStudent implements Foreigner {

    public void chatInEnglish() {
        System.out.println("I'm speaking in english");
    }
}
```
可直接在课堂上发言。

ChineseStudent为适配者，并且有自己的独立接口。
```java
public class ChineseStudent implements Chinese {
    public void chatInChinese() {
        System.out.println("我是说中文的");
    }
}

```
由于没有符合EnglishClassroom的标准，故ChineseStudent不能直接发言。
ChineseAdapter为适配器，实现了Foreigner接口，符合EnglishClassroom的标准，其包含了ChineseStudent，可以
帮助ChineseStudent完成发言行为
```java
public class ChineseAdapter implements Foreigner {

    private Chinese chinese;

    public ChineseAdapter(Chinese chinese) {
        this.chinese = chinese;
    }

    public void chatInEnglish() {
        System.out.print("我能让说中文的人在英语课堂上与外国人交流:\t");
        chinese.chatInChinese();
    }
}
```
ChineseStudent通过适配器可以在EnglishClassroom发言，ForeignerStudent也能听得懂

日常生活中，中文系学生想要和英文系学生直接面对面交流，需要一个适配器同时无缝衔接转换

FaceToFaceAdapter适配器，同时实现Chinese，Foreigner,包含对两个不同语言的转换和调用
```java
public class FaceToFaceAdapter implements Chinese,Foreigner {

    private Chinese chinese;
    private Foreigner foreigner;

    public void setChinese(Chinese chinese) {
        this.chinese = chinese;
    }

    public void setForeigner(Foreigner foreigner) {
        this.foreigner = foreigner;
    }

    public void chatInChinese() {
        foreigner.chatInEnglish();
    }

    public void chatInEnglish() {
        chinese.chatInChinese();
    }
}
```
互相交流(调用),达到双向适配器模式
```java
public class DailyLife {

    public static void main(String...args){
        Foreigner A = new ForeignerStudent();
        Chinese B = new ChineseStudent();

        FaceToFaceAdapter faceToFace = new FaceToFaceAdapter();
        faceToFace.setChinese(B);
        faceToFace.chatInEnglish();

        faceToFace.setForeigner(A);
        faceToFace.chatInChinese();
    }
}
```