-- 以下每步插入之前都要先建立相应的table
-- 计算每一天不同性别的用户的骑行数量
insert into day_gender_count
select day(starttime) as trip_day,
       count(*) as trip_count
from
       tb_trip_1904
group by
       day(starttime),gender;
       
-- 统计一个月30天中，每小时的总骑行数量(即4月1-30日0点-1点骑行数量的和，4月1-30日1-2点骑行数量的和，以此类推)
insert into hour_count
select hour(starttime) as trip_hour,
       count(*) as trip_count
from   
       tb_trip_1904
group by
       hour(starttime);
       
-- 排名前5的热门起始站点的名称，当月的骑行次数
insert into order_station
select start_station_name,
       count(*) as trip_count
from
       tb_trip_1904
group by
       start_station_name order by count(*) desc limit 5;
       
-- 年龄在18-30之间的各性别用户的平均骑行时长
-- 先对年龄排序知道了最小是2003年的用户
insert into age_gender_time
select gender,
       birth_year,
       avg(TIMESTAMPDIFF(SECOND,starttime,stoptime)) as time
from
       tb_trip_1904
group by
       gender,birth_year;

       
       
       
       