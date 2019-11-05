### influx 基础语法

#### 登录influx

**无账号密码登录**
```cmd
influx
```
**验证登录**
```cmd
influx -username root -password 1234
```

#### 数据库

**选择数据库**
```cmd
use [database]
```
**查看数据库列表**
```cmd
show databases
```

#### 时间格式化

```cmd
precision rfc3339
```

#### 查询数据库保存策略

```cmd
show retention policies [on <database_name>]
```

#### 查询持续查询列表

```cmd
show continous queries [on <database_name>]
```
