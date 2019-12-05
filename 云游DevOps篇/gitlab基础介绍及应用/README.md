### gitlab

#### centos下安装

拉取镜像

```
docker pull gitlab/gitlab-ce
```

创建存储目录

```
mkdir -p /srv/gitlab/config
mkdir -p /srv/gitlab/logs 
mkdir -p /srv/gitlab/data
```

启动镜像

```
docker run --detach \
  --hostname gitlab.example.com \
  --publish 8443:443 --publish 8880:80 --publish 8222:22 \
  --name gitlab \
  --restart always \
  --volume /srv/gitlab/config:/etc/gitlab \
  --volume /srv/gitlab/logs:/var/log/gitlab \
  --volume /srv/gitlab/data:/var/opt/gitlab \
  --privileged=true \
  gitlab/gitlab-ce:latest
```