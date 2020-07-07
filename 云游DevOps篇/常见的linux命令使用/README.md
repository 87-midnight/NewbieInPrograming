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


#### 安装lrzsz 接收、下载文件

```cmd
yum -y install lrzsz 
```

#### 解压、压缩文件

- tar.gz

```cmd
# 解压
tar -zxvf xxx.tar.gz
```

#### 防火墙设置

添加放行端口

```
firewall-cmd --zone=public --add-port=80/tcp --permanent 

firewall-cmd --reload
```
