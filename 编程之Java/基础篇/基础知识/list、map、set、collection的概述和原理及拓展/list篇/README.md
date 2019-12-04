#### list原理及常用类应用

list是接口，继承于collection。

list包含常见的ArrayList，Vector，LinkedList。

##### 特点

>List 有序,可重复

###### ArrayList

>- 优点: 底层数据结构是数组，查询快，增删慢。
>- 缺点: 线程不安全，效率高
###### Vector

优点: 底层数据结构是数组，查询快，增删慢。

缺点: 线程安全，效率低
######LinkedList

优点: 底层数据结构是链表，查询慢，增删快。

缺点: 线程不安全，效率高

#### List集合常见的方法介绍

##### contains

contains本质上是调用indexOf方法。

>- **注意:** 如果list数据量非常大的时候，在使用contains查找是否包含元素时，会非常耗时。
因为本质上是循环对象数组里的元素。
>- 解决方案，使用HashSet来代替

##### indexOf

