#### map原理及常用类应用

map是独立接口，与collection无关联。

map的实现类包括HashTable，LinkedHashMap，HashMap，TreeMap

##### 特点

- TreeMap是有序的，HashMap和HashTable是无序的。
- HashTable的方法是同步的，HashMap的方法不是同步的。这是两者最主要的区别。
- HashTable是线程安全的，HashMap不是线程安全的。
- HashMap效率较高，HashTable效率较低。
- 如果对同步性或与遗留代码的兼容性没有任何要求，建议使用HashMap。 查看HashTable的源代码就可以发现，除构造函数外，Hashtable的所有 public 方法声明中都有 synchronized关键字，而HashMap的源码中则没有。
- HashTable不允许null值，HashMap允许null值（key和value都允许）
- 父类不同：HashTable的父类是Dictionary，HashMap的父类是AbstractMap

###### linkHashMap特点

相比于HashMap：迭代HashMap的顺序并不是HashMap放置的顺序，也就是无序。
而LinkedHashMap，它虽然增加了时间和空间上的开销，通过一个双向链表，
LinkedHashMap保证了元素迭代的顺序。该迭代顺序有两种，可以是插入顺序或者是访问顺序。
LinkedHashMap继承了HashMap类，有着HashMap的所有功能，还提供了记住元素添加顺序的方法。


1. LinkedHashMap继承了HashMap ，实现了Clonable ，serialiable（可序列化） ， map接口；
2. 提供了AccessOrder参数，用来指定LinkedHashMap的排序方式，
accessOrder =false -> 插入顺序进行排序 ， accessOrder = true -> 访问顺序进行排序。
3. linkedHashMap虽然继承HashMap， 但实现了双线链表 ，有固定的顺序，与插入entry的顺序一样；而HashMap存储的方式是无序
4. LinkedHashMap包含removeEldestEntry()方法，而HashMap则没有；
5. Key和Value都允许空;Key重复会覆盖、Value允许重复
6. 非线程安全
7. 都实现了Clonable ，serialiable（可序列化） ， map接口；


#### concurrentMap原理及应用
