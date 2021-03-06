###面试篇

#### JVM三大性能调优参数

- -Xss 规定了每个线程虚拟机栈(堆栈)的大小
- -Xms 堆的初始值
- -Xmx 堆能达到的最大值

#### JVM 几个重要的参数：

>- -server -Xmx3g -Xms3g -XX:MaxPermSize=128m
>- -XX:NewRatio=1 eden/old 的比例
>- -XX:SurvivorRatio=8 s/e的比例
>- -XX:+UseParallelGC
>- -XX:ParallelGCThreads=8
>- -XX:+UseParallelOldGC 这个是JAVA 6出现的参数选项
>- -XX:LargePageSizeInBytes=128m 内存页的大小， 不可设置过大， 会影响Perm的大小。
>- -XX:+UseFastAccessorMethods 原始类型的快速优化
>- -XX:+Disable‘’ExplicitGC 关闭System.gc()

####JVM调优

查看堆空间大小分配（年轻代、年老代、持久代分配）

垃圾回收监控（长时间监控回收情况）

线程信息监控：系统线程数量

线程状态监控：各个线程都处在什么样的状态下

线程详细信息：查看线程内部运行情况，死锁检查

CPU热点：检查系统哪些方法占用了大量CPU时间

内存热点：检查哪些对象在系统中数量最大

**jvm问题排查和调优：**

jps主要用来输出JVM中运行的进程状态信息。

jstat命令可以用于持续观察虚拟机内存中各个分区的使用率以及GC的统计数据

jmap可以用来查看堆内存的使用详情。

jstack可以用来查看Java进程内的线程堆栈信息。 jstack是个非常好用的工具，结合应用日志可以迅速定位到问题线程。

Java性能分析工具

jdk会自带JMC(JavaMissionControl)工具。可以分析本地应用以及连接远程ip使用。提供了实时分析线程、内存，CPU、GC等信息的可视化界面。

#### JVM内存调优

>对JVM内存的系统级的调优主要的目的是减少GC的频率和Full GC的次数。 过多的GC和Full GC是会占用很多的系统资源（主要是CPU），影响系统的吞吐量。
使用JDK提供的内存查看工具，比如JConsole和Java VisualVM。

1. 监控GC的状态，使用各种JVM工具，查看当前日志，并且分析当前堆内存快照和gc日志，根据实际的情况看是否需要优化。
2. 通过JMX的MBean或者Java的jmap生成当前的Heap信息，并使用Visual VM或者Eclipse自带的Mat分析dump文件
3. 如果参数设置合理，没有超时日志，GC频率GC耗时都不高则没有GC优化的必要，如果GC时间超过1秒或者频繁GC，则必须优化
4. 调整GC类型和内存分配，使用1台和多台机器进行测试，进行性能的对比。再做修改，最后通过不断的试验和试错，分析并找到最合适的参数

**导致Full GC一般由于以下几种情况：**

- 旧生代空间不足

>调优时尽量让对象在新生代GC时被回收、让对象在新生代多存活一段时间和不要创建过大的对象及数组避免直接在旧生代创建对象

- 新生代设置过小

>一是新生代GC次数非常频繁，增大系统消耗；二是导致大对象直接进入旧生代，占据了旧生代剩余空间，诱发Full GC

- 新生代设置过大

>一是新生代设置过大会导致旧生代过小（堆总量一定），从而诱发Full GC；二是新生代GC耗时大幅度增加

- Survivor设置过小

>导致对象从eden直接到达旧生代

- Survivor设置过大
>导致eden过小，增加了GC频率
一般说来新生代占整个堆1/3比较合适

GC策略的设置方式

1. 吞吐量优先 可由-XX:GCTimeRatio=n来设置
2. 暂停时间优先 可由-XX:MaxGCPauseRatio=n来设置

#### JVM内存管理

**JVM内存区域的划分：**

>- **程序计数器（PC，Program Counter Register）**。在 JVM 规范中，每个线程都有它自己的程序计数器，并且任何时间一个线程都只有一个方法在执行，也就是所谓的当前方法。程序计数器会存储当前线程正在执行的 Java 方法的 JVM 指令地址；或者，如果是在执行本地方法，则是未指定值（undefined）。（唯一不会抛出OutOfMemoryError）

