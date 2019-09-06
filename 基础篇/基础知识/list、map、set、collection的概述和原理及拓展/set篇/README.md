#### set原理及常用类应用

set是接口，继承collection。

set常见的实现类有HashSet，LinkedHashSet，TreeSet

##### 特点

Set 无序,唯一

######HashSet
底层数据结构是哈希表。(无序,唯一)

如何来保证元素唯一性?
1. 依赖两个方法：hashCode()和equals()

######LinkedHashSet
底层数据结构是链表和哈希表。(FIFO插入有序,唯一)
1. 由链表保证元素有序
2. 由哈希表保证元素唯一

######TreeSet
底层数据结构是红黑树。(唯一，有序)
1. 如何保证元素排序的呢?

自然排序

比较器排序

2. 如何保证元素唯一性的呢?

根据比较的返回值是否是0来决定

