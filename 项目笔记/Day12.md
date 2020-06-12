# 问题解决 ：

1. 部署到Tomcat之后的访问路径是如何确定的？
   - 我们在web项目的properties -> web project settings -> context root下的值是我们部署到Tomcat之后，要访问的路径。在项目新建时，此值默认与项目名同名，但是如果我们复制此项目时，项目名会改，但是这个context root值不会变。

# 项目跟进：

1. 实现每日每小时各性别骑行数量分析需求的目标表字段设计

   - JavaEE开发中的设计表结构（项目经理）：对业务数据进行存储，包含所有业务模块的数据，设计表与表之间的关系，便于业务发生对数据进行CURD操作
   - 数据分析中的设计表结构（数据工程师）：设计从原始数据红提取出哪些字段构成新表，新表中的字段应该能够满足后续数据分析任务的需求。
   - 针对于共享单车的数据分析，相比于同一日期的对比，同一周几的对比效果更好。也就是我们统一对比4月的第一天，不如统一对比4月的第一个礼拜一。所以我们的字段里应该有星期字段。
   - 年份、月份、日期、周几、小时、性别、数量

2. 自我学习的方式：

   - 应该以结果为导向，在最开始分析的时候，应该最全面的分析出我们所需要的。即作为产品经理的角度去思考，在实现的时候，再站在开发的角度去思考，再根据实现难易程度去砍掉需求。

3. 从数据库表中提取信息

   - 新建一个tb_day_hour_count表

   - 利用SQL语句操作tb_trip_1904和tb_trip_2004

   - 30 * 24 * 3 * 2 = 4320条数据

   - 在一页数据中，无法有效显示这么多数据，也没有必要，在后续需求中，我们应该提供一个查询功能，让用户去对比想比对的数据。后台应该提供全部的数据。

   - 建表语句：

     - ```mysql
       -- 创建tb_day_hour_count表
       create table tb_day_hour_count(
       trip_year int comment '数据的年份',
       trip_month int comment '数据的月份',
       trip_day int comment '数据的日期',
       trip_dayofweek int comment '周几',
       trip_hour int comment '数据的小时',
       gnder int comment '性别',
       trip_count int comment '日骑行总数'
       )default charset utf8;
       ```

   - 插入数据：

     - ```mysql
       -- 计算2004表中每日每小时不同性别用户的骑行数量写入上表
       insert into tb_day_hour_count
       select year(starttime) as trip_year,
              month(starttime) as trip_month,
              day(starttime) as trip_day,
              dayofweek(starttime) as trip_dayofweek,
              hour(starttime) as trip_hour,
              gender,
              count(*) as trip_count 
       from 
              tb_trip_2004 
       group by 
              year(starttime),month(starttime),day(starttime),hour(starttime),gender;
              
       -- 写入tb_day_hour_count表
       insert into tb_day_hour_count 
       select 
           year(starttime) as trip_year, 
           month(starttime) as trip_month, 
           day(starttime) as trip_day, 
           dayofweek(starttime) as trip_dayofweek,
           hour(starttime) as trip_hour,
       	gender,    
           count(*) as trip_count 
       from 
           tb_trip_1904 
       group by
           year(starttime), 
           month(starttime), 
           day(starttime),
           hour(starttime),
           gender;
       ```

   - 插入的数据应该是缺少的，发现19年数据缺少了7条，20年数据缺少了6条。 经过分析，缺少数据的原因是某一天的某个小时中，没有出现某个性别的用户的骑行记录。例如，在20年4月1日凌晨4点期间，没有性别为0的用户的骑行记录。

   - 从数据集完整性的角度来说，应该对缺少的数据进行补充，补充默认的trip_count为0的数据，这样后续进行分析和可视化时，会更为方便，可以规避一些空指针等异常。

4. 数据处理：

   - 方式一：在数据库中通过SQL确定缺少的数据，然后通过SQL手动插入对应的数据。例如：

     - ```mysql
       insert into 
           tb_day_hour_count(
           trip_year,trip_month,
           trip_day,trip_dayofweek,
           trip_hour,gender,trip_count
       ) 
       values(
           2020,4,1,4,
           4,0,0
       )
       ```

   - 方式二：通过编程的方式，遍历表中数据，计算出缺失的数据，再插入缺失的数据。

     - 通过字符串比对来判断，每个hour内有三个值，如果缺少就根据hour的i值插入第i个位置一条数据，赋值为0，保存此插入SQL语句，存入list中。
     - main方法执行所有list中的SQL语句，在控制台可以查看插入了哪条数据，共插入了多少条数据。

5. JavaEE部分实现思路分析

   - ![](https://img.99couple.top/20200609172020.png)

6. entity实体类

   - DateInfo类，用以封装所有日期信息
   - year、month、day、dayOfWeek属性照常写，dayOfWeekStr是根据dayOfWeek和静态常量数组的逻辑关系得出来的。定义成一个方法，在setDayOfWeek时直接调用，这样dayOfWeek和dayOfWeekStr就都有了。

7. DAO持久层

   - 新建listDateInfo方法，目的是取出tb_day_hour_count表中所有的日期信息
   - 利用distinct关键字去重，将结果封装成一个DateInfo类型的List返回。
   - 测试listDateInfo方法。

# 知识补充：

1. 字符串拼接：！！！！！注意！！！！！！
   - 字符串底层其实是final类型的char数组，而使用+来进行字符串拼接的代码。实际上是不断地开辟新的空间来将两个字符串复制过来生成新的。是极其浪费空间的。为此，我们可以使用官方的字符串拼接StringBuffer（线程安全）和StringBuilder（不提供线程安全）来实现。