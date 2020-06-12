-- 创建数据库
create database nybikedb;

-- 使用库
use nybikedb;

-- 创建表
create table tb_trip_1904(
tripduration int,
starttime char(26),
stoptime char(26),
start_station_id int,
start_station_name varchar(255),
start_station_latitude double,
start_station_longtitude double,
end_station_id int,
end_station_name varchar(255),
end_station_latitude double,
end_station_longtitude double,
bikeid int,
usertype char(12),
birth_year int,
gender tinyint
)default charset utf8;

-- 查看当前库下所有表
show tables;

-- 查看一个表的字段和数据类型
desc tb_trip_1904;

-- 查看一张表的建表语句
show create table tb_trip_1904;

-- 查询表中的数据数量
select count(*) from tb_trip_1904;

-- 将csv数据导入数据库表
load data infile 'D:/201904-citibike-tripdata.csv' -- 文件的绝对路径
into table tb_trip_1904 -- 表名
fields terminated by ',' -- 原始文件中的分隔符
optionally enclosed by '"' -- 去掉原始数据前后的双引号
ignore 1 lines; -- 导入时忽略第一行-表头行

-- 查询表中1条实际的记录
select * from tb_trip_1904 limit 1;

-- 声明保存统计结果的表
create table tb_trip_1904(
trip_year int,
trip_month int,
trip_day int,
trip_count int
)default charset utf8;

-- 从tb_trip_1904的表中统计出结果的表
create table tb_day_count(
    trip_year int comment '数据的年份',
    trip_month int comment '数据的月份',
    trip_day int comment '数据的日期',
    trip_count int comment '日骑行总数'
);

-- 插入tb_day_count表
insert into tb_day_count
select year(starttime) as trip_year,
       month(starttime) as trip_month,
       day(starttime) as trip_day,
       count(*) as trip_count 
from 
       tb_trip_1904 
group by 
       year(starttime),month(starttime),day(starttime);

-- 创建保存20年4月车次的表格
create table tb_trip_2004(
tripduration int,
starttime char(26),
stoptime char(26),
start_station_id int,
start_station_name varchar(255),
start_station_latitude double,
start_station_longtitude double,
end_station_id int,
end_station_name varchar(255),
end_station_latitude double,
end_station_longtitude double,
bikeid int,
usertype char(12),
birth_year int,
gender tinyint
)default charset utf8;

-- 将csv数据导入数据库表
load data infile 'D:/202004-citibike-tripdata.csv' -- 文件的绝对路径
into table tb_trip_2004 -- 表名
fields terminated by ',' -- 原始文件中的分隔符
optionally enclosed by '"' -- 去掉原始数据前后的双引号
ignore 1 lines; -- 导入时忽略第一行-表头行

-- 插入tb_day_count表
insert into tb_day_count
select year(starttime) as trip_year,
       month(starttime) as trip_month,
       day(starttime) as trip_day,
       count(*) as trip_count 
from 
       tb_trip_2004 
group by 
       year(starttime),month(starttime),day(starttime);
       
-- 创建tb_day_hour_count表
create table tb_day_hour_count(
trip_year int comment '数据的年份',
trip_month int comment '数据的月份',
trip_day int comment '数据的日期',
trip_dayofweek int comment '周几',
trip_hour int comment '数据的小时',
gender int comment '性别',
trip_count int comment '日骑行总数'
)default charset utf8;

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

-- 数据比对 计算总数求和 和原表对比
select sum(trip_count) from tb_day_hour_count;

-- 与2004表中男性骑行数量一致 412624
select 
	sum(trip_count) 
from 
	tb_day_hour_count 
where
     gender=1;

-- 想找出少的6条记录属于哪一天 当天的数据条数小于3
select 
	trip_year, trip_month, trip_day, count(*)
from 
	tb_day_hour_count 
group by
	trip_year, trip_month, trip_day;
-- 4-1少1  4-6少2  4-8少1 4-13少1 4-22少1

-- 如何验证原始数据中4月1日是不是真的少了1条
-- 锁定是哪个小时的数据少了1条
select 
	trip_year, trip_month, trip_day, trip_hour, count(*)
from 
	tb_day_hour_count 
where 
	trip_day=1
group by
	trip_year, trip_month, trip_day, trip_hour;

-- 4月1日 凌晨4点 少了一个性别的用户数据 具体看哪个性别
select 
	*
from 
	tb_day_hour_count
where
	trip_day=1 and 
	trip_hour=4;
-- 缺少性别为0的用户的数据

-- 原始数据中查询该小时是不是真的完全没有对应性别的用户
select 
	count(*)  
from 
	tb_trip_2004
where 
	day(starttime)=1 and 
	hour(starttime)=4 and 
	gender=0;
-- 结果为0，说明原始数据中，
-- 4月1日凌晨4点确实没有性别为0的用户的骑行记录

-- 计算每日每小时不同性别用户的骑行数量，
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
-- 2153条 少7条

-- 是否补充缺失的数据，由后续的需求决定
-- 原则上，应该进行补充
       
       
       
       
       
       
