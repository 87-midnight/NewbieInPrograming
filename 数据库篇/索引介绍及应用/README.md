https://blog.csdn.net/justry_deng/article/details/81458470

https://juejin.im/post/5c2c53396fb9a04a053fc7fe

####索引
>关键字与数据的映射关系称为索引（==包含关键字和对应的记录在磁盘中的地址==）。关键字是从数据当中提取的用于标识、检索数据的特定内容。

#####索引检索为什么快？
- 关键字相对于数据本身，==数据量小==
- 关键字是==有序==的，二分查找可快速确定位置

#####MySQL中索引类型
>普通索引（index），唯一索引（unique key），主键索引（primary key），全文索引（fulltext key）

**三种索引的索引方式是一样的，只不过对索引的关键字有不同的限制：**

>- 普通索引：对关键字没有限制
>- 唯一索引：要求记录提供的关键字不能重复
>- 主键索引：要求关键字唯一且不为null

#####查看索引
- show create table 表名;
- desc 表名;
- show indexes from `表名`;
- show keys from `表名`;
#####创建索引


```sql
create TABLE user_index(
	id int auto_increment primary key,
	first_name varchar(16),
	last_name VARCHAR(16),
	id_card VARCHAR(18),
	information text
);

-- 更改表结构
alter table user_index
-- 创建一个first_name和last_name的复合索引，并命名为name
add key name (first_name,last_name),
-- 创建一个id_card的唯一索引，默认以字段名作为索引名
add UNIQUE KEY (id_card),
-- 鸡肋，全文索引不支持中文
add FULLTEXT KEY (information);
```

**建表后创建：**

ALTER TABLE 表名 ADD [UNIQUE | FULLTEXT | SPATIAL]  INDEX | KEY  [索引名] (字段名1 [(长度)] [ASC | DESC]) [USING 索引方法]；

或

CREATE  [UNIQUE | FULLTEXT | SPATIAL]  INDEX  索引名 ON  表名(字段名) [USING 索引方法]；

示例：
```mysql

ALTER TABLE projectfile ADD UNIQUE INDEX (fileuploadercode);
ALTER TABLE projectfile ADD INDEX (fileuploadercode, projectid);


-- 将id列设置为主键
ALTER TABLE index_demo ADD PRIMARY KEY(id) ;
-- 将id列设置为自增
ALTER TABLE index_demo MODIFY id INT auto_increment;  

```

#####创建表时指定索引
```sql
CREATE TABLE user_index2 (
	id INT auto_increment PRIMARY KEY,
	first_name VARCHAR (16),
	last_name VARCHAR (16),
	id_card VARCHAR (18),
	information text,
	KEY name (first_name, last_name),
	FULLTEXT KEY (information),
	UNIQUE KEY (id_card)
);

```
建表时创建：
CREATE TABLE 表名(

字段名 数据类型 [完整性约束条件],<br>
       ……，<br>
[UNIQUE | FULLTEXT | SPATIAL] INDEX | KEY<br>

[索引名](字段名1 [(长度)] [ASC | DESC]) [USING 索引方法]<br>
);


####索引的删除：

    DROP INDEX 索引名 ON 表名
    
或
    
    ALTER TABLE 表名 DROP INDEX 索引名

####执行计划explain