>- **Java 虚拟机栈（Java Virtual Machine Stack）**，早期也叫 Java 栈。每个线程在创建时都会创建一个虚拟机栈，其内部保存一个个的栈帧（Stack Frame），对应着一次次的 Java 方法调用。
前面谈程序计数器时，提到了当前方法；同理，在一个时间点，对应的只会有一个活动的栈帧，通常叫作当前帧，方法所在的类叫作当前类。如果在该方法中调用了其他方法，对应的新的栈帧会被创建出来，成为新的当前帧，一直到它返回结果或者执行结束。JVM 直接对 Java 栈的操作只有两个，就是对栈帧的压栈和出栈。
栈帧中存储着局部变量表、操作数（operand）栈、动态链接、方法正常退出或者异常退出的定义等。
>- **堆（Heap）**，它是 Java 内存管理的核心区域，用来放置 Java 对象实例，几乎所有创建的 Java 对象实例都是被直接分配在堆上。堆被所有的线程共享，在虚拟机启动时，我们指定的“Xmx”之类参数就是用来指定最大堆空间等指标。
（ 编译器通过逃逸分析，确定对象是在栈上分配还是在堆上分配）
理所当然，堆也是垃圾收集器重点照顾的区域，所以堆内空间还会被不同的垃圾收集器进行进一步的细分，最有名的就是新生代、老年代的划分。
>- **方法区（Method Area）**。这也是所有线程共享的一块内存区域，用于存储所谓的元（Meta）数据，例如类结构信息，以及对应的运行时常量池、字段、方法代码等。
由于早期的 Hotspot JVM 实现，很多人习惯于将方法区称为永久代（Permanent Generation）。Oracle JDK 8 中将永久代移除，同时增加了元数据区（Metaspace）。
>- **运行时常量池（Run-Time Constant Pool）**，这是方法区的一部分。如果仔细分析过反编译的类文件结构，你能看到版本号、字段、方法、超类、接口等各种信息，还有一项信息就是常量池。Java 的常量池可以存放各种常量信息，不管是编译期生成的各种字面量，还是需要在运行时决定的符号引用，所以它比一般语言的符号表存储的信息更加宽泛。
>- **本地方法栈（Native Method Stack）**。它和 Java 虚拟机栈是非常相似的，支持对本地方法的调用，也是每个线程都会创建一个。在 Oracle Hotspot JVM 中，本地方法栈和 Java 虚拟机栈是在同一块儿区域，这完全取决于技术实现的决定，并未在规范中强制。

#### JVM的常见的垃圾收集器

>1. Serial：最古老的垃圾收集器，“Serial”体现在其收集工作是单线程的， 在进行垃圾收集过程中，会进入臭名昭著的“Stop-The-World”状态，一直是 Client 模式下 JVM 的默认选项。复制算法（-XX:+UseSerialGC）
Serial Old：老年代，采用了标记 - 整理（Mark-Compact）算法
>2. ParNew:新生代 GC 实现,是 Serial GC 的多线程版本，最常见的应用场景是配合老年代的 CMS GC 工作（-XX:+UseParNewGC）
>3. Parallel Scavenge： 新生代的多线程收集器（并行收集器） 其采用的是Copying算法，是 server 模式 JVM 的默认 GC 选择（-XX:+UseParallelGC）
>4. Parallel Old：并行运行；作用于老年代；标记-整理算法；吞吐量优先；适用于后台运算而不需要太多交互的场景。
>5. CMS：基于标记 - 清除（Mark-Sweep）算法，设计目标是尽量减少停顿时间，采用的标记 - 清除算法，存在着内存碎片化问题，所以难以避免在长时间运行等情况下发生 full GC（-XX:+UseConcMarkSweepGC），在 JDK 9 中被标记为废弃（deprecated）
>6. G1 垃圾收集器：
>   - G1 同样存在着年代的概念，但是与我前面介绍的内存结构很不一样，其内部是类似棋盘状的一个个 region 组成。
region 的大小是一致的，数值是在 1M 到 32M 字节之间的一个 2 的幂值数，JVM 会尽量划分 2048 个左右、同等大小的 region。当然这个数字既可以手动调整，G1 也会根据堆大小自动进行调整。在 G1 实现中，年代是个逻辑概念，具体体现在，一部分 region 是作为 Eden，一部分作为 Survivor，除了意料之中的 Old region，G1 会将超过 region 50% 大小的对象（在应用中，通常是 byte 或 char 数组）归类为 Humongous 对象，并放置在相应的 region 中。逻辑上，Humongous region 算是老年代的一部分，缺点：region 大小和大对象很难保证一致，这会导致空间的浪费。从 GC 算法的角度，G1 选择的是复合算法，可以简化理解为：
在新生代，G1 采用的仍然是并行的复制算法，所以同样会发生 Stop-The-World 的暂停。
在老年代，大部分情况下都是并发标记，而整理（Compact）则是和新生代 GC 时捎带进行，并且不是整体性的整理，而是增量进行的。习惯上人们喜欢把新生代 GC（Young GC）叫作 Minor GC，老年代 GC 叫作 Major GC，区别于整体性的 Full GC，但是现代 GC 中，这种概念已经不再准确，对于 G1 来说：Minor GC 仍然存在，虽然具体过程会有区别，会涉及 Remembered Set 等相关处理。老年代回收，则是依靠 Mixed GC。并发标记结束后，JVM 就有足够的信息进行垃圾收集，Mixed GC 不仅同时会清理 Eden、Survivor 区域，而且还会清理部分 Old 区域。有一个重点就是 Remembered Set，用于记录和维护 region 之间对象的引用关系。G1中提供了两种模式垃圾回收模式，Young GC和Mixed GC，两种都是Stop The World(STW)的。
>       1. YoungGC年轻代收集
在分配一般对象（非巨型对象）时，当所有eden region使用达到最大阀值并且无法申请足够内存时，会触发一次YoungGC。每次younggc会回收所有Eden以及Survivor区，并且将存活对象复制到Old区以及另一部分的Survivor区。
>       2. mixed gc
当越来越多的对象晋升到老年代old region时，为了避免堆内存被耗尽，虚拟机会触发一个混合的垃圾收集器，即mixed gc，该算法并不是一个old gc，除了回收整个young region，还会回收一部分的old region，这里需要注意：是一部分老年代，而不是全部老年代，可以选择哪些old region进行收集，从而可以对垃圾回收的耗时时间进行控制。
G1没有fullGC概念，需要fullGC时，调用serialOldGC进行全堆扫描（包括eden、survivor、o、perm）。

