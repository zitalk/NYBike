# 需求分析：

1. 如何实现显示纽约市所有共享单车的站点功能？

   - 先实现纽约市地图，基于百度API实现。
   - 在地图页面，发送AJAX请求，请求的官方的实时数据接口，获取站点信息数据，格式为JSON格式。
   - 收到响应后，使用JavaScript对响应内容进行解析，从中获取保存了所有站点的stations数组。
   - 遍历该数组，从每一个数组获取站点的经纬度，创建一个Point实例，调用addMarker方法，从而达到实现显示所有站点。

2. 更改addMarker方法实现显示不同的图片

   - 参数除了point之外，还应该有一个代表当前站点具体情况的参数，定义为bikeLevel
   - 在资源url处改成+连接，图片几也就是bikeLevel几，从0-4。
   - 为避免报错，先在实时数据请求的方法中写死bikeLevel，后续再更改。

3. 获取站点的可用车和可用桩数量（站点状态数据）

   - 网址：[station_status]("https://gbfs.citibikenyc.com/gbfs/en/station_status.json")

   - 通过官方文档数据说明文档可知statios_status.json中使我们需要的数据。

   - 接下来在nymap.html中添加代码，请求所有站点的实时状态信息:

   - ```javascript
     //声明站点状态的URL
     //var status_url="https://gbfs.citibikenyc.com/gbfs/en/station_status.json";
     		var status_url="http://106.75.28.204/dataserver/rtdata/status";
     		//发送AJAX请求
     		$.get(status_url,function(statusData){
     			console.log(statusData);
     			//获取每个stations
     			var statusStation = statusData.data.stations;
     			for(var index in statusStation){
     				//每个站点
     				var statu = statusStation[index];
     				//id
     				var station_id = statu.station_id;
     				//可用车数量
     				var nba = statu.num_bikes_available;
     				//可用桩数量
     				var nda = statu.num_docks_available;			                       console.log("id="+station_id+"nba="+nba+"nda="+nda);
     			}
     		});
     ```

4. 计算站点的bikeLlevel：

   - 在nymap.html中声明一个函数bikeLevel：

   - ```javascript
     /**
     		*根据可用车数量和可用桩数量计算bikeLevel
     		*nba ：可用车数量
     		*nda ：可用桩数量
     		*return bikeLevel属于0-4
     		*/
     		function getBikeLevel(nba, nda) {
     			/**
     			* 可用车站点原则：
     			* 声明可用车指数：abi（available bike index），abi=可用车数量/（可用车数量+可用桩数量）
     			* 可用车为0：红  return 0
     			* 可用车数量小于5：黄  return 1
     			* abi<=0.5：少绿  return 2
     			* abi>0.5：多绿  return 3
     			* 可用桩为0：全绿  return 4
     			*/
     			
     			if(nba+nda==0 || nba==0){
     				return 0;
     			}
     			if(nba<5){
     				return 1;
     			}
     			var abi = nba/(nba+nda);
     			if(abi<=0.5){
     				return 2;
     			}
     			if(abi<1){
     				return 3;
     			}
     			if(nda==0){
     				return 4;
     			}
     		}
     ```

   - 再在获取站点状态数据的ajax请求中调用此方法，控制台输出验证一下。

5. 将站点id和bikeLevel保存到Map集合中

   - 先声明一个全局Map集合
   - 将station_id和bikeLevel封装进bikeLevelMap中，在请求站点状态数据时放进bikeLevelMap中。

6.  基于站点id获取bikeLevel，实现目标功能

   - 在获取站点实时信息数据时，通过站点station_id拿到bikeLevelMap中的数据。
   - 再将参数point和bikeLevel传给addMarker调用。

7. 请求bi_undefined.png的异常分析：

   - 基于上述代码，可能会产生标注图标无法显示的Bug，调试之后可以发现请求的图片是`localhost:8080/nybike/img/bi_undefined.png`这个地址，而不是我们预期的以bi_0.png。
   - 原因：
     - 我们存在两次AJAX请求。一次请求的站点信息数据，一次是站点的状态数据。我们需要先在请求状态数据的回调函数内部将bikeLevelset进bikeLevelMap之后才可以在请求信息数据的请求的回调函数中get到bikeLevel。我们上述的代码没有对顺序进行限制，所以可能存在先get后set，也就产生了请求异常。
   - 解决：
     - 因此，在本用例中，应该保证请求状态数据的回调函数先执行，在它执行完毕后，再执行请求信息数据的回调函数。
     - 如果说当前仅有2-3个AJAX请求需要进行上述控制，可以将后边的AJAX的代码放到前面的AJAX的回调函数中。存在多个时，可以采用promise的API实现。
     - 我们这里只有两个，所以我们将请求站点信息数据的AJAX请求放在了请求状态数据的AJAX请求的回调函数的set方法之后，可以完全保证执行的顺序。

