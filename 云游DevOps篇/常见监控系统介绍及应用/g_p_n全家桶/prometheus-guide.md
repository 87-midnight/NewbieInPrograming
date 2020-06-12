## prometheus 安装使用

```cmd
docker pull prom/prometheus:latest
```

- 创建配置文件

```cmd
vi /usr/local/etc/prometheus.yml
```

```yaml
global:
  scrape_interval:     60s
  evaluation_interval: 60s
 
scrape_configs:
  - job_name: prometheus
    static_configs:
      - targets: ['localhost:9090']
        labels:
          instance: prometheus
 
  - job_name: localhost
    static_configs:
      - targets: ['192.168.1.221:9100']
        labels:
          instance: localhost
```

- 启动服务

```cmd
docker run  -d \
  --user root \
  -p 9090:9090 \
  -v /usr/local/etc/prometheus.yml:/etc/prometheus/prometheus.yml  \
  --restart=always \
  --privileged=true \
  --name prometheus \
  prom/prometheus
```