**G1收集器的阶段分以下几个步骤：**

1. 初始标记（它标记了从GC Root开始直接可达的对象）
2. 并发标记（从GC Roots开始对堆中对象进行可达性分析，找出存活对象）
3. 最终标记（标记那些在并发标记阶段发生变化的对象，将被回收）
4. 筛选回收（首先对各个Regin的回收价值和成本进行排序，根据用户所期待的GC停顿时间指定回收计划，回收一部分Region）

**GC调优：**

基本的调优思路可以总结为：

- 理解应用需求和问题，确定调优目标。评估用户可接受的响应时间和业务量，将目标简化为，希望 GC 暂停尽量控制在 200ms 以内，并且保证一定标准的吞吐量。
- 掌握 JVM 和 GC 的状态，定位具体的问题，确定真的有 GC 调优的必要。比如，通过 jstat 等工具查看 GC 等相关状态，可以开启 GC 日志，或者是利用操作系统提供的诊断工具等
- 选择的 GC 类型是否符合我们的应用特征，如果是，具体问题表现在哪里，是 Minor GC 过长
- 通过分析确定具体调整的参数或者软硬件配置。
- 验证是否达到调优目标，如果达到目标，即可以考虑结束调优；否则，重复完成分析、调整、验证这个过程。

***GC日志分析**

调优命令

调优工具

调优命令

>Sun JDK监控和故障处理命令有jps jstat jmap jhat jstack jinfo

- jps，JVM Process Status Tool,显示指定系统内所有的HotSpot虚拟机进程。
- jstat，JVM statistics Monitoring是用于监视虚拟机运行时状态信息的命令，它可以显示出虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数据。
- jmap，JVM Memory Map命令用于生成heap dump文件
- jhat，JVM Heap Analysis Tool命令是与jmap搭配使用，用来分析jmap生成的dump，jhat内置了一个微型的HTTP/HTML服务器，生成dump的分析结果后，可以在浏览器中查看
- jstack，用于生成java虚拟机当前时刻的线程快照。
- jinfo，JVM Configuration info 这个命令作用是实时查看和调整虚拟机运行参数。

调优工具

>- 常用调优工具分为两类,jdk自带监控工具：jconsole和jvisualvm，第三方有：MAT(Memory Analyzer Tool)、GChisto。
>- jconsole，Java Monitoring and Management Console是从java5开始，在JDK中自带的java监控和管理控制台，用于对JVM中内存，线程和类等的监控

#### GC触发的条件有两种

（1）程序调用System.gc时可以触发；（2）系统自身来决定GC触发的时机。

