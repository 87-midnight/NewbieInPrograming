## gitlab 使用教程

### 安装方式

#### centos 官方仓库安装

- 添加仓库

```
curl -L https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.rpm.sh | sudo bash
```

- 安装 postfix

```
 yum install postfix
 systemctl enable postfix
 systemctl start postfix
```

- 安装gitlab-ce

```
yum install gitlab-ce
```

- 编辑/etc/gitlab/gitlab.rb

```
nginx['enable'] = true
nginx['listen_port'] = 8888
external_url 'http://ip:8888'
```

- 保存配置

```
gitlab-ctl reconfigure
gitlab-ctl restart
gitlab-ctl status
```

- 修改管理员密码

```
gitlab-rails console -e production

Loading production environment (Rails 6.0.2)
irb(main):001:0> user = User.where(id: 1).first
=> # <User id:1 @root=>
irb(main):002:0> user.password = '123456'
=> "123456"
irb(main):003:0> user.password_confirmation = '123456'
=> "123456"
irb(main):004:0> user.save!
Enqueued ActionMailer::DeliveryJob (Job ID: f1b67889-3321-464f-9c52-35e5dfdfe3ab) to Sidekiq(mailers) with arguments: "DeviseMailer", "password_change", "deliver_now", #> <URI::GID gid://gitlab/User/1>>
=> true
irb(main):005:0> exit
```


