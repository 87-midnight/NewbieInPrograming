### spring jpa基础介绍及应用

概述：spring jpa

### spring boot 集成示例

**maven 依赖**

```xml
<dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.7.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
**配置文件**

```yaml
spring:
  # spring DataSource数据源配置
  datasource:
    url: jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    hikari:
      auto-commit: true
      maximum-pool-size: 20
      minimum-idle: 10
  #jpa配置
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
```

**添加实体类**

```java
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title_")
    private String title;
    @Column(name = "content_")
    private String content;

    @CreatedDate
    @Column(name = "create_time")
    public Date createTime;

    @CreatedBy
    @Column(name = "create_by")
    public Long createBy;
}
```
@CreatedBy,@CreatedDate注解主要用于自动设置创建时间和创建人。开启@@EnableJpaAuditing即可设置时间，
如果需要设置创建人，则需要自定义注入，配置如下：

```java
@Component
public class AuditingConfig implements AuditorAware<Long> {
    public Optional<Long> getCurrentAuditor() {
//        security获取当前会话者
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        Object principal = ctx.getAuthentication().getPrincipal();
        return Optional.of(125241L);
    }
}
```