要完全回收一个对象，至少需要经过两次标记的过程。
第一次标记：对于一个没有其他引用的对象，筛选该对象是否有必要执行finalize()方法，如果没有执行必要，则意味可直接回收。（筛选依据：是否复写或执行过finalize()方法；因为finalize方法只能被执行一次）。
第二次标记：如果被筛选判定位有必要执行，则会放入FQueue队列，并自动创建一个低优先级的finalize线程来执行释放操作。如果在一个对象释放前被其他对象引用，则该对象会被移除FQueue队列。

#### Minor GC ，Full GC 触发条件

Minor GC触发条件：当Eden区满时，触发Minor GC。

Full GC触发条件：

（1）调用System.gc时，系统建议执行Full GC，但是不必然执行

（2）老年代空间不足

（3）方法区空间不足

（4）通过Minor GC后进入老年代的平均大小大于老年代的可用内存

（5）由Eden区、From Space区向To Sp3ace区复制时，对象大小大于To Space可存，则把该对象转存到老年代，且老年代的可用内存小于该对象大小

#### java内存模型

Java内存模型定义了多线程之间共享变量的可见性以及如何在需要的时候对共享变量进行同步。JMM 内部的实现通常是依赖于所谓的内存屏障，通过禁止某些重排序的方式，提供内存可见性保证，也就是实现了各种 happen-before 规则。

与JVM 内存模型不同。

Java内存模型即Java Memory Model，简称JMM。JMM定义了Java 虚拟机(JVM)在计算机内存(RAM)中的工作方式。JVM是整个计算机虚拟模型，所以JMM是隶属于JVM的。

Java内存模型定义了多线程之间共享变量的可见性以及如何在需要的时候对共享变量进行同步。

Java线程之间的通信采用的是过共享内存模型，这里提到的共享内存模型指的就是Java内存模型(简称JMM)，JMM决定一个线程对共享变量的写入何时对另一个线程可见。从抽象的角度来看，JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（main memory）中，每个线程都有一个私有的本地内存（local memory），本地内存中存储了该线程以读/写共享变量的副本。

#### java垃圾回收机制

###### 一.如何确定某个对象是“垃圾”？

1）引用计数法。（python）
2） 在Java中采取了 可达性分析法
通过一系列的“GC Roots”对象作为起点进行搜索，如果在“GC Roots”和一个对象之间没有可达路径，则称该对象是不可达的，不过要注意的是被判定为不可达的对象不一定就会成为可回收对象。被判定为不可达的对象要成为可回收对象必须至少经历两次标记过程，如果在这两次标记过程中仍然没有逃脱成为可回收对象的可能性，则基本上就真的成为可回收对象了。

###### 二.典型的垃圾收集算法
（标记-清除）算法 （复制）算法 （标记-整理）算法 （分代收集）算法

###### 三.典型的垃圾收集器

1.Serial：最古老的垃圾收集器，“Serial”体现在其收集工作是单线程的， 在进行垃圾收集过程中，会进入臭名昭著的“Stop-The-World”状态，一直是 Client 模式下 JVM 的默认选项。复制算法（-XX:+UseSerialGC）
Serial Old：老年代，采用了标记 - 整理（Mark-Compact）算法
　2.ParNew:新生代 GC 实现,是 Serial GC 的多线程版本，最常见的应用场景是配合老年代的 CMS GC 工作（-XX:+UseParNewGC）
3.Parallel Scavenge： 新生代的多线程收集器（并行收集器） 其采用的是Copying算法，是 server 模式 JVM 的默认 GC 选择（-XX:+UseParallelGC）
　4.Parallel Old：并行运行；作用于老年代；标记-整理算法；吞吐量优先；适用于后台运算而不需要太多交互的场景。
　5.CMS：基于标记 - 清除（Mark-Sweep）算法，设计目标是尽量减少停顿时间，采用的标记 - 清除算法，存在着内存碎片化问题，所以难以避免在长时间运行等情况下发生 full GC（-XX:+UseConcMarkSweepGC），在 JDK 9 中被标记为废弃（deprecated）
6.G1(重点讲，引用上面的G1)：兼顾吞吐量和停顿时间的 GC 实现，是 Oracle JDK 9 以后的默认 GC 选项。G1 可以直观的设定停顿时间的目标。
G1 同样存在着年代的概念，但是与我前面介绍的内存结构很不一样，其内部是类似棋盘状的一个个 region 组成。

