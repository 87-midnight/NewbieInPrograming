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


#### centos && vm虚拟机网络配置

##### 静态配置方式

- 网卡选择桥接模式，独立分配ip

- 修改/etc/sysconfig/network-scripts/ifcfg-ens33

- IPADDR为宿主机的ip段，网关子网与宿主机一样。
```
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=cfa52298-c719-46fd-8512-dc944ac26567
DEVICE=ens33
ONBOOT=yes
IPADDR=192.168.31.131
GATEWAY=192.168.31.1
NETMASK=255.255.255.0
DNS1=8.8.8.8
DNS2=114.114.114.114
IPV6_PEERDNS=yes
IPV6_PEERROUTES=yes
IPV6_PRIVACY=no

```