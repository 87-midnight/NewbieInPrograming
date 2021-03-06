1、悲观锁与乐观锁
        悲观锁与乐观锁是两种常见的资源并发锁设计思路，也是并发编程中一个非常基础的概念。本文将对这两种常见的锁机制在数据库数据上的实现进行比较系统的介绍。

1.1、悲观锁（Pessimistic Lock）

        悲观锁的特点是先获取锁，再进行业务操作，即“悲观”的认为获取锁是非常有可能失败的，因此要先确保获取锁成功再进行业务操作。通常所说的“一锁二查三更新”即指的是使用悲观锁。通常来讲在数据库上的悲观锁需要数据库本身提供支持，即通过常用的select … for update操作来实现悲观锁。当数据库执行select for update时会获取被select中的数据行的行锁，因此其他并发执行的select for update如果试图选中同一行则会发生排斥（需要等待行锁被释放），因此达到锁的效果。select for update获取的行锁会在当前事务结束时自动释放，因此必须在事务中使用。悲观锁(Pessimistic Lock), 顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。

        需要注意的是不同的数据库对select for update的实现和支持都是有所区别的，例如oracle支持select for update no wait，表示如果拿不到锁立刻报错，而不是等待，mysql就没有no wait这个选项。另外mysql还有个问题是select for update语句执行中所有扫描过的行都会被锁上，这一点很容易造成问题。因此如果在mysql中用悲观锁务必要确定走了索引，而不是全表扫描。

悲观锁使用场景

        比较适合写入操作比较频繁的场景，如果出现大量的读取操作，每次读取的时候都会进行加锁，这样会增加大量的锁的开销，降低了系统的吞吐量。

        悲观锁包括：行锁（包括：共享锁，排他锁），表锁（包括：读锁，写锁）。

1.2、乐观锁（Optimistic Lock）

        乐观锁的特点先进行业务操作，不到万不得已不去拿锁。即“乐观”的认为拿锁多半是会成功的，因此在进行完业务操作需要实际更新数据的最后一步再去拿一下锁就好。乐观锁(Optimistic Lock), 顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制。乐观锁适用于多读的应用类型，这样可以提高吞吐量，像数据库如果提供类似于write_condition机制的其实都是提供的乐观锁。

        乐观锁在数据库上的实现完全是逻辑的，不需要数据库提供特殊的支持。一般的做法是在需要锁的数据上增加一个版本号，或者时间戳，然后按照如下方式实现：

1. SELECT data AS old_data, version AS old_version FROM …;
2. 根据获取的数据进行业务操作，得到new_data和new_version
3. UPDATE SET data = new_data, version = new_version WHERE version = old_version
if (updated row > 0) {
    // 乐观锁获取成功，操作完成
} else {
    // 乐观锁获取失败，回滚并重试
}
        乐观锁是否在事务中其实都是无所谓的，其底层机制是这样：在数据库内部update同一行的时候是不允许并发的，即数据库每次执行一条update语句时会获取被update行的写锁，直到这一行被成功更新后才释放。因此在业务操作进行前获取需要锁的数据的当前版本号，然后实际更新数据时再次对比版本号确认与之前获取的相同，并更新版本号，即可确认这之间没有发生并发的修改。如果更新失败即可认为老版本的数据已经被并发修改掉而不存在了，此时认为获取锁失败，需要回滚整个业务操作并可根据需要重试整个过程。

     乐观锁可以理解为无锁，靠的是机制和数据库update时获取的到的行锁。

     对数据库单独某个字段进行增加操作 update b_credit t set t.amount=t.amount+100；    是可以不用锁，也无需开启事务的。

乐观锁  优点：

        乐观锁（ Optimistic Locking ） 相对悲观锁而言，乐观锁机制采取了更加宽松的加锁机制。悲观锁大多数情况下依靠数据库的锁机制实现，以保证操作最大程度的独占性。但随之而来的就是数据库性能的大量开销，特别是对长事务而言，这样的开销往往无法承受。而乐观锁机制在一定程度上解决了这个问题。乐观锁，大多是基于数据版本（ Version ）记录机制实现。何谓数据版本？即为数据增加一个版本标识，在基于数据库表的版本解决方案中，一般是通过为数据库表增加一个 数值类型的“version” 字段来实现。读取出数据时，将此版本号一同读出，之后更新时，对此版本号加一。更新数据时将读出的版本号作为一个条件，如果数据库表中版本号与此版本号相同则能更新成功，否则更新失败。

        乐观锁，大多是基于数据版本 （ Version ）记录机制实现。数据版本，即为数据增加一个版本标识，在基于数据库表的版本解决方案中，一般是通过为数据库表增加一个 “version” 字段来实现（也可以采用另一种方式，同样是在需要乐观锁控制的table中增加一个字段，名称无所谓，字段类型使用时间戳timestamp, 和上面的version类似，也是在更新的时候检查当前数据库中数据的时间戳和自己更新前取到的时间戳进行对比，如果一致则OK，否则就是版本冲突）。

         乐观锁机制避免了长事务中的数据库加锁开销，大大提升了大并发量下的系统整体性能表现。