region 的大小是一致的，数值是在 1M 到 32M 字节之间的一个 2 的幂值数，JVM 会尽量划分 2048 个左右、同等大小的 region。
当然这个数字既可以手动调整，G1 也会根据堆大小自动进行调整。在 G1 实现中，年代是个逻辑概念，具体体现在，一部分 region 是作为 Eden，一部分作为 Survivor，除了意料之中的 Old region，G1 会将超过 region 50% 大小的对象（在应用中，通常是 byte 或 char 数组）归类为 Humongous 对象，并放置在相应的 region 中。逻辑上，Humongous region 算是老年代的一部分，
缺点：region 大小和大对象很难保证一致，这会导致空间的浪费。
从 GC 算法的角度，G1 选择的是复合算法，可以简化理解为：
在新生代，G1 采用的仍然是并行的复制算法，所以同样会发生 Stop-The-World 的暂停。
在老年代，大部分情况下都是并发标记，而整理（Compact）则是和新生代 GC 时捎带进行，并且不是整体性的整理，而是增量进行的。

#### jvm怎样判断一个对象是否可回收，怎样的对象才能作为GC root

1)在Java中采取了 可达性分析法
通过一系列的“GC Roots”对象作为起点进行搜索，如果在“GC Roots”和一个对象之间没有可达路径，则称该对象是不可达的，不过要注意的是被判定为不可达的对象不一定就会成为可回收对象。被判定为不可达的对象要成为可回收对象必须至少经历两次标记过程，如果在这两次标记过程中仍然没有逃脱成为可回收对象的可能性，则基本上就真的成为可回收对象了。

2)虚拟机栈中引用的对象、方法区类静态属性引用的对象、方法区常量池引用的对象、本地方法栈JNI引用的对象


#### OOM说一下？怎么排查？哪些会导致OOM? OOM出现在什么时候

OOM，全称“Out Of Memory”，官方说明：当JVM因为没有足够的内存来为对象分配空间并且垃圾回收器也已经没有空间可回收时，就会抛出这个error。
（没有空闲内存，并且垃圾收集器也无法提供更多内存。）
怎么排查？
首先可以查看服务器运行日志以及项目记录的日志，捕捉到内存溢出异常。
核心系统日志文件
OOM出现在什么时候？哪些会导致OOM?
java堆内存溢出，此种情况最常见，一般由于内存泄露或者堆的大小设。置不当引起。 可以通过虚拟机参数-Xms,-Xmx等修改。
（1）java永久代溢出，即方法区溢出了，因为永久代的大小是有限的，并且 JVM 对永久代垃圾回收（如，常量池回收、卸载不再需要的类型）非常不积极，所以当我们不断添加新类型的时候，永久代出现 OutOfMemoryError 也非常多见 ，尤其是在运行时存在大量动态类型生成的场合；（ＪＤＫ　８　已经没有方法区了，改为元数据区）
（2）JAVA虚拟机栈溢出，不会抛OOM error，一般是由于程序中存在死循环或者深度递归调用造成的，栈大小设置太小也会出现此种溢出。可以通过虚拟机参数-Xss来设置栈的大小。程序不断的进行递归调用，而且没有退出条件，就会导致不断地进行压栈。类似这种情况，JVM 实际会抛出 StackOverFlowError；当然，如果 JVM 试图去扩展栈空间的的时候失败，则会抛出 OutOfMemoryError。
（３）直接内存不足，也会导致 OOM

#### 什么是Full GC？GC? major GC? stop the world

从年轻代空间（包括 Eden 和 Survivor 区域）回收内存被称为 Minor GC。
Minor GC触发条件：当Eden区满时，触发Minor GC。

Full GC 是清理整个堆空间—包括年轻代和老年代。
Full GC触发条件：
（1）调用System.gc时，系统建议执行Full GC，但是不必然执行
（2）老年代空间不足
（3）方法去空间不足
（4）通过Minor GC后进入老年代的平均大小大于老年代的可用内存
（5）由Eden区、From Space区向To Space区复制时，对象大小大于To Space可用内存，则把该对象转存到老年代，且老年代的可用内存小于该对象大小

GC，即就是Java垃圾回收机制。GC触发的条件有两种。（1）程序调用System.gc时可以触发；（2）系统自身来决定GC触发的时机。
两次标记的过程。
第一次标记：对于一个没有其他引用的对象，筛选该对象是否有必要执行finalize()方法，如果没有执行必要，则意味可直接回收。（筛选依据：是否复写或执行过finalize()方法；因为finalize方法只能被执行一次）。
第二次标记：如果被筛选判定位有必要执行，则会放入FQueue队列，并自动创建一个低优先级的finalize线程来执行释放操作。如果在一个对象释放前被其他对象引用，则该对象会被移除FQueue队列。

