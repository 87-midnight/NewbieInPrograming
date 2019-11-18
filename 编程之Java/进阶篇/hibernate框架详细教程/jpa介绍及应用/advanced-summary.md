### jpa 常见配置

```yml
spring:
    jpa:
       hibernate:
         ddl-auto: update #是否自动更新实体到数据库
       show-sql: true #是否显示SQL执行语句
       database-platform: org.hibernate.dialect.MySQL5InnoDBDialect # 定义数据库平台类型
```

### jpa Persistable 用法

**介绍：**
> Persistable 是一个接口类，其包含一个isNew方法。
该方法返回boolean类型，用于判断当前实例是否是新的记录，
在新增或更新时会用到，使用者可以自定义什么情况下为新增，什么
情况下为更新，true为新增，false为更新

> 该接口类隶属标记，在执行CrudRepository的save方法时，其实现类
SimpleJpaRepository会对该接口类的方法进行调用，以达到判断新增或
更新的效果

**save源码：**
```java
@Transactional
    public <S extends T> S save(S entity) {
        if (this.entityInformation.isNew(entity)) {
            this.em.persist(entity);
            return entity;
        } else {
            return this.em.merge(entity);
        }
    }
```

**使用示例：**

```java
public class Article implements Persistable<Long> {

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
    
    private transient boolean isNew;//默认为false

    @Override
    public boolean isNew() {
        return isNew;//新增时，设置该属性为true，更新时可以不设值，默认为false
    }
}
```
