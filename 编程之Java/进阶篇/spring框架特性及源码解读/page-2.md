### spring 常见用法整理

#### 获取jar或war包内的资源

记录一下三种getResourceAsStream使用方式

Class.getResourceAsStream("")
从当前类的所在包下获取资源

Class.getResourceAsStream("/")
从classpath下获取资源，maven项目下，resources目录下的文件默认打包到classpath下

ClassLoader.getResourceAsStream()
不能以"/"开头，默认从classpath下获取

