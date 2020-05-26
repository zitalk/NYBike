# 需求分析：

1. 第三方地图服务

   - 在HTML页面中显示地图，可以基于第三方地图服务API实现，主流地图服务如“高德地图”、“腾讯地图”、“腾讯地图”

   - 本项目用哪个？这种场景称为“技术选型”。

   - 本项目使用百度地图API，原因如下：
     - 本地图开发纽约市地图服务，只有百度地图可以支持纽约城市的地图。
     - 本项目涉及数据可视化用例，较为流行的第三方插件是“ECharts”，最初由百度开发，那么该API与百度地图的兼容性最佳。

2. 上手百度地图API

   - 百度地图的官方网站上提供了百度地图API的详细教程[--网址--](https://lbsyun.baidu.com/)
   - 地图瓦片原理：![](https://img.99couple.top/20200526151126.png)
   - 注册账号，申请成为开发者，获取密钥AK。

3. 开发百度地图的Hello World实例：

   - 从官方教学实例中复制全部代码，并替换个人AK即可。
   - 部署，运行，查看实际效果。

4. HTML5知识回顾

   - ![](https://img.99couple.top/20200526153236.png)
   - ![](https://img.99couple.top/20200526153252.png)
   - ![](https://img.99couple.top/20200526153312.png)

5. 显示纽约市地图

   - 需求：

   - 在webapp下新建一个nybike.html文件，在该文件中开发纽约市地图用例，以帝国大厦为地图中心，使用15作为默认缩放级别，显示地图。帝国大厦坐标（-73.98635,40.74852）

   - 开启地图鼠标缩放功能`map.enableScrollWheelZoom(true);`

   - 添加平移缩放控件`map.addControl(new BMap.NavigationControl());`

   - 在右下角显示平移缩放控件

     - ```html
       var navControl = new BMap.NavigationControl({
             anchor:BMAP_ANCHOR_BOTTOM_RIGHT ,//显示在右下角
             offset: new BMap.Size(10, 10),//偏移量
             type:BMAP_NAVIGATION_CONTROL_LARGE//显示完整的平移缩放空间
         });
       map.addControl(navControl);
       ```

6. 在地图一个指定位置显示一个标注（Marker）

   - 需求：

     - 在帝国大厦点添加默认标注

     - ```html
       var marker = new BMap.Marker(point);        // 创建标注    
       map.addOverlay(marker);
       ```

     - 在西南方向的邮局添加标注

     - ```html
       var point2 = new BMap.Point(-73.986192, 40.748119);
       var marker2 = new BMap.Marker(point2);
       map.addOverlay(marker2);
       ```

     - 地图层级区分：

     - ![](https://img.99couple.top/20200526185232.png)

7. 

# 补充知识：

## 互联网访问的基本概念：

- IP：192.168.1.1一台主机在互联网上的地址（公网IP）
- DomainName：域名，例如：[吃土少年8](https://99couple.top)
- 用户在浏览器中输入域名，浏览器将该域名转换成IP地址，然后向该IP地址发送请求。
- 浏览器如何知道对应IP？使用DNS域名解析公共服务。
- URL：统一资源定位符，用于定位互联上的一个资源（可以是任何东西）
- 语法格式：协议：//ip（或者域名）：端口号/资源路径/子路径.......
- 协议：http、https、sftp、ssh、ftp等
- 特殊的域名：localhost----默认域名1----对应127.0.0.1
- 端口号：8080 3306 80 1080 等，一台主机上运行着可以多个接受访问的程序，如A和B程序。他们都在这台主机上，被访问时的IP地址都是一样的，所以需要加以端口号来区分到底访问哪个程序。
- 特殊的端口：80端口（默认端口），URL没有显式声明端口则默认访问默认端口。
- ![](https://img.99couple.top/20200526144059.png)

## HTML(Hyper Text Markup Language)

html的中文名是“超文本标记语言”，是当前所有浏览器显示的页面的开发语言。

也可以将html理解成是一种文档的格式。

这个语言的特点：在文档中即可以记录要显示的信息，也可以记录该信息显示的格式。

通过html的标签来指定信息显示的格式，通过标签的内容来保存显示的信息。

`<!DOCTYPE html>`html文档声明，当这样写的时候，默认使用的是html5版本。

`<html></html>`规定了html文档的范围，所有的内容都应该放在该标签内部

`<html>`标签中有2组固定的标签，分别是`<head>`标签和`<body>`标签。

`<head>`标签中声明的是当前页面的一些配置信息和当前页面中引入的其他的资源，例如引入的css文件和js文件

`<meta>`用于声明页面中的一些配置，属性值不同，声明的配置不同。

`<body>`标签中包含的页面中显示的实际内容

## CSS(Cascading Style Sheets)

层叠样式表，用于为html中的标签指定显示的样式，可以实现标签内容和标签样式的分离

- 三种添加CSS的方式：

  - 在标签内使用style属性添加`<div style="color:red">aaa</div>`

  - 在页面上使用style标签添加

  - ```html
    <style type="text/css">
        div {
            color:red;
            font-size:18px;
        }
    </style>
    ```

  - 在.css文件中添加样式，在页面上使用标签引入css`<link rel="stylesheet" href="demo.css">`

- 3种方式的优先级为：属性>页面内部>页面外部,如果为同一个标签添加多次样式，后添加的样式会覆盖先添加的样式。

## JavaScript

JavaScript是一门基于对象的，事件驱动的脚本语言，用于在html页面上添加可执行的逻辑，与html的用户实现互动。

它由网景公司开发，最初叫liveScript，后来该语言吸收了java的面向对象的编程思想，更名为JavaScript，非常热门。

- 添加JS的方式：

  - 在页面上使用script标签添加

  - ```html
    <button onclick="fn1()">点我！</button>
    
    <script>
        function fn1(){
            alert("被点击！")
        }
    </script>
    ```

  - 在.js文件中添加js代码，在页面上使用script标签引入`<script type="text/javascript" src="js/demo.js"></script>`