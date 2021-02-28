## centos 虚拟机应用

### centos 7 安装


### 更改默认的源

- 备份
	
       mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup

- 下载

```cmd
# centos-7 aliyun
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

# centos-7 163 yun
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.163.com/.help/CentOS5-Base-163.repo


```

- 清缓存和重新生成

```
# 清除缓存
yum clean all
# 生存缓存
yum makecache
```

### centos && vm虚拟机网络配置

#### 静态配置方式

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

#### 一键初始化ip及ssh及yum

```shell script
cat >/etc/sysconfig/network-scripts/ifcfg-ens33<<EOF
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
IPADDR=192.168.31.134
GATEWAY=192.168.31.1
NETMASK=255.255.255.0
DNS1=8.8.8.8
DNS2=114.114.114.114
IPV6_PEERDNS=yes
IPV6_PEERROUTES=yes
IPV6_PRIVACY=no
EOF
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum clean all && yum makecache
yum install -y git net-tools lrzsz

systemctl restart network
```