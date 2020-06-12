# 项目跟进：

1. 创建实体类TripDayHourCount，封装从数据库`tb_day_hour_count`表中查询到的数据：

   - 创建TripDayHourCount类。
   - 依次定义私有属性，生成一系列实体类该有的方法。

2. 创建持久层TripDAO类，从`tb_day_hour_count`表中查询数据：

   - 在TripDAO类中声明listDayHourCount方法，传入年-月-日格式数组。
   - Service交过来的是年-月-日格式的数组，我们要根据年月日，到数据库中去查询出每小时的数据。
   - 将字符串分割成年月日单独的字符串数组，参数绑定。
   - 进行查询，利用无参构造或者有参构造封装为TripDayHourCount 类型对象，然后添加进list中，返回list。
   - 添加测试用例，返回24 * 3 = 72个测试实例

3. 创建实体类TripDayHourCountVO封装返给前端的数据：

   - 实体类package中新建TripDayHourCountVO类。
   - 声明`List<String>`类型xData属性，`Map<String,List<Integer>>`类型yData。

4. 在业务层定义TripDayHourCountVO类型的findDayHourCount方法来处理DAO层交上来的数据：

   - 在TripService类中定义findDayHourCount方法，传入日期字符串信息数组。

   - 调用DAO方法，获取数据。创建xData，创建yDataMap。

   - 利用for循环向xData中添加数据。

   - 因为数据每小时有三个性别，一天有24小时，每个月有30天，所以我们需要定义临时变量来区分时、天。

   - 定义 统计每小时骑行总数的临时变量tempCount、定义标识当前遍历的小时信息的临时变量tempHour、标识当前遍历的数据条数的临时变量dateCount、定义临时保存统计到的一天24小时的骑行数量tempList。

   - for循环遍历数据库传来的每一条数据，对tempHour进行判断来区分是否小时变换了，小时变换了需要保存上一个小时的汇总数据。处理完一条数据后，dateCount++。表示处理完了一条数据，而一天中会有24 * 3 = 72条这样的记录。如果dateCount等于72，就表示一天的数据处理完了，进行字符串拼接，存入yDataMap中。重置临时变量。

   - 利用有参构造创建TripDayHourCountVO 对象，返回vo对象。

   - BUG：之前在数据库补全的时候，是在最后的位置插入的数据，所以本来缺数据的地方被我们添加了为0的数据，但是这个数据在所有数据的最后面，没有在他该在的位置。所有我们要对SQL语句进行修改，按照排序的方式，排好序再返还给我们。语句：

   - ```mysql
     String sql="select * from tb_day_hour_count "
                 + "where trip_year=? and trip_month=? and trip_day=? "
                 +"order by trip_year,trip_month,trip_day,trip_hour";
     ```

     

5. 控制层TripDayHourCountServlet类：

   - 对前端的请求进行判断，非空再调用service层方法，进行JSON转化，否则提示进行勾选。
   - 测试：浏览区访问`localhost:8080/nybikeT/tripDayHourCount?dateArray=2020-04-01,2019-04-03`自主添加指定日期，查看是否正常返回数据。

6. 前端页面：

   - 难点：y的数据数量不固定，要根据用户实际勾选的数量进行动态生成。
   - 定义保存y轴配置的数组mySeries、定义保存用例名称的数组legendData。
   - 利用for循环动态生成myseries中的内容。
   - 测试，查看效果。

# 知识补充：

1. 预编译SQL执行器：
   - PreparedStatement：预编译SQL执行器。
   - 如果单纯使用Statement作为SQL的执行器，每次查询，数据库都会重新执行SQL的执行流程。先获取SQL语句---再解析SQL执行流程--------然后生成执行流程----执行。
   - 事实上的场景是SQL语义的执行流程都是相同的，只是参数不同，针对这种场景有了PreparedStatement，预编译SQL执行器。
   - PreparedStatement会先将带？的SQL语句发给MySQL服务器进行SQL的解析和生成执行流程，然后每次ps执行查询，仅仅是进行替换？代表的参数，之前解析的SQL和生成的执行流程会被一直复用，极大的提高了效率。
2. Servlet获取前端参数函数解析：
   - req.getParameter("参数名")，这个得到的是一个参数，如果原前端提交的是带有同一名字多个参数的表单，那么这个方法只可以获取到同一名字的第一个参数。
   - req.getParameterValues("参数名")，这个得到的是一个数组，用来接收前端同一"参数名"的所有参数，只存值。









