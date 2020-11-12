
#### ClassLoader 分类

- Bootstrap ClassLoader

加载JVM自身工作需要的类，它由JVM自己实现。它会加载$JAVA_HOME/jre/lib下的文件

- ExtClassLoader

它是JVM的一部分，由sun.misc.LauncherJAVA_HOME/jre/lib/ext目录中的文件（或由System.getProperty("java.ext.dirs")所指定的文件）。

- AppClassLoader

应用类加载器，我们工作中接触最多的也是这个类加载器，它由sun.misc.Launcher$AppClassLoader实现。它加载由System.getProperty("java.class.path")指定目录下的文件，也就是我们通常说的classpath路径。


> PS: 参考 https://juejin.im/post/6844903794627608589
>https://blog.csdn.net/javazejian/article/details/73413292