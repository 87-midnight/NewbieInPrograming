## grafana 安装使用

- 拉取镜像

```cmd
docker pull grafana/grafana:latest
```

- 创建挂载目录

```cmd
mkdir -p /usr/local/grafana-storage
chmod 777 /usr/local/grafana-storage
```

- 启动服务

```cmd
docker run -d \
  --user root \
  -p 3000:3000 \
  --name=grafana \
  -v /usr/local/grafana-storage:/var/lib/grafana \
  --restart=always \
  --privileged=true \
  --name grafana \
  grafana/grafana
```

- 访问主页

> - http://ip:port
> - 默认登录用户密码都是admin

- 下载汉化的prometheus，导入

`https://grafana.com/grafana/dashboards/8919/revisions`

下载最新的json文件

![](./static/prometheus-import.png)

导入文件，保存。

效果如图所示：

![监控首页](./static/prometheus-home.png)

### grafana 官网dashboard搜索

地址：https://grafana.com/grafana/dashboards?orderBy=name&direction=asc

>- 查找适合的prometheus dashboard，获取其id，然后在import界面导入
>- 比如需要监控mysql服务
>- 首先安装prometheus/mysql-exporter镜像，配置好连接信息
>- 重启prometheus服务
>- 最后再使用合适的mysql-dashboard来导入