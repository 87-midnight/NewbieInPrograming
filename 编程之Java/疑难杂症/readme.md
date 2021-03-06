### 文件/文件夹占用问题排查

问题描述：

    很多情况下，使用java的api删除文件或文件夹时，会出现删除失败，其中比较大概率的原因在于：
    1、文件夹不为空，需要遍历删除该文件夹下的文件后，才能删除文件夹。
    2、文件、文件夹被线程占用，比如打开了流却忘了关闭，比如解压文件时，忘记关闭

场景描述：
    
    1、对于一些复杂业务功能，其实现逻辑如果过于复杂，诸如几千行的代码？
    2、A接口上传了文件，保存在foler1目录，B接口去删除folder1时，发现删除失败，在window的窗体找到该目录来删除时，
    也提示文件夹或文件被占用。
    
排查手段：

    通常输入输出流打开了没关闭，很难定位到具体的代码。
    1、尝试在每一处操作io的代码逻辑后面，加一行System.gc()，手动执行垃圾回收器，这样会关掉io流，
    断点测试，在执行gc后，测试手动删除文件夹是否成功，成功则表示在此之前有未关闭的io流。通过这样的方式快速定位。
    2、（可能奏效的思路）可以dump内存信息出来，使用MAT工具分析其使用内存中所有的对象占比，找到跟xxxStream相关的对象，猜测其代码位置
    

### 排查java应用运行时线程占用资源过高问题

> 参考：https://blog.csdn.net/weixin_42523104/article/details/113534423

思路：

    1.根据top和ps命令查找到进程中CPU利用率最高的线程(内核级线程)
    2.将内核级线程的十进制转成十六进制
    3.根据jstack命令获取JVM级的线程信息

方法1：   
       
    1.通过top命令找到CPU消耗(%CPU列)最高的进程, 并记住PID
    2.通过top -Hp PID 找到CPU消耗(%CPU列)最高的线程, 并记住线程TID
      通过printf "%xn" 十进制线程TID # 将十进制转成十六进制
    3.通过jstack PID | grep 十六进制TID -A 30
    
方法2：

    1.通过top命令找到CPU消耗(%CPU列)最高的进程, 并记住PID
    2.通过ps -mp PID -o THREAD,tid,time 找到CPU消耗(%CPU列)最高的线程, 并记住线程TID
      通过printf "%xn" 十进制线程TID # 将十进制转成十六进制
    3.通过jstack PID | grep 十六进制TID -A 30
   