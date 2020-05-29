# 需求分析：

1. 点击站点，弹出信息窗，显示站点实时信息
   - 先实现点击Marker的时候弹出信息窗（固定内容）。
     - 为Marker添加点击事件。（点击事件监听，注意要放在addMarker里面才能保证为每个Marker进行监听）
     - 被点击时，生成一个信息窗（百度地图API）。
       - 这里使用以字符串拼接HTML代码来实现我们想要的显示效果。
       
       - 再在Marker监听里指定当前信息窗，并且显示出来。
       
       - ```javascript
         首先，以字符串形式声明信息窗中显示的内容：
         
         var sContent =
         "<h4 style='margin:0 0 5px 0;padding:0.2em 0'>天安门</h4>" + 
         "<img style='float:right;margin:4px' id='imgDemo' src='img/info_1.png' width='139' height='104' title='天安门'/>" + 
         "<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>天安门坐落在中国北京市中心,故宫的南侧,与天安门广场隔长安街相望,是清朝皇城的大门...</p>" + 
         "</div>";
         然后，创建一个信息窗实例，绑定要显示的内容：
         var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
         最后，在addMarker方法中，添加为marker设置点击事件的代码：
         function addMarker(point,bikeLevel){  // 创建图标对象   
             ...
             // 为Marker添加点击事件
             marker.addEventListener("click", function(){
                 // 在当前marker位置显示信息窗
                 this.openInfoWindow(infoWindow);
             });
             map.addOverlay(marker);    
         }
         ```
     
   - 更改HTML代码，保证与我们的想要的效果一致
     
     - 这里采用老师课前准备的素材包，包括HTML文件和css文件。
     - 我们将HTML代码拷贝粘贴到拼接字符串，用于显示。引用css文件，用以修饰信息窗，也即是拼接字符串内的HTML代码。
     
   - 再实现信息窗中的内容是实时的。
     
     - 上一步我们将老师的HTML代码拿进来做成了拼接字符串sContent，要想实现每个信息窗内的sContent内的内容不同，我们需要定义参数来实现传参，展示。
     
     - 每个Marker使用的sContent是不同的，sContent的变化参数为：站点id，可用车数量，可用桩数量。是根据变量进行变化的。
     - 变量替换：
       - 方式一：通过变量拼接生成新的字符串，达到变量替换
     
         - `var nba=15; var sContent ="..."+" "+nba+" "+"..."`
         - 两个加号之间的nba就是我们变化的参数之一（number_bikes_available）
     
       - 方式二：通过字符串替换的方式（replace方法），
         
         - ```javascript
           var nba=15; 
           var sContent ="..."+" [nba] "+"..."; 
           sContent=sContent.replace(/[nba]/,nba);
           // 将sContent中的 [nba] 替换成 nba变量的值->15 
           ```
         
         - ![](https://img.99couple.top/20200529153359.png)
         
         - 这里我们采用任意一种方式（两种方法只是getInfoWindow方法有区别）：
         
           - 首先声明一个全局Map，用来保存站点和信息窗内容，信息窗内容封装成一个对象，作为Map中的value。
           - 先请求的站点状态数据，所以我们在请求状态数据的回调函数中创建一个空的实例iwData，再把nba，nda赋给iwData，再set进iwDataMap集合内。
           - 接下来到了请求信息数据的回调函数，先通过station_id得到iwData，再赋值name和short_name。
           - 因为我们是通过station_id一整条线传下来的，所以在Marker的点击事件监听中，指向了当前实时的信息窗，也就是我们需要在addMarker方法中拿到station_id，所以我们在addMarker的方法中再添加一个参数station_id。在请求站点信息数据时调用。
           - 同样的方法，在getInfoWindow的方法体中，先get到iwData。然后传参到sContent内进行变量替换。返回这个infoWindow。
2. 地图用例收尾：
   - 代码优化：
     - 上述代码虽然可读性较好，但是在实际开发过程中应该对其进行优化。
     - 减少不必要的对象创建
     - 除核心逻辑以外的js方法，封装在独立文件里，在页面中引入该文件
     - 将对象声明放在开头，方便管理
3. 拓展需求：
   - 在地图右上角添加车图标与桩图标切换的按钮，和官方的情况一样，当点击桩图标时，页面中所有的marker的图片替换成桩的图片，注意，桩和车使用的级别是不同的，应该进行相应的改变
   - 信息窗指针下方默认与Marker的点所在的位置一致，但是这不符合用户的体验。应该改变信息窗指针下方的位置，更符合用户的体验。同时需要注意，在缩放级别改变是，小图标和大图标对应的信息窗指针下方的位置是不同的。
   - (可加分)对信息窗的UI进行优化，或者对地图的其他细节进行优化。
4. 数据可视化：
   - 定义：数据可视化，是关于数据视觉表现形式的科学技术研究。而这些技术方法允许利用图形、图像处理、计算机视觉以及用户界面，通过表达、建模以及对立体、表面、属性以及动画的显示，对数据加以可视化解释。
   - 实现：
     - BI（Business Intelligence）类平台：综合性的商业数据分析和决策平台，从数据导入，数据存储，数据处理，数据分析，数据挖掘，到数据可视化一整套流程，提供简单便捷的操作平台。
       - 成熟的BI平台：Power BI，永洪科技的BI产品，网易的BI产品等
     - 各类可视化插件：ECharts（浏览器端的可视化插件），Python的可视化插件库，R语言的可视化插件模块等等
5. 上手ECharts：[官网]("https://echarts.apache.org/zh/index.html")
   - 由百度开发，后贡献给Apache基金会，被称为Apache孵化器项目。
   - 拷贝echarts.min.js到js文件夹
   - 基于ECharts API开发可视化图表实例，分为三步：
     - 第一步：创建一个ECharts图表的对象，在创建过程中，绑定DOM容器。
     - 第二步：声明图表的配置项，对图表进行全面的配置。
     - 第三步：将图表应用到图表对象上，并实际显示该图表。
   - 但实际上我们需要做的只是学习第二步的设计，可以参考官方图表实例，进行自己的开发。
   - 入门实例中option个属性含义：
     - title：标题
     - tooltip：工具栏，也就是鼠标移动到图标上产生的效果
     - legend：小图标
     - xAxis：x轴
     - yAxis；不能删
     - series：真正配置y轴的地方
   - ![](https://img.99couple.top/20200529204024.png)