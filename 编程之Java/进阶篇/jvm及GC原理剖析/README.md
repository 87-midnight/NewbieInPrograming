### Sun JDK监控和故障处理命令

#### jstat

格式：`jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]`

分析进程id为31736 的gc情况，每隔1000ms打印一次记录，打印10次停止，每3行后打印指标头部:

    jstat -gc -h3 31736 1000 10

`jstat -gc pid`

|参数|	描述|
|:----:|:----:|
|S0C	|年轻代中第一个survivor（幸存区）的容量 (字节)
|S1C	|年轻代中第二个survivor（幸存区）的容量 (字节)
|S0U	|年轻代中第一个survivor（幸存区）目前已使用空间 (字节)
|S1U	|年轻代中第二个survivor（幸存区）目前已使用空间 (字节)
|EC	|年轻代中Eden（伊甸园）的容量 (字节)
|EU	|年轻代中Eden（伊甸园）目前已使用空间 (字节)
|OC	|Old代的容量 (字节)
|OU	|Old代目前已使用空间 (字节)
|PC	|Perm(持久代)的容量 (字节)
|PU	|Perm(持久代)目前已使用空间 (字节)
|YGC	|从应用程序启动到采样时年轻代中gc次数
|YGCT	|从应用程序启动到采样时年轻代中gc所用时间(s)
|FGC	|从应用程序启动到采样时old代(全gc)gc次数
|FGCT	|从应用程序启动到采样时old代(全gc)gc所用时间(s)
|GCT	|从应用程序启动到采样时gc用的总时间(s)

#### jmap

`jmap -heap [pid]`

>查看整个JVM内存状态

>要注意的是在使用CMS GC 情况下，jmap -heap的执行有可能会导致Java 进程挂起

https://www.cnblogs.com/handsomeye/p/5442879.html