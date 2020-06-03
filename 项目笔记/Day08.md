# 需求跟进：

1. 创建结果表，分析出每日的骑行总数

   - 我们之前导入到数据库的tb_trip_1904中保存了19年4月的全部骑行数据，本用例需要从这些数据中，统计出4月每一天的总骑行量，然后进行可视化

   - 因为是历史数据，不会发生变化，只需进行一次查询。设计一张结果表，用于保存信息，后续用户需要查看统计结果时，可以直接查询该表。

   - 设计tb_day_count表

     - trip_count 字段    日骑行数
     - trip_day 字段 日期
     - trip_year 字段 年份
     - trip_month 字段 月份

   - 如图：

   - ![](https://img.99couple.top/20200603180230.png)

   - SQL语句如下：

   - 原始数据 -> 按照年分组 -> 按照月分组 -> 按照日分组 -> 统计数据条目。

   - 可以省略年月分组，因为都是19.04的数据。

   - 该表中，骑行时间用starttime字段表示，该字段同时包含了年月日时分秒的信息，如果想要按照日期进行分组，需要从starttime中截取出日期的值。

   - "2019-04-01 12:30：59.8600"    ->   01

   - 采用MySQL内置函数day（str） 截取出天。

   - 有内置函数需求就去查MySQL函数即可。

   - ```mysql
     select year(starttime),month(starttime),day(starttime),count(*) from tb_trip_1904 group by year(starttime),month(starttime),day(starttime);
     ```

   - 也可以起别名

   - ```mysql
     select year(starttime) as trip_year,
            month(starttime) as trip_month,
            day(starttime) as trip_day,
            count(*) as trip_count 
     from 
            tb_trip_1904 
     group by 
            year(starttime),month(starttime),day(starttime);
     ```

   - 将查询结果保存到表中，在上述语句之前加一句insert intotb_day_count即可

   - ```mysql
     insert into tb_day_count
     select year(starttime) as trip_year,
            month(starttime) as trip_month,
            day(starttime) as trip_day,
            count(*) as trip_count 
     from 
            tb_trip_1904 
     group by 
            year(starttime),month(starttime),day(starttime);
     ```

   - 作业：对`tb_trip_1904`进行分析，写出下列需求的SQL语句，并对结果进行截图，如果结果数据条数很多，截图中仅包含前5条记录即可：

     1. 计算每一天不同性别的用户的骑行数量

        - ```mysql
          insert into day_gender_count
          select day(starttime) as trip_day,
                 count(*) as trip_count
          from
                 tb_trip_1904
          group by
                 day(starttime),gender;
          ```

     2. 统计一个月30天中，每小时的总骑行数量(即4月1-30日0点-1点骑行数量的和，4月1-30日1-2点骑行数量的和，以此类推)

        - ```mysql
          insert into hour_count
          select hour(starttime) as trip_hour,
                 count(*) as trip_count
          from   
                 tb_trip_1904
          group by
                 hour(starttime);
          ```

     3. 排名前5的热门起始站点的名称，当月的骑行次数

        - ```mysql
          insert into order_station
          select start_station_name,
                 count(*) as trip_count
          from
                 tb_trip_1904
          group by
                 start_station_name order by count(*) desc limit 5;
          ```

     4. 年龄在18-30之间的各性别用户的平均骑行时长

# 基础知识：

1. SQL语句
   - SQL：结构化查询语言，是关系系型数据库的通用操作语言。程序员必备基础技能。
   - SQL不是编程语言，是数据库的操作语言，是非过程性的语言。
   - database  ----- 一般与一个项目相对应
   - table  ----------- 一般与一个模块相对应
   - SQL提供了对上述3个级别的增删改查(也称CRUD，Create，Read，Update，Delete)操作的语句。
   - SQL操作database
     - 创建
     - create database 库名
     - 删除
     - drop database 库名
     - 修改
     - alter 库名   ..........
     - 查看库的列表
     - show databases
     - 查询建库语句
     - show create database 库名
     - 使用库
     - use 库名
   - SQL操作table
     - 创建
     - create table 表名(
     - 字段名 字段类型 [约束] [默认值] [comment ' ']，
     - ....
     - );
     - 删除
     - drop table表名
     - 修改
     - alter 表名   ..........
     - 查看当前库下表的列表
     - show tables
     - 查询建表语句
     - show create tablse 表名
     - 查看表的结构
     - desc 表名
   - SQL操作表中数据
     - 向表中插入数据
       - insert into 表名 [(字段列表)] values(值的列表)[,(值的列表)...]      --[]表示可以没有
       - insert into 表名 values(xxx,xxxx,xxx...)       -----省略字段列表，但是values列表中的顺序不能混和字段不能少
       - insert into 表名 (字段1，字段3，字段2) values (xxx,xxx,xxx)   --- 字段顺序和值顺序对应好就行
       - 企业会硬性要求有字段名
       - insert into 表名 (字段列表) values(值的列表),(值的列表)...      -----一次性插入多条记录
     - 删除数据
       - delete from 表名    --删除表中所有记录
       - delete from 表名 where 条件    ---删除符合条件的语句
     - 改数据
       - update 表名 set 字段名=值 [where 条件]   --- 没条件就全改
     - 查询
       - select * from 表名 [where 条件]    -----数据少可以这么干，多了就是个灾难
       - select 字段列表 from 表名  -----企业中要求必须使用字段列表
     - where子句
       - select username，age from student where id>4   ----查询id大于4的数据的username和id
     - order by 子句
       - select * from student order by age [asc] ---不指定的话默认从小到大（升序）
       - select * from student order by age desc   ---降序
   - 聚合函数（对表中的一列数据进行统计）
     - select count(*) from 表名     ------求数据的行（条）数
     - count(字段名)     -------该字段的值不为null的数据的条数
     - sum(字段名)   ---- 求该字段数据的和
     - avg(字段名)   ------- 求该字段下的数据的平均值
     - select avg(字段名)  from 表名 where 条件   ---指定条件
     - max(字段名)    --最大值
     - min(字段名)     --最小值
     - 需求：查询1班的平均分        select avg(score) from student2 where class='class1';
     - 
   - 分组函数
     - group by
     - select class,avg(score) from student2 group by class;
     - 上述语句的执行逻辑是先获取student2表中所有的数据，然后按照class字段的值将表中数据分成多张子表，每张子表中，所有的数据的class字段的值是相同的，然后在分别对每个子表中的数据进行求平均值操作。
     - 练习：统计tb_trip_1904表中不同性别的人的骑行总次数。
     - select gender, count(*) from tb_trip_1904 group by gender;
     - 特别注意！！！！在分组查询中，select后面仅能够出现聚合函数或分组字段，未参加分组的字段，不能出现在select后面。
     - select class,avg(score) from student2 group by class;
     - 以上述语句为例，select后面可以出现avg(score)，也可以出现class字段，但是不能出现id和username，因为没有参加分组。
     - 注意！！！在group by之后，可以包含多个字段，以逗号分割。实际执行逻辑是先按第一个字段的值将表格拆分成多个子表，然后再对第二个字段的值，对每个子表进行进一步拆分，以此类推。
     - 例子：
     - ![](https://img.99couple.top/20200603180128.png)
   - limit子句（MySQL方言，限定查询到的数据的条数）
     - select * from 表名 limit 1  --仅显示一条
     - select * from 表名 limit 2,1   --跳过前两条，显示一条