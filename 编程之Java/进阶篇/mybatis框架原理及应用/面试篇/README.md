### 面试篇
####简介：
>MyBatis是一个优秀的持久层ORM框架，它对jdbc的操作数据库的过程进行封装，使开发者只需要关注SQL 本身，而不需要花费精力去处理例如注册驱动、创建connection、创建statement、手动设置参数、结果集检索等jdbc繁杂的过程代码。
Mybatis通过xml或注解的方式将要执行的statement配置起来，并通过java对象和statement中的sql进行映射生成最终执行的sql语句，最后由mybatis框架执行sql并将结果映射成java对象并返回。

####Mybatis要解决的问题：
- 将sql语句硬编码到java代码中，如果修改sql语句，需要修改java代码，重新编译。系统可维护性不高。

设想如何解决？

能否将sql单独配置在配置文件中。

- 数据库连接频繁开启和释放，对数据库的资源是一种浪费。

设想如何解决？

使用数据库连接池管理数据库连接。

- 向preparedStatement中占位符的位置设置参数时，存在硬编码（占位符的位置，设置的变量值）

设想如何解决？

能否也通过配置的方式，配置设置的参数，自动进行设置参数

- 解析结果集时存在硬编码（表的字段名、字段的类型）

设想如何解决？

能否将查询结果集映射成java对象。

#####{}和${}的区别是什么？

> 答：#{}是预编译处理，${}是字符串替换。Mybatis在处理#{}时，会将sql中的#{}替换为?号，
调用PreparedStatement的set方法来赋值；
Mybatis在处理{}时，就是把{}替换成变量的值。
使用#{}可以有效的防止SQL注入，提高系统安全性。
$方式无法防止Sql注入。$方式一般用于传入数据库对象，例如传入表名.　MyBatis排序时使用order by 动态参数时需要注意，用$而不是#

####当实体类中的属性名和表中的字段名不一样 ，怎么办 ？

######第1种： 通过在查询的sql语句中定义字段名的别名，让字段名的别名和实体类的属性名一致
```xml
<select id=”selectorder” parametertype=”int” resultetype=”me.gacl.domain.order”> 
       select order_id id, order_no orderno ,order_price price form orders where order_id=#{id}; 
    </select>
```
######第2种： 通过<resultMap>来映射字段名和实体类属性名的一一对应的关系
```xml
<select id="getOrder" parameterType="int" resultMap="orderresultmap">
        select * from orders where order_id=#{id}
    </select>
<resultMap type=”me.gacl.domain.order” id=”orderresultmap”> 
        <!–用id属性来映射主键字段–> 
        <id property=”id” column=”order_id”> 
        <!–用result属性来映射非主键字段，property为实体类属性名，column为数据表中的属性–> 
        <result property = “orderno” column =”order_no”/> 
        <result property=”price” column=”order_price” /> 
</reslutMap>
```

####模糊查询like语句该怎么写?
```cmd
string wildcardname = “smi”; 
    list<name> names = mapper.selectlike(wildcardname);

    <select id=”selectlike”> 
        select * from foo where bar like "%"#{value}"%"
    </select>
```
1. 表达式: name like"%"#{name}"%" #起到占位符的作用

2. 表达式: name like '%${name}%' $进行字符串的拼接,直接把传入的值,拼接上去了,没有任何问题

