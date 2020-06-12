# 项目跟进：

1. entity实体类---DateInfoVO类
   - 声明一个`Map<String,List<String>>`类型的一个属性dateInfo，用来封装所有的日期信息数据给前端。
2. 查询日期信息---Service业务层
   - 声明findDateInfo方法。
     - 调用dao的方法，获取List<DateInfo>。
     - 创建Map<String,List<String>> -> dateInfoMap。
     -  创建Map时，考虑一个问题，Map中的key是否需要有顺序？需要。
     - 常用的Map有 HashMap，TreeMap，LinkedHashMap(保证key的顺序和添加顺序一致)。
     - 创建List<String> tempList，保存一年的日期信息。
     - 创建变量tempYear=0。
     -  遍历List<DateInfo>，向dateInfoMap中填充数据。
       - 获取year。
       - 判断tempYear是否等于=0 ->tempYear=year。
       - 判断tempYear是否不等于year，且tempYear!=0。
       - 将前一年的数据存入Map集合。
       - tempYear=新的年份。
       - 将tempList变为新的空集合。
       - 获取month、day、dayOfWeekStr，拼接生成 "0401(一)"。
       - 将拼接生成的字符串保存到集合中 List<String>。
     - 手动将最后一年的数据存入Map集合。
     - 创建DateInfoVO对象，封装Map<String, List<String>>。
     - 返回DateInfoVO对象。
     - 利用Junit测试Service。
3. 查询日期---Servlet控制层
   - 生成service对象，重写service方法。生成vo对象。
   - 指定编码集，将vo转成JSON格式写进响应中。
   - 配置web.xml，指定映射路径为/dateInfo。
4. 查询日期--前端界面
   - ![](https://img.99couple.top/20200610223346.jpg)
   - 定义出相应的div区域。
   - 这里数据的获取采用相对路径，即在父路径下追加url。
   - 发送AJAX请求，获取日期信息。采用模板替换的方式实现数据填充。
   - 为查询按钮添加点击事件。

# 知识补充：

1. 字符集：
   - 浏览器带一套字符集，服务器带一套字符集。如果不指定字符集，那么在传输中就会以各自默认的字符集来接受、发送和解析数据。也就是产生了所谓的乱码。
   - 在JavaEE中，使用response.setContentType("数据类型",charset=xxx)来指定字符集。有两个作用：通知服务器，使用xxx对传输内容编码，通知浏览器，使用xxx对收到的内容进行解码。
   - 注意！utf-8不是字符集，是UNICode字符集的传输规则。
2. 数据库连接池：
   - 在jdk1.7之前，我们需要try catch  final，然后在final中调用close方法。但是到了jdk1.7之后，Sun公司对此进行了优化，可以自动关闭。我们只需在try中定义参数即可。
   - 数据库连接池配置中，会定义最大连接。如果满了，后续的连接就会陷入等待状态，也就是我们看到的转圈圈不动。