## elastic,kibana,logstash,kafka安装使用

### elastic

>参考elastic安装文档

简单使用

```cmd
docker run -itd --name elasticsearch -p 9200:9200 -p 9300:9300 -v /opt/elastic/data:/usr/share/elasticsearch/data -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.7.1
```

### zookeeper安装

- 拉取镜像

```cmd
mkdir -p /opt/zookeeper/data
mkdir -p /opt/zookeeper/logs
docker pull zookeeper
```

- 启动zookeeper容器

```cmd
docker run -itd  --name zookeeper \
-p 2181:2181 \
-v /opt/zookeeper/data:/data \
-v /opt/zookeeper/logs:/logs \
-e "ZOO_PORT=2181" \
-e "ZOO_DATA_DIR=/data" \
-e "ZOO_DATA_LOG_DIR=/logs" \
-e "ZOO_MY_ID=1" \
zookeeper:latest
```
>- -e ZOO_PORT
>   - zookeeper启动端口
>- -e ZOO_DATA_DIR
>   - 数据存放目录
>- -e ZOO_DATA_LOG_DIR
>   - 日志存放目录
>- -e ZOO_MY_ID
>   - zookeeper集群节点唯一标识id
>- -e ZOO_SERVERS=”server.1=宿主机host:2888:3888 server.2=宿主机host:2888:3888 server.3=宿主机host:2888:3888″
>   - zookeeper集群配置节点host端口


### kafka 单节点安装

- 拉取镜像

```cmd
mkdir -p /opt/kafka
docker pull wurstmeister/kafka:latest
```

- 启动容器

```cmd
docker run -itd --name kafka \
-p 9092:9092 \
-p 1099:1099 \
--link zookeeper:zookeeper \
-v /opt/kafka:/data \
-e "KAFKA_BROKER_ID=1" \
-e " KAFKA_AUTO_CREATE_TOPICS_ENABLE=true " \
-e "KAFKA_PORT=9092" \
-e "KAFKA_HEAP_OPTS=-Xms3g -Xmx5g" \
-e "KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.31.134:9092" \
-e "KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092" \
-e "KAFKA_LOG_DIRS=/data/kafka" \
-e "KAFKA_ZOOKEEPER_CONNECT=192.168.31.134:2181" \
-e "KAFKA_JMX_OPTS=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=192.168.31.134 -Dcom.sun.management.jmxremote.rmi.port=1099" \
-e "JMX_PORT=1099" \
wurstmeister/kafka:latest
```
>- -e KAFKA_BROKER_ID
>   - kafka broker唯一标识id，集群区分
>- -e KAFKA_PORT
>   - 启动端口
>- -e KAFKA_HEAP_OPTS
>   - kafka启动时jvm参数设置
>- -e KAFKA_HOST_NAME，KAFKA_ADVERTISED_HOST_NAME
>   - kafka host名，ip
>- -e KAFKA_LOG_DIRS
>   - 日志路径
>- -e KAFKA_ZOOKEEPER_CONNECT=”192.168.31.134:2181″
>   - 连接zookeeper集群地址，host:port格式，逗号分隔

### kibana 安装

- 拉取镜像

```cmd
# 需要执行版本号，否则无法下载
docker pull docker.elastic.co/kibana/kibana:7.7.1 
```
- 启动kibana

```cmd
docker run -itd --name kibana -p5601:5601 -e "ELASTICSEARCH_URL=http://192.168.31.134:9200" --link elasticsearch:elasticsearch docker.elastic.co/kibana/kibana:7.7.1 
```

### logstash 安装

- 拉取镜像
```cmd
mkdir -p /opt/logstash/conf
vi /opt/logstash/conf/logstash.conf

#配置输入输出
input{
   kafka{
        group_id => "log-api-1"
        topics => ["log-api"]
        bootstrap_servers => ["192.168.31.134:9092"]
        auto_offset_reset => "earliest"
        consumer_threads => 20
        type => "log-application"
    }
}
output{
    elasticsearch {
        hosts => ["192.168.31.134:9200"]
        index => "logstash-%{type}-%{+YYYY.MM.dd}"
        }
   stdout {
    codec => rubydebug
  }
}

# 拉取镜像
docker pull docker.elastic.co/logstash/logstash:7.7.1


vi /opt/logstash/conf/logstash.yml
```


```yaml
pipeline:
  batch:
    size: 125
    delay: 50
http.host: 127.0.0.1
monitoring.elasticsearch.hosts: http://192.168.31.134:9200
```

- 启动服务

```cmd
docker run -itd --name logstash \
--net=host -v /opt/logstash/conf/logstash.conf:/usr/share/logstash/pipeline/logstash.conf \
-v /opt/logstash/conf/logstash.yml:/usr/share/logstash/config/logstash.yml \
docker.elastic.co/logstash/logstash:7.7.1 
```




### kafka-manager

- 全部配置文件

`https://github.com/yahoo/CMAK/blob/master/conf/application.conf`


```cmd
docker pull kafkamanager/kafka-manager

docker run -itd --name manager \
-p9012:9000 \
-e "ZK_HOSTS=192.168.31.134:2181" \
-e " KAFKA_BROKERS=192.168.31.134:9092" \
--link kafka:kafka \
 kafkamanager/kafka-manager
```