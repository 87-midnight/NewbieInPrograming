## node-exporter 安装使用

- docker拉取镜像

```cmd
docker pull prom/node-exporter
```

- 启动node_exporter容器

```cmd
docker run -d -p 9100:9100 \
 --user root \
  -v "/proc:/host/proc:ro" \
  -v "/sys:/host/sys:ro" \
  -v "/:/rootfs:ro" \
  --net="host" \
  --restart=always \
  --privileged=true \
  --name node-exporter \
  prom/node-exporter
```