当程序运行到这些“安全点”(方法调用,循环跳转,异常跳转)的时候就会暂停所有当前运行的线程（Stop The World 所以叫STW）。
在GC发生时，直接把所有线程都挂起，然后检测所有线程是否都在安全点，如果不在安全点则恢复线程的执行，等执行到安全点再挂起。
VM会设置一个标志，当线程执行到安全点的时候会轮询检测这个标志，如果发现需要GC，则线程会自己挂起，直到GC结束才恢复运行

#### JVM中类加载机制，类加载过程，什么是双亲委派模型？ 类加载器有哪些

一般来说，我们把 Java 的类加载过程分为三个主要步骤：加载、链接、初始化，具体行为在Java 虚拟机规范里有非常详细的定义。

首先是加载阶段（Loading），它是 Java 将字节码数据从不同的数据源读取到 JVM 中，并映射为 JVM 认可的数据结构（Class 对象），这里的数据源可能是各种各样的形态，如 jar 文件、class 文件，甚至是网络数据源等；如果输入数据不是 ClassFile 的结构，则会抛出 ClassFormatError。

加载阶段是用户参与的阶段，我们可以自定义类加载器，去实现自己的类加载过程。
第二阶段是链接（Linking），这是核心的步骤，简单说是把原始的类定义信息平滑地转化入 JVM 运行的过程中。这里可进一步细分为三个步骤：

验证（Verification），这是虚拟机安全的重要保障，JVM 需要核验字节信息是符合 Java 虚拟机规范的，否则就被认为是 VerifyError，这样就防止了恶意信息或者不合规的信息危害 JVM 的运行，验证阶段有可能触发更多 class 的加载。

准备（Preparation），创建类或接口中的静态变量，并初始化静态变量的初始值。但这里的“初始化”和下面的显式初始化阶段是有区别的，侧重点在于分配所需要的内存空间，不会去执行更进一步的 JVM 指令。

解析（Resolution），在这一步会将常量池中的符号引用（symbolic reference）替换为直接引用。在Java 虚拟机规范中，详细介绍了类、接口、方法和字段等各个方面的解析。

最后是初始化阶段（initialization），这一步真正去执行类初始化的代码逻辑，包括静态字段赋值的动作，以及执行类定义中的静态初始化块内的逻辑，编译器在编译阶段就会把这部分逻辑整理好，父类型的初始化逻辑优先于当前类型的逻辑。
双亲委派模型：
再来谈谈双亲委派模型，简单说就是当类加载器（Class-Loader）试图加载某个类型的时候，除非父加载器找不到相应类型，否则尽量将这个任务代理给当前加载器的父加载器去做。使用委派模型的目的是避免重复加载 Java 类型。
类加载器有哪些：？
1.启动类加载器：加载 jre/lib 下面的 jar 文件
2.扩展类加载器，负责加载我们放到 jre/lib/ext/ 目录下面的 jar 包，
3.应用类加载器（Application or App Class-Loader），就是加载我们最熟悉的 classpath 的内容。

#### 如何判断是否有内存泄露？

泄露可以对比不同时间点内存分配，一般看用户类型的分配情况，什么在增加。具体，比如用jmap -histo:live 多次快照，然后对比差异，或者用jmc之类profiling工具，都可以进行，对比会更加流畅一些

**定位 Full GC 发生的原因，有哪些方式？**

1. 首先通过printgcdetail 查看fullgc频率以及时长
2. 通过dump 查看内存中哪些对象多，这些可能是引起fullgc的原因，看是否能优化
3. 如果堆大或者是生产环境，可以开起jmc 飞行一段时间，查看这期间的相关数据来订位问题

#### Java 中都有哪些引用类型？

强引用：发生 gc 的时候不会被回收。 new

软引用：有用但不是必须的对象，在发生内存溢出之前会被回收。SoftReference

弱引用：有用但不是必须的对象，在下一次GC时会被回收。WeakReference

虚引用（幽灵引用/幻影引用）：无法通过虚引用获得对象，用 PhantomReference 实现虚引用，虚引用的用途是在 gc 时返回一个通知。
PhantomReference pr = new PhantomReference (object, queue);



注：https://blog.csdn.net/Butterfly_resting/article/details/89705057