乐观锁  缺点：

        乐观锁机制往往基于系统中的数据存储逻辑，因此也具备一定的局限性，由于乐观锁机制是在我们的系统中实现，来自外部系统的更新操作不受我们系统的控制，因此可能会造成脏数据被更新到数据库中。在系统设计阶段，应该充分考虑到这些情况出现的可能性，并进行相应调整（如将乐观锁策略在数据库存储过程中实现，对外只开放基于此存储过程的数据更新途径，而不是将数据库表直接对外公开）。

总的来说：

1）乐观锁在不发生取锁失败的情况下开销比悲观锁小，但是一旦发生失败回滚开销则比较大，因此适合用在取锁失败概率比较小的场景，可以提升系统并发性能

2）乐观锁还适用于一些比较特殊的场景，例如在业务操作过程中无法和数据库保持连接等悲观锁无法适用的地方

乐观锁使用场景

      比较适合读取操作比较频繁的场景，如果出现大量的写入操作，数据发生冲突的可能性就会增大，为了保证数据的一致性，应用层需要不断的重新获取数据，这样会增加大量的查询操作，降低了系统的吞吐量。

2、表级锁
MySQL表级锁分为读锁和写锁。

2.1、读锁

    用法：LOCK TABLE table_name [ AS alias_name ] READ

释放锁使用UNLOCK tables.可以为表使用别名，如果一旦使用别名在使用的时候也必须采用别名。成功申请读锁的前提是当前没有线程对该表使用写锁，否则该语句会被阻塞。申请读锁成功后，其他线程也可以对该表进行读操作，但不允许有线程对其进行写操作，就算是当前线程也不允许。当锁住了A表之后，就只能对A表进行读操作，对其他表进行读操作会出现错误（tablename was not locked with LOCK TABLES）

2.2、写锁

    用法： LOCK TABLE table_name [AS alias_name] [ LOW_PRIORITY ] WRITE

同样也可以使用别名，与读锁不同的是，写锁中可以指定锁的优先级。LOW_PRIORITY是一种比读锁更低优先级的锁,当多个线程同时申请多种锁（LOW_PRIORITY,READ,WRITE）时，LOW_PRIORITY的优先级最低。读锁申请成功的前提是没有线程对表加读锁和其他写锁，否则会被阻塞。

表级锁在MyISAM和innoDB中都有用到，创建锁的开销小，不会出现死锁，由于锁定的是整张表，所以并发度低。当需要频繁对大部分数据做 GROUP BY 操作或者需要频繁扫描整个表时，推荐使用表级锁。

3、行级锁
        行级锁是Mysql中最细粒度一种锁，能大大减少数据库操作的冲突，但是，由于其粒度最小，加锁的开销最大。行级锁分为共享锁和排他锁。

3.1、共享锁(S LOCK)

    用法：SELECT ...LOCK IN SHARE MODE;

Mysql会对查询结果中的每行都加共享锁，当没有其他线程对查询结果集中的任何一行使用排他锁时，可以成功申请共享锁，否则会被阻塞。其他线程也可以读取使用了共享锁的表，而且这些线程读取的是同一个版本的数据。

举例：

1、执行如下：

```
BEGIN;
SELECT t.userId,t.realName FROM b_user t WHERE t.userId='ff808181542eaafe01542ebf00150018' LOCK IN SHARE MODE;

COMMIT;
```

2、再执行如下：

    UPDATE  b_user t SET t.realName='张三' WHERE t.userId='ff808181542eaafe01542ebf00150018';

3.2、排他锁(X LOCK)

    用法：SELECT ...LOCK FOR UPDATE;

Mysql会对查询结果中的每行都加排他锁，当没有其他线程对查询结果集中的任何一行使用排他锁时，可以成功申请排他锁，否则会被阻塞。

行级锁都是基于索引的，如果一条SQL语句用不到索引是不会使用行级锁的，会使用表级锁。行级锁的缺点是：由于需要请求大量的锁资源，所以速度慢，内存消耗大



>PS:参考 https://blog.csdn.net/jiahao1186/article/details/82263029