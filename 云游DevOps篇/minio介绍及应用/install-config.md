## Minio

### window 安装启动

命令行运行

    minio.exe server .\data

- 指定 server参数
- 指定文件存储路径

#### window 配置

```
# 配置用户名
set MINIO_ACCESS_KEY=admin
# 配置登录密码
set MINIO_SECRET_KEY=12345678
# 配置prometheus访问minio时的认证类型为public，即可无密码访问
set MINIO_PROMETHEUS_AUTH_TYPE=public
```

### centos 安装