8. 当地图缩放级别发生改变时，动态修改标注的图标

   - 需求：

     - 当缩放级别由大到小切换到一个固定点时，应该将大图标切换为小图标，反之，当缩放级别由小到大切换到一个固定的点时，应该将小图标切换为大图标。

   - 监听器 / 事件监听（Listener）

   - 实现：

     - 利用百度地图API提供的地图事件监听API，来监听地图缩放级别改变的事件。
     - 可以明确当地图缩放级别大于14时，使用大图标。
     - 小于15时，使用小图标。

   - Bug：如果我们按照上述代码执行，会产生一个小Bug，就是一但我们产生了缩放变化，就要进行一次图标切换，显然这是没必要的。我们只需要在14这个级别变化一次即可。又因为smallToBig是一个局部变量，没有办法监控他，所以我们添加一个类似于判断链表是否为环的flag标记。命名为isBigIcon。

   - 继续实现：

     - 在添加监听时，加上对isBigIcon的判断，从而实现我们的需求。

     - ```javascript
       //添加一个全局变量，表示当前使用的是大图标还是小图标
       		var isBigIcon = true;
       		
       		//添加地图缩放级别改变的监听
       		map.addEventListener("zoomend", function() {
       			if(this.getZoom() > 14 && isBigIcon==false){
       				switchIcon(true);
       				isBigIcon=true;//使用大图标
       			}
       			if(this.getZoom() < 15 && isBigIcon==true){
       				switchIcon(false);
       				isBigIcon=false;//使用小图标
       			}
       		});
       		/**
       		*实现标注图标类型切换
       		*  smallToBig ： true--->进行大换小
       		*                false-->进行小换大
       		*
       		*/
       		function switchIcon(smallToBig){
       			if(smallToBig==true){
       				alert("执行小换大");
       			}else{
       				alert("执行大换小");
       			}
       		}
       ```

     - 切换图标：

       - 定义一个全局的数组，用来存放每个marker，目的是后续获取每个marker中的icon中的imageurl。

       - 在addmarker中，生成一个marker就放进这个数组中。

       - 在switchIcon方法中，利用for循环遍历出每个marker，利用官方的getIcon方法得到里面的imageUrl，赋给oldImageUrl，再利用JavaScript中的replace方法，利用正则表达式将b替换为s，从而实现大图标到小图标。

       - ```javascript
         function switchIcon(smallToBig) {
         			if (smallToBig == true) {
         				//alert("执行小换大");
         				for ( var index in markerArray) {
         					var marker = markerArray[index];
         					//创建一个新的icon
         					var opts = { //icon配置参数
         						/*图标的定位锚点。此点用来决定图标与地理位置的关系，
         						  是相对于图标左上角的偏移值，默认等于图标宽度和高度的中间值*/
         						anchor : new BMap.Size(23, 50),
         						//设置图片大小
         						imageSize : new BMap.Size(46, 50)
         					}
         					//从之前marker中获取之前的icon中的imageurl
         					// img/bi_0.png
         					// img/si_0.png
         					var oldImageUrl = marker.getIcon().imageUrl;
         					//replace(正则表达式-用于选中被替换的元素，替换成的值)
         					//img/si_0.png--->img/bi_0.png
         					var newImageUrl = oldImageUrl.replace(/s/,"b");
         					var myIcon = new BMap.Icon(newImageUrl, new BMap.Size(46, 50), opts); //icon类的带参构造，创建一个实例
         					//调用marker的setIcon方法，注入新的Icon
         					marker.setIcon(myIcon);
         				}
         			} else {
         				//alert("执行大换小");
         				//遍历所有的marker
         				for ( var index in markerArray) {
         					var marker = markerArray[index];
         					//创建一个新的icon
         					var opts = { //icon配置参数
         						/*图标的定位锚点。此点用来决定图标与地理位置的关系，
         						  是相对于图标左上角的偏移值，默认等于图标宽度和高度的中间值*/
         						anchor : new BMap.Size(5, 10),
         						//设置图片大小
         						imageSize : new BMap.Size(10, 10)
         					}
         					//从之前marker中获取之前的icon中的imageurl
         					// img/bi_0.png
         					// img/si_0.png
         					var oldImageUrl = marker.getIcon().imageUrl;
         					//replace(正则表达式-用于选中被替换的元素，替换成的值)
         					//img/bi_0.png--->img/si_0.png
         					var newImageUrl = oldImageUrl.replace(/b/,"s");
         					var myIcon = new BMap.Icon(newImageUrl, new BMap.Size(10, 10), opts); //icon类的带参构造，创建一个实例
         					//调用marker的setIcon方法，注入新的Icon
         					marker.setIcon(myIcon);
         				}
         			}
         		}
         ```

# 补充知识：

1. 同步和异步

   - ![](https://img.99couple.top/20200528162514.png)

   - 进程：
     - 独立运行的一个程序，进程是系统分配资源的最小单位。
   - 线程：
     - 运行在进程中的独立的逻辑流（代码片段），是CPU调度的最小单位。
     - 线程的运行依赖于现有的进程，包含于某个进程内。共享进程被分配到的内存空间。因此同一进程内的线程可以访问全局变量，也就是并发问题。
   - 原则：
     - 在主线程中，不允许进行耗时操作。（也就是不允许产生阻塞）
     - 多个线程的执行顺序，由CPU的调度来决定，多个线程彼此之间处于“争抢”状态，而不是“排队”状态。
     - 如果需要多个线程按照特定的顺序来执行，则应该手动对线程的执行顺序进行控制。
     - 多个AJAX请求，可以利用JavaScript中的promise中的API来实现控制。

2. 设计模式 之 监听者模式：

   - 设计模式：众多程序员在漫长的开发过程中，对通用性的问题总结的通用性的解决方案。
   - 模式的分支有很多种，例如软件设计模式、架构模式等。。。。。
     - 行业中较为流行的是23种软件设计模式。
     - 设计模式的学习分为3个阶段：
       - 认知阶段：（上学、工作1-2年）
         - 了解23种软件设计模式的基本概念，解决的问题，对应的规范等。
       - 识别阶段：（工作3-5年）
         - 在阅读源码的同时，能够识别出这里应用了哪种设计模式，为什么选择应用这种设计模式。
       - 应用阶段：（高级程序员、架构师）
         - 在项目设计中，实际应用设计模式解决具体问题，保证项目的灵活性
   - 监听者模式：
     - ![](https://img.99couple.top/20200528162539.png)