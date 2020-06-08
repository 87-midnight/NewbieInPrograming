## gitlab-runner 使用教程

### centos 官方仓库安装

- 脚本下载

```cmd
curl -L https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.rpm.sh | sudo bash
```

- yum 安装

```commandline
yum install gitlab-runner
```

### gitlab-runner 两种注册gitlab方式

- specific，为某个具体的项目注册runner

>获取某个项目的token

- shared，全局共享的runner

>获取全局下的token

- 注册步骤

>-      gitlab-runner register
>- 输入gitlab的服务URL，http://ip:port
>- 输入gitlab-ci的Toekn
>- 关于集成服务中对于这个runner的描述
>- 给这个gitlab-runner输入一个标记，这个tag非常重要，在后续的使用过程中需要使用这个tag来指定gitlab-runner
是否运行在没有tag的build上面。在配置gitlab-ci的时候，会有很多job，每个job可以通过tags属性来选择runner。这里为true表示如果job没有配置tags，也执行
是否锁定runner到当前项目
>- 选择执行器，gitlab-runner实现了很多执行器，可用在不同场景中运行构建，详情可见GitLab Runner Executors


#### gitlab-runner之 runner-executor使用

> 在注册时，会有以下的几种executor提供使用。

Executor|	SSH	Shell|	VirtualBox|	Parallels|	Docker|	Kubernetes|	Custom|
|:---|:---:|:---:|:---:|:---:|:---:|:---:|
Clean build environment for every build|	✗|	✗|	✓|	✓|	✓|	✓|	conditional (4)
Reuse previous clone if it exists|	✓|	✓|	✗|	✗	✓|	✗|	conditional (4)
Runner file system access protected (5)|	✓|	✗|	✓|	✓|	✓|	✓|	conditional
Migrate runner machine|	✗|	✗|	partial|	partial|	✓|	✓|	✓
Zero-configuration support for concurrent builds|	✗|	✗ (1)|	✓|	✓|	✓|	✓|	conditional (4)
Complicated build environments|	✗|	✗ (2)|	✓ (3)	|✓ (3)	✓|	✓	|✓
Debugging build problems|	easy|	easy|	hard|	hard|	medium|	medium|	medium

- shell