## centos版本

### 修改远程ssh连接配置

#### 查看是否已安装ssh服务

>yum list installed | grep openssh-server

`安装ssh服务`

> yum install openssh-server


`修改基础配置`

> vi /etc/ssh/sshd_config

去掉注释或者修改配置
>Port 22 # 远程连接的端口，可修改为其他端口
>ListenAddress 0.0.0.0 # 去掉注释
>ListenAddress :: # 去掉注释
>PermitRootLogin yes # 开启允许远程登录
>PasswordAuthentication yes # 开启使用用户名密码来作为连接验证


#### 安装netstat命令

>yum install net-tools


