# 项目跟进:

1. 接口interface：
   - 可以多个叠加的buff
   - 在现实开发中，接口是一种约定，或者说是一种规范。
   - 在JDBC中，开发者只需要面向接口编程，底层的实现类都是由数据库厂商以jar包的形式来提供。
2. 测试用例开发
   - 在项目的测试类中使用Junit插件，配置Junit的依赖。
   - 在测试类前添加注解@Test，会导入Junit的Test包，run as，Junit会自己配置运行环境，不需要写main方法。
   - 其中测试哪个方法就要生成一个同名的void方法，在里面生成实例，打印输出获取其他测试的方法。
   - 右键run as  Junit Test查看测试结果。
3. entity中的TripDayCountVO类开发
   - 用以封装Service整理好的数据，封装成前端需要的数据格式。
   - get / set方法、tostring方法等等一系列方法。
4. Service层开发
   - 创建service package，并创建TripService类
   - 作为车次数据的业务层类，该类中负责封装具体的业务处理逻辑，负责调用持久层方法获取数据。在本用例中，持久层查询到的数据以`TripDayCount`的集合的形式进行封装，与前端ECharts所需的数据格式不符。 在业务层中，需要将持久层查询到的数据转变成ECharts所需的数据格式。 新的数据格式使用`TripDayCountVO`进行封装。
   - ![](https://img.99couple.top/20200605103511.png)
5. TripDayCountServlet开发
   - 先导入Tomcat的jar包
   - 对servlet在web.xml中进行配置。
   - ![](https://img.99couple.top/20200605210128.png)
   - 添加fastjson插件。
   - 实现service实例化，获取vo对象。重写service方法。如果非空就通知浏览器返回格式，并且利用fastjson将vo对象转成json字符串，写入响应中。
6. JSON插件：
   - 可以将Java对象中的数据提取出来，拼接成JSON字符串格式。   ----  JSON生成器
   - 也可以从JSON字符串中解析数据，封装成Java对象。                 ----   JSON解析器
   - 常用JSON插件，JackSon（外国产）、FastJson（阿里产）
7. 前端页面开发：
   - 新建tripDayCount.html页面。
   - 将Servlet的数据地址赋给变量url，发送AJAX请求。
   - 将数据和配置依次赋给echarts，查看效果。
8. 项目总结

# 知识补充：

1. Servlet是运行在服务器程序上的独立的代码片段，用于处理用户的HTTP请求。本质上，是Sun公司定义的JavaEE的组件规范。
   - Servlet与Web服务器(Tomcat)的关系：
   - ![](https://img.99couple.top/20200605153101.png)
   - 为了保证服务器模块和开发者提供的代码判断能够正确的配合到一起，Sun公司设计了Servlet接口。
   - 其中init（ServletConfig）方法：当一个Servlet对象被创建后，我们的服务器，会马上调用该对象的init方法，实现初始化的逻辑。我们如果希望哪些逻辑希望在Servlet对象在被创建后立即执行，可以放入init方法。
   - destroy（）方法：当服务器准备销毁一个Servlet对象之前，会主动调用该对象的destroy方法。我们如果希望哪些逻辑在Servlet对象销毁之前调用，可以放入destroy方法内。
   - service（）方法：当服务器收到一份Servlet来响应用户的请求时，会调用该Servlet的service方法。处理用户的请求的逻辑应该放入service方法中。
   - init方法，destroy方法，service方法在一个Servlet的生命周期中分别会被调用1次，1次，多次。
   - 还有getServletInfo（）方法和ServletConfig（）方法目前的开发中一般不会应用到。
   - 并且，在实际开发中。我们一般仅仅只需要写service方法中的逻辑。
   - 因此，Sun公司对此重新定义了GenericServlet抽象类，该类实现了Servlet接口，和其他四个方法的空实现。我们现在的实际开发中，只需要继承GenericServlet抽象类即可。
   - 但是，浏览器访问服务器存在HTTP协议和HTTPS协议，GenericServlet是面向两种协议的协议，没有对HTTP协议的特殊支持。
   - 所以，Sun公司又开发了面向HTTP协议的HTTPServlet，继承了GenericServlet，并提供了很多处理HTTP协议请求的