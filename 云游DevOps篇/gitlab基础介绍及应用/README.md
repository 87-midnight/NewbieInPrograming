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

#### gitlab-runner 安装

`通过docker`

`拉取镜像`

```cmd
docker pull gitlab/gitlab-runner:latest
```

>docker run -d --name gitlab-runner --restart always \
   -v /srv/gitlab-runner/config:/etc/gitlab-runner \
   -v /var/run/docker.sock:/var/run/docker.sock \
   gitlab/gitlab-runner:latest


#### gitlab-runner 注册gitlab和k8s

```cmd
kubectl cluster-info | grep 'Kubernetes master' | awk '/http/ {print $NF}'

#获取kubernetes api地址
```

`gitlab-admin-service-account.yaml配置内容，用于创建用户`

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: gitlab-admin
  namespace: kube-system
---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: ClusterRoleBinding
metadata:
  name: gitlab-admin
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: gitlab-admin
  namespace: kube-system

```

`在kube里面创建serviceAccount,获取名称和token`
```cmd
kubectl apply -f gitlab-admin-service-account.yaml

#结果
serviceaccount/gitlab-admin created
clusterrolebinding.rbac.authorization.k8s.io/gitlab-admin created


`获取默认秘钥的名称`
```cmd
kubectl get secret
```

`通过上述的命令，拿到Name的值，放到下面的secret后面，使用kubectl 创建ca证书`

```cmd
kubectl get secret default-token-ss8gj -o jsonpath="{['data']['ca\.crt']}" | base64 --decode
```

`获取service token`

```cmd
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep gitlab-admin | awk '{print $1}')
```

`进入gitlab-runner容器，执行以下命令`

```
gitlab-runner register \
  --non-interactive \
  --url "http://gitlab-host" \
  --registration-token "token" \
  --executor "kubernetes" \
  --description "kubernetes-runner" \
  --tag-list "kubernetes-runner" \
  --kubernetes-host "https://host:6443" \
  --kubernetes-ca-file "/ca.crt" \
  --kubernetes-bearer_token ""
```

注册成功后，保存，在高级选项那里选择administrator