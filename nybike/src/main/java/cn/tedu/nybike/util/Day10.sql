-- 使用库
use nybikedb;

-- 创建表
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

-- 查看当前库下所有表
show tables;


-- 查看一个表的字段和数据类型
desc tb_trip_2004;

-- 查看一张表的建表语句
show create table tb_trip_2004;

-- 查询表中的数据数量
select count(*) from tb_trip_2004;

-- 将csv数据导入数据库表
load data infile 'D:/202004-citibike-tripdata.csv' -- 文件的绝对路径
into table tb_trip_2004 -- 表名
fields terminated by ',' -- 原始文件中的分隔符
optionally enclosed by '"' -- 去掉原始数据前后的双引号
ignore 1 lines; -- 导入时忽略第一行-表头行

-- 查询表中1条实际的记录
select * from tb_trip_2004 limit 1;

-- 从tb_trip_2004的表中统计出结果的表
create table tb_day_count2(
    trip_year int comment '数据的年份',
    trip_month int comment '数据的月份',
    trip_day int comment '数据的日期',
    trip_count int comment '日骑行总数'
);

-- 插入tb_day_count2表
insert into tb_day_count2
select year(starttime) as trip_year,
       month(starttime) as trip_month,
       day(starttime) as trip_day,
       count(*) as trip_count 
from 
       tb_trip_2004 
group by 
       year(starttime),month(starttime),day(starttime);

