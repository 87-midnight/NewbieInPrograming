### influx 安装

#### Debain & Ubuntu

Debian和Ubuntu用户可以直接用apt-get包管理来安装最新版本的InfluxDB。

对于Ubuntu用户，可以用下面的命令添加InfluxDB的仓库
```cmd
curl -sL https://repos.influxdata.com/influxdb.key | sudo apt-key add -
source /etc/lsb-release
echo "deb https://repos.influxdata.com/${DISTRIB_ID,,} ${DISTRIB_CODENAME} stable" | sudo tee /etc/apt/sources.list.d/influxdb.list
```
Debian用户用下面的命令：
```cmd
curl -sL https://repos.influxdata.com/influxdb.key | sudo apt-key add -
source /etc/os-release
test $VERSION_ID = "7" && echo "deb https://repos.influxdata.com/debian wheezy stable" | sudo tee /etc/apt/sources.list.d/influxdb.list
test $VERSION_ID = "8" && echo "deb https://repos.influxdata.com/debian jessie stable" | sudo tee /etc/apt/sources.list.d/influxdb.list
```

安装、启动influxdb：
```cmd
sudo apt-get update && sudo apt-get install influxdb
sudo service influxdb start
```
如果你的系统可以使用Systemd(比如Ubuntu 15.04+, Debian 8+），也可以这样启动：
```cmd
sudo apt-get update && sudo apt-get install influxdb
sudo systemctl start influxdb
```
#### RedHat and CentOS

RedHat和CentOS用户可以直接用yum包管理来安装最新版本的InfluxDB。

```cmd
cat <<EOF | sudo tee /etc/yum.repos.d/influxdb.repo
[influxdb]
name = InfluxDB Repository - RHEL \$releasever
baseurl = https://repos.influxdata.com/rhel/\$releasever/\$basearch/stable
enabled = 1
gpgcheck = 1
gpgkey = https://repos.influxdata.com/influxdb.key
EOF
```
一旦加到了yum源里面，就可以运行下面的命令来安装和启动InfluxDB服务：

```cmd
sudo yum install influxdb
sudo service influxdb start
```

如果你的系统可以使用Systemd(比如CentOS 7+, RHEL 7+），也可以这样启动：

```cmd
sudo yum install influxdb
sudo systemctl start influxdb
```

#### MAC OS X

OS X 10.8或者更高版本的用户，可以使用Homebrew来安装InfluxDB; 一旦brew安装了，可以用下面的命令来安装InfluxDB：

```cmd
brew update
brew install influxdb
```
登陆后在用launchd开始运行InfluxDB之前，先跑：
```cmd
ln -sfv /usr/local/opt/influxdb/*.plist ~/Library/LaunchAgents
```
然后运行InfluxDB：
```cmd
launchctl load ~/Library/LaunchAgents/homebrew.mxcl.influxdb.plist
```
如果你不想用或是不需要launchctl，你可以直接在terminal里运行下面命令来启动InfluxDB：
```cmd
influxd -config /usr/local/etc/influxdb.conf
```

#### 配置文件讲解及使用

查看influx默认配置

```cmd
influxd config
```
用自定义的配置文件来运行InfluxDB：

- 运行的时候通过可选参数-config来指定：
    -  ```cmd
       influxd -config /etc/influxdb/influxdb.conf
       ```
- 设置环境变量INFLUXDB_CONFIG_PATH来指定
    - ```cmd
       echo $INFLUXDB_CONFIG_PATH
       /etc/influxdb/influxdb.conf
       
       influxd
      ```
      
#### 配置文件属性

