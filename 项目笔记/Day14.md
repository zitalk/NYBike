# 知识补充：

1. 静态资源和动态资源
   - 静态资源：指的是用户访问之前，该资源已经存在。不同的用户访问该资源，得到的资源是相同的。常见的静态资源包括：HTML文件、CSS文件、js文件、图片、音频、视频等。
   - 动态资源：指的是用户访问之前，该资源并不存在。在用户访问时，通过代码动态生成该资源，不同的用户访问时，得到的内容一般是不一样的。如：购物车订单页。需要利用技术，在用户访问时，通过代码动态生成资源，在Java中，这类技术有Servlet、JSP、Thymeleaf等。
     - Sun公司最开始推出的生成动态资源的技术是Servlet。.Java文件在项目部署时，会被编译成字节码文件.class，默认在用户第一次访问时实例化。并且会在内存中一直存在，直到服务器关闭或者项目被移除。如果想要使用Servlet动态生成HTML页面的内容，需要在Servlet中编写大量的HTML内容，造成Java代码和HTML标签混合在一起。随着页面的复杂，Servlet的开发难度越来越大，维护难度也越来越大。因此有了JSP技术。
     - JSP是动态生成页面的技术，其内容和HTML文档非常相似，因此非常适合生成HTML的内容，同时也可以嵌入Java代码，JSP本质上是一个Servlet，当用户第一次访问一个JSP时，Web服务器会找到该JSP文件，翻译成.java文件，再编译成.class文件，加载并实例化，然后在内存中运行。
     - 编译后的.Java文件和.class文件会保存在Tomcat下的work文件夹下，查看.Java文件可知，就是我们如果想利用Servlet来实现动态生成页面的代码，这也证实了JSP其实就是一个Servlet。
2. JSP
   - JSP是Sun公司用于动态生成HTML页面内容的技术。JSP分为如下三种：
   - JSP的模板元素：JSP中的HTML内容，包括CSS和JS内容。在翻译成.Java代码时，会以字符串的形式传进out.write方法内。
   - JSP表达式：`<%=java表达式>`，该语法用于在页面上动态输出内容，这部分内容（Java代码）会被作为参数直接传给out.print方法内。
   - JSP的脚本片段：`<% java代码 %>`，该语法用于在页面上执行一段Java代码，脚本判断中的Java代码会被直接复制放入.java文件的_jspservice方法中，因此，脚本片段中可以包含多行代码，但是必须符合Java的语法。
   - 为避免乱码可在首行添加contentType声明，指定字符集。
   - `<%@ page language="java" contentType="text/html; charset=UTF-8"%>`
3. B / S动态生成页面的方式：
   - JSP / Thymeleaf + Servlet 代码来动态生成
     - 优势：一次请求，一次响应即可获取到全部所需内容。
     - 代码运行在服务器上面，可以直接访问到其他对象中的缓存数据，如Session中的数据，ServletContext中的数据，编程更为便捷。
     - 劣势：.使用数据生成页面内容的代码运行在服务器上,消耗服务器资源,如果请求量很大,服务器负担较重。
   - 利用AJAX + JS代码来生成
     - 优势：使用数据生成页面内容的代码运行在浏览器上，使用的是用户的电脑的计算资源，不消耗服务器计算资源。
     - 这种方式更为灵活,htm文档属于静态资源,可以放在静态资源服务器上,提供数据的 Servlet属于动态资源,可以放在动态资源服务器上。Serve仅提供数据,相对于提供这个动态生成的htm页面来说,更节省网络带宽消耗。
     - 劣势：需要多次请求

# 技术路线：

1. 前端工程师：

   - HTML、CSS、JS相关框架   --->页面开发+移动端开发（目前趋于饱和）

2. Java开发

   - JavaEE、流行框架、高并发、分布式

3. 大数据开发 / 运维

   - 建议奔一下这个，前景好、工资高、和所学比较相关。

   - Hadoop、HBase、Hive、Spark、Storm、Flink、Kafka....

4. 运维工程师：

   - Linux，实际部署，出差多。
   - 云平台运维工程师。

5. 测试工程师：

   - 测试工具、测试流程

6. 爬虫 / 数据采集工程师Python

   - 学的简单，本科出来尽量别干，职业前景不好。

7. 人工智能工程师Python

   - 不建议，起步都要硕士。

# 项目跟进：

1. 查询日期信息 - 浏览器发送被勾选的日期信息

   - 首先，应该在动态生成checkbox时，为每个checkbox添加一个`value`属性，值为该`checkbox`对应的日期信息。修改`tripDayHourCount.html`中循环生成`checkbox`的代码：

   - ```javascript
     for(var index in dateArray){
         var year=key.substring(0,4); // 2020年 -> 2020
         var month=dateArray[index].substring(0,2); // 0401(三) -> 04
         var day=dateArray[index].substring(2,4); // 0401(三) -> 01
         var dateStr=year+"-"+month+"-"+day;
         dateChk+="<input type='checkbox' value='"+dateStr+"'>"+dateArray[index]+" ";
     }
     ```

   - 然后，应该在“查询”按钮的点击事件中，获取所有被选中的`checkbox`，并获取`checkbox`的`value`值：

   - ```javascript
     // 为查询按钮添加点击事件
     $("#btn_search").click(function(){
         var array=[]; // 用于保存选中的日期信息
         //1. 获取页面中所有的checkBox，并遍历
         $("input[type='checkbox']").each(function(){
             // 2. 如果当前checkbox有一个属性'checked'的值为true
             if($(this).prop("checked")){ // 说明被选中
                 // 获取该checkbox的value值，存入array数组中
                 array.push($(this).val());
             }
         });
         // console.log(array.toString());   
         // 验证是否至少勾选了一个checkbox
         if(array.length==0){ // 说明未勾选
             alert("请至少勾选1个日期");
             return; // 后续的代码不再执行
         }
     
         //3. 发送AJAX请求，请求对应的日期小时数据
         var url="tripDayHourCount"; // 相对路径 -> TripDayHourCountServlet映射的路径
         var params={
             "dateArray":array.toString()
         }
         $.get(url,params,function(result){
             // 4. 解析响应数据，基于ECharts的API，在part2生成图表
     
         })
     });
     ```

   - 接下来，在服务器上开发`TripDayHourCountServlet`，接受用户发来的请求参数，并在控制台输出对应的内容：

   - ```javascript
     public class TripDayHourCountServlet extends HttpServlet {
         private static final long serialVersionUID = 1L;
     
         protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             // 解决请求参数的乱码问题 -必须写在req.getParameter方法之前
             request.setCharacterEncoding("UTF-8");
             // 获取用户发来的请求参数
             String[] dateArray=request.getParameter("dateArray").split(",");
             // 遍历验证
             for(String str:dateArray) {
                 System.out.println(str);
             }
         }
     }
     ```

   - 配置web.xml，在浏览器进行测试。

