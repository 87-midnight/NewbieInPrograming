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

由于端口变动，在创建项目后，clone的地址是不带端口的，需要以下操作：

```
cd /srv/gitlab/data/gitlab-rails/etc
vi gitlab.yml
```
修改gitlab.host或gitlab.port为运行镜像时指定的http端口。

紧接着不要重启gitlab了，这样会还原配置的。

```
docker exec -it gitlab bash
gitlab-ctl restart
```
这样重启就能生效并且不会还原配置


邮箱配置：
修改gitlab.rb
```
gitlab_rails['gitlab_email_from'] = 'xxx@163.com'
gitlab_rails['gitlab_email_display_name'] = '标题显示的名称'
gitlab_rails['smtp_enable'] = true
gitlab_rails['smtp_address'] = "smtp.163.com"
gitlab_rails['smtp_port'] = 465
gitlab_rails['smtp_user_name'] = "xxx@163.com"
gitlab_rails['smtp_password'] = "SMTP设置的秘钥"
gitlab_rails['smtp_domain'] = "163.com"
gitlab_rails['smtp_authentication'] = "login"
gitlab_rails['smtp_enable_starttls_auto'] = true
gitlab_rails['smtp_tls'] = true //如果25端口被占用了，就启用465端口，然后把这个属性设为true
```
进入容器里，执行以下命令验证：

```
gitlab-rails console
```
进入rails命令行后
```
Notify.test_email('destination_email@address.com', 'Message Subject', 'Message Body').deliver_now
```