3. 表达式: name likeconcat(concat('%',#{username}),'%') 这是使用了cancat进行字符串的连接,同时使用了#进行占位

4. 表达式:name like CONCAT('%','${name}','%') 对上面的表达式进行了简化,更方便了

####通常一个Xml映射文件，都会写一个Dao接口与之对应，请问，这个Dao接口的工作原理是什么？Dao接口里的方法，参数不同时，方法能重载吗？

>- Dao接口，就是人们常说的Mapper接口，接口的全限名，就是映射文件中的namespace的值，接口的方法名，就是映射文件中MappedStatement的id值，接口方法内的参数，就是传递给sql的参数。
>- Mapper接口是没有实现类的，当调用接口方法时，接口全限名+方法名拼接字符串作为key值，可唯一定位一个MappedStatement，举例：com.mybatis3.mappers.StudentDao.findStudentById，可以唯一找到namespace为com.mybatis3.mappers.StudentDao下面id = findStudentById的MappedStatement。在Mybatis中，每一个<select>、<insert>、<update>、<delete>标签，都会被解析为一个MappedStatement对象。
>- Dao接口里的方法，是不能重载的，因为是全限名+方法名的保存和寻找策略。
>- Dao接口的工作原理是JDK动态代理，Mybatis运行时会使用JDK动态代理为Dao接口生成代理proxy对象（如使用spring会注入到容器中），代理对象proxy会拦截接口方法，转而执行MappedStatement所代表的sql，然后将sql执行结果返回。

####Mybatis是如何将sql执行结果封装为目标对象并返回的？都有哪些映射形式？
>答：第一种是使用<resultMap>标签，逐一定义列名和对象属性名之间的映射关系。
第二种是使用sql列的别名功能，将列别名书写为对象属性名，比如T_NAME AS NAME，对象属性名一般是name，小写，但是列名不区分大小写，Mybatis会忽略列名大小写，智能找到与之对应对象属性名，你甚至可以写成T_NAME AS NaMe，Mybatis一样可以正常工作。
有了列名与属性名的映射关系后，Mybatis通过反射创建对象，同时使用反射给对象的属性逐一赋值并返回，那些找不到映射关系的属性，是无法完成赋值的。

####如何获取自动生成的(主)键值?

insert 方法总是返回一个int值 - 这个值代表的是插入的行数。
而自动生成的键值在 insert 方法执行完后可以被设置到传入的参数对象中。
示例：

```cmd
<insert id="insertUserMessage" parameterType="com.xxx.xxx.model.UserMessage"
            useGeneratedKeys="true" keyProperty="userMessage.id">
        insert into my_news
        (orderid,commentid,type,title,content,createtime)
        values
        (#{userMessage.orderid},#{userMessage.commentid},#{userMessage.type},#{userMessage.title}
        ,#{userMessage.content},#{userMessage.createtime})
    </insert>
```
这里需要注意的是需要把实体类传进来。keyProperty为自增的id字段。调用insert后自动将自增id赋值进insert调用的实体类中

```java
//新建对象
UserMessage userMessage = new UserMessage();
userMessage.setXxxxxx(xxxxxx); 
userMessageDao.insertUserMessage(userMessage);

//这时userMessage.getId()就可以获取到自增主键了
BigInteger id = userMessage.getId();
```
####在mapper中如何传递多个参数?

######第一种
```cmd
//DAO层的函数
Public UserselectUser(String name,String area); 

//对应的xml,#{0}代表接收的是dao层中的第一个参数，#{1}代表dao层中第二参数，更多参数一致往后加即可。
<select id="selectUser"resultMap="BaseResultMap">  
    select *  fromuser_user_t   whereuser_name = #{0} anduser_area=#{1}  
</select>  
```

######第二种
```cmd
import org.apache.ibatis.annotations.param; 
public interface usermapper { 
         user selectuser(@param(“username”) string username, 
         @param(“hashedpassword”) string hashedpassword); 
        }
//获取时        
<select id=”selectuser” resulttype=”user”> 
         select id, username, hashedpassword 
         from some_table 
         where username = #{username} 
         and hashedpassword = #{hashedpassword} 
    </select>
```

####Mybatis动态sql是做什么的？都有哪些动态sql？能简述一下动态sql的执行原理不？

>Mybatis动态sql可以让我们在Xml映射文件内，以标签的形式编写动态sql，完成逻辑判断和动态拼接sql的功能。
Mybatis提供了9种动态sql标签：trim|where|set|foreach|if|choose|when|otherwise|bind。
其执行原理为，从sql参数对象中计算表达式的值，根据表达式的值动态拼接sql，以此来完成动态sql的功能。

比如：
```cmd
select id="findUserById" resultType="user">
           select * from user where 
           <if test="id != null">
               id=#{id}
           </if>
            and deleteFlag=0;
</select>
```

####Mybatis的Xml映射文件中，不同的Xml映射文件，id是否可以重复？

>不同的Xml映射文件，如果配置了namespace，那么id可以重复；如果没有配置namespace，那么id不能重复；毕竟namespace不是必须的，只是最佳实践而已。
 原因就是namespace+id是作为Map<String, MappedStatement>的key使用的，如果没有namespace，就剩下id，那么，id重复会导致数据互相覆盖。有了namespace，自然id就可以重复，namespace不同，namespace+id自然也就不同
 
 ####为什么说Mybatis是半自动ORM映射工具？它与全自动的区别在哪里？
 >Hibernate属于全自动ORM映射工具，使用Hibernate查询关联对象或者关联集合对象时，可以根据对象关系模型直接获取，所以它是全自动的。而Mybatis在查询关联对象或关联集合对象时，需要手动编写sql来完成，所以，称之为半自动ORM映射工具。
 
 ####一对一、一对多的关联查询 ？
 
 ```cmd
 <mapper namespace="com.lcb.mapping.userMapper">  
     <!--association  一对一关联查询 -->  
     <select id="getClass" parameterType="int" resultMap="ClassesResultMap">  
         select * from class c,teacher t where c.teacher_id=t.t_id and c.c_id=#{id}  
     </select>  
     <resultMap type="com.lcb.user.Classes" id="ClassesResultMap">  
         <!-- 实体类的字段名和数据表的字段名映射 -->  
         <id property="id" column="c_id"/>  
         <result property="name" column="c_name"/>  
         <association property="teacher" javaType="com.lcb.user.Teacher">  
             <id property="id" column="t_id"/>  
             <result property="name" column="t_name"/>  
         </association>  
     </resultMap>  
 
     <!--collection  一对多关联查询 -->  
     <select id="getClass2" parameterType="int" resultMap="ClassesResultMap2">  
         select * from class c,teacher t,student s where c.teacher_id=t.t_id and c.c_id=s.class_id and c.c_id=#{id}  
     </select>  
     <resultMap type="com.lcb.user.Classes" id="ClassesResultMap2">  
         <id property="id" column="c_id"/>  
         <result property="name" column="c_name"/>  
         <association property="teacher" javaType="com.lcb.user.Teacher">  
             <id property="id" column="t_id"/>  
             <result property="name" column="t_name"/>  
         </association>  
         <collection property="student" ofType="com.lcb.user.Student">  
             <id property="id" column="s_id"/>  
             <result property="name" column="s_name"/>  
         </collection>  
     </resultMap>  
 
 </mapper> 
 ```
 
 #### mybatis一级缓存
 
 >Mybatis对缓存提供支持，但是在没有配置的默认情况下，它只开启一级缓存，一级缓存只是相对于同一个SqlSession而言。
 所以在参数和SQL完全一样的情况下，我们使用同一个SqlSession对象调用一个Mapper方法，往往只执行一次SQL，
 因为使用SelSession第一次查询后，MyBatis会将其放在缓存中，以后再查询的时候，如果没有声明需要刷新，
 并且缓存没有超时的情况下，SqlSession都会取出当前缓存的数据，而不会再次发送SQL到数据库。
 
 1、一级缓存的生命周期有多长？
 
 　　a、MyBatis在开启一个数据库会话时，会 创建一个新的SqlSession对象，SqlSession对象中会有一个新的Executor对象。Executor对象中持有一个新的PerpetualCache对象；当会话结束时，SqlSession对象及其内部的Executor对象还有PerpetualCache对象也一并释放掉。
 
 　　b、如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用。
 
 　　c、如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用。
 
 　　d、SqlSession中执行了任何一个update操作(update()、delete()、insert()) ，都会清空PerpetualCache对象的数据，但是该对象可以继续使用
 
2、怎么判断某两次查询是完全相同的查询？
 
 　　mybatis认为，对于两次查询，如果以下条件都完全一样，那么就认为它们是完全相同的两次查询。
 
 　　2.1 传入的statementId
 
 　　2.2 查询时要求的结果集中的结果范围
 
 　　2.3. 这次查询所产生的最终要传递给JDBC java.sql.Preparedstatement的Sql语句字符串（boundSql.getSql() ）
 
 　　2.4 传递给java.sql.Statement要设置的参数值
 
 #### mybatis二级缓存
 
 MyBatis的二级缓存是Application级别的缓存，它可以提高对数据库查询的效率，以提高应用的性能。
 
 >SqlSessionFactory层面上的二级缓存默认是不开启的，二级缓存的开席需要进行配置，实现二级缓存的时候，
 MyBatis要求返回的POJO必须是可序列化的。 也就是要求实现Serializable接口，配置方法很简单，
 只需要在映射XML文件配置就可以开启缓存了<cache/>，如果我们配置了二级缓存就意味着：
>- 映射语句文件中的所有select语句将会被缓存。
>-  映射语句文件中的所欲insert、update和delete语句会刷新缓存。
>-  缓存会使用默认的Least Recently Used（LRU，最近最少使用的）算法来收回。
>-  根据时间表，比如No Flush Interval,（CNFI没有刷新间隔），缓存不会以任何时间顺序来刷新。
>-  缓存会存储列表集合或对象(无论查询方法返回什么)的1024个引用
>-  缓存会被视为是read/write(可读/可写)的缓存，意味着对象检索不是共享的，而且可以安全的被调用者修改，不干扰其他调用者或线程所做的潜在修改。
 
 ###### 默认的二级缓存开启，只在内存中缓存
 
 创建对象
 ```java
public class Student implements Serializable{

    private static final long serialVersionUID = 735655488285535299L;
    private String id;
    private String name;
    private int age;
    private Gender gender;
    private List<Teacher> teachers;   
}
```
xxxMapper.xml配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yihaomen.mybatis.dao.StudentMapper">
    <!--开启本mapper的namespace下的二级缓存-->
    <!--
        eviction:代表的是缓存回收策略，目前MyBatis提供以下策略。
        (1) LRU,最近最少使用的，一处最长时间不用的对象
        (2) FIFO,先进先出，按对象进入缓存的顺序来移除他们
        (3) SOFT,软引用，移除基于垃圾回收器状态和软引用规则的对象
        (4) WEAK,弱引用，更积极的移除基于垃圾收集器状态和弱引用规则的对象。这里采用的是LRU，
                移除最长时间不用的对形象

        flushInterval:刷新间隔时间，单位为毫秒，这里配置的是100秒刷新，如果你不配置它，那么当
        SQL被执行的时候才会去刷新缓存。

        size:引用数目，一个正整数，代表缓存最多可以存储多少个对象，不宜设置过大。设置过大会导致内存溢出。
        这里配置的是1024个对象

        readOnly:只读，意味着缓存数据只能读取而不能修改，这样设置的好处是我们可以快速读取缓存，缺点是我们没有
        办法修改缓存，他的默认值是false，不允许我们修改
    -->
    <cache eviction="LRU" flushInterval="100000" readOnly="true" size="1024"/>
    <resultMap id="studentMap" type="Student">
        <id property="id" column="id" />
        <result property="name" column="name" />
        <result property="age" column="age" />
        <result property="gender" column="gender" typeHandler="org.apache.ibatis.type.EnumOrdinalTypeHandler" />
    </resultMap>
    <resultMap id="collectionMap" type="Student" extends="studentMap">
        <collection property="teachers" ofType="Teacher">
            <id property="id" column="teach_id" />
            <result property="name" column="tname"/>
            <result property="gender" column="tgender" typeHandler="org.apache.ibatis.type.EnumOrdinalTypeHandler"/>
            <result property="subject" column="tsubject" typeHandler="org.apache.ibatis.type.EnumTypeHandler"/>
            <result property="degree" column="tdegree" javaType="string" jdbcType="VARCHAR"/>
        </collection>
    </resultMap>
    <select id="selectStudents" resultMap="collectionMap">
        SELECT
            s.id, s.name, s.gender, t.id teach_id, t.name tname, t.gender tgender, t.subject tsubject, t.degree tdegree
        FROM
            student s
        LEFT JOIN
            stu_teach_rel str
        ON
            s.id = str.stu_id
        LEFT JOIN
            teacher t
        ON
            t.id = str.teach_id
    </select>
    <!--可以通过设置useCache来规定这个sql是否开启缓存，ture是开启，false是关闭-->
    <select id="selectAllStudents" resultMap="studentMap" useCache="true">
        SELECT id, name, age FROM student
    </select>
    <!--刷新二级缓存
    <select id="selectAllStudents" resultMap="studentMap" flushCache="true">
        SELECT id, name, age FROM student
    </select>
    -->
</mapper>
```

在mybatis-config.xml里开启二级缓存

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--这个配置使全局的映射器(二级缓存)启用或禁用缓存-->
        <setting name="cacheEnabled" value="true" />
    </settings>
</configuration>
```

###### mybatis使用redis缓存作为二级缓存介质。

实现org.apache.ibatis.cache.Cache。
```java
public class RedisCache implements Cache{
    
}
```
需要缓存的对象必须实现Serializable接口。

具体代码请看:[mybatis](../../../../code/spring-boot-mybatis-sample)
 