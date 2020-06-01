# 复习

1. 整体上完成了纽约市共享单车项目

2. 学习了百度地图API

   - 在页面上显示地图

     - ```javascript
       var map = new BMap.Map("container");
       var point = new BMap.Point(经度,纬度);
       map.centerAddZoom(point,15);
       ```

     - BMap是大类，包含所有小类，避免和其他方法重名。

     - 腾讯--->TMap   高德-->AMap，为什么三家地图的API基本上一致？方便迁移。方便抢客户，大体相同，略有不同。

   - 为地图添加控件

     - ```javascript
       var opts={
           配置项
       }
       var control = new BMap.XXXXControl(opts);
       map.addControl(control);
       ```

   - 在地图上添加一个Marker

     - ```javascript
       var point = new BMap.Point(经度,纬度);
       var marker = new BMap.Marker(point);
       map.addOverlay(marker);
       ```

   - 在地图添加一个Marker（自定义图片）

     - ```javascript
       var opts = {
           anchor : new BMap.Size(...),
           imageSize : new BMap.Size(...)
       }
       var iconSize = new BMap.Size(...);
       var icon = new BMap.Icon(imageUrl,iconSize,opts);
       marker.setIcon(icon);
       map.addOverlay;
       ```

   - 为地图添加事件监听

     - ```javascript
       map.addEventListener("事件名",function(){
          事件发生后执行的处理逻辑 
       });
       ```

   - 点击Marker弹出信息窗

     - ```javascript
       marker.addEventListener("click",function(){
           var sContent = "html格式的字符串"//信息窗内容
           var infoWindow = new BMap.InfoWindow(sContent);
           this.openInfoWindow(infoWindow);
       })；
       ```

   - ECharts API

     - 创建ECharts图表对象

       - ```javascript
         var echart = echarts.init(document.getElementById("main"));
         ```

     - 声明ECharts配置对象

       - ```javascript
         var option = {
             title : ....,
             tooltip : ...,
             legend : ....,
             xAsix : ....,
             yAsix : ....,
             series : [{
             name : ...,
             type : ...,
             data : ...
         },{
             name : ...,
             type : ...,
             data : ...  
         }]
         }
         ```

     - 应用ECharts配置对象，显示图表

       - `echart.setOption(option)`

3. Web的基本概念

4. Web的前端相关技术

5. JSON数据格式及解析

6. AJAX

   - 基于原生的js代码（很少用了）

   - 基于jQuery的API

     - ```json
       $.get(url,params,function(result){
           //执行逻辑
       })
       $.post(url,params,function(result){
           
       })
       ```

# 需求：

1. 共享单车实时数据可视化

   - 模拟需求：
     - 运行平台的管理者，需要看到站点的实时数据的统计信息：站点数量、不同类型的站点的具体数量、不同类型站点的占比、当前数据的时间戳、周期性更新数据、
     - 如图：
     - ![](https://img.99couple.top/20200601114435.png)

2. 实现步骤：

   - 总体分为两步，开发页面HTML内容，另一步是获取实时数据，进行展示。（新手期建议先实现图表，在拿到真实数据进行替换，比较形象）实际开发过程中，要先约定图表所需数据的内容和格式。

3. 实现页面效果

   - 在webapp文件夹下创建panel1.html，该页面中就发需求的页面。
   - 步骤一：将panel页面且分为三个部分，上下两个部分，再把下面的切成左右的两个。
   - div标签在页面上显示为一个矩形区域。默认下，di高度由div内部的填充物撑起的。默认占一行。如果希望一行显示多个div，需要改变div独占一行的设置（display:block），可以为div添加css样式（display：inline）或者（display-inline-block）

4. 显示实时数据

   - 时间戳：一个long类型数值，表示自格林威治时间自1970年1月1日午夜0点到当前时间的毫秒值。

   - 获取方法：Java  

   - ```java
     long timestamp = System.currentTimemills()
     ```

   - 1590994360 -->10位长度 -->到s单位

   - 1590994678044  -->  13位长度 ---> 到ms单位

   - 在Java和JavaScript默认都是13位，PHP默认10位

5. 实现思路：

   - ![](https://img.99couple.top/20200601191728.png)

   - ```javascript
     var statusUrl = "xxxx";
     发送AJAX请求，获取站点状态数据
     $.get(statusUrl,function(statusStation){
          获取数据时间戳
          var timeStamp = statusStation.last_updated;
            将时间戳转变成当前时区的字符串表示
            var date = timeStampToDate(timeStamp);
            将时间显示在页面上
            $("#span_id_name").text("xxx"+date);
             获取stations数组的长度
             var length = statusStation.data.stations.length();
             将长度作为站点的数量，显示在页面上
             $("#span_id_name").text("xxx"+length);
     
         声明5个局部变量，分别表示5种类型的站点的数量
         var yData_1 = 0;//无车的站点
         var yData_2 = 0;//可用车小于等于4的站点
         var yData_3 = 0;//可用车小于50%的站点
         var yData_4 = 0;//可用车大于50%的站点
         var yData_5 = 0;//没有可用桩，只有车的站点
         遍历stations数组
         for(var index in statusStation){
              获取当前元素->1个站点的数据
              var statu = statusStation[index];
              获取nba
              var nba = statu.num_bikes_available;
              获取nda
              var nda = statu.num_docks_available;
              判断当前站点所属的类型
              对代表该类型站点的变量进行+1操作 
              var abi = nba/(nba+nda);
              if(nba==0&&nba+nda==0){
                  yData_1++;
              }else if(nba<5){
                  yData_2++;
              }else if(abi<50%){
                  yData_3++;
              }else if(abi>=50%){
                  yData_4++;         
              }else if(nda==0){
                  yData_5++;
              }
              
         }     
          将5个局部变量添加到yData中
          yData = [yData_1, yData_2, yData_3, yData_4, yData_5];
          基于xData和yData生成pieData中的内容
          for(var index in xData){
              var obj = {
                  value : yData[index],
                  name : xData[index]
              };         
              pieData.push(obj);
          }
          调用方法初始化左侧图表
          initLeft();
          调用方法初始化右侧图表
          initRight();
     });
     function timeStampToDate(timeStamp){
         xxxxx;
         return xx;
     }
     
     ```

6. 代码实现：

   - ```javascript
     //发送AJAX请求，获取站点状态数据
     	var statusUrl = "https://gbfs.citibikenyc.com/gbfs/en/station_status.json";
     	$.get(statusUrl, function(statusData) {
     		console.log(statusData);
     		//获取时间戳 并转换为13位的时间戳
     		var timeStamp = statusData.last_updated * 1000;
     		//将时间戳转变为当前时区的字符串表示
     		var date = timeStampToDate(timeStamp);
     
     		//显示在页面上
     		//通过jQuery选中页面上负责显示时间的span
     		//.text(str)是将标签间的内容进行替换的方法
     		$("#dateId").text("数据时间：" + date);
     
     		//获取station数组长度
     		var count = statusData.data.stations.length;
     		//将长度作为站点数量，显示在页面上
     		$("#countId").text("站点总数：" + count);
     		//声明5个局部变量，分别表示5种类型站点对应的量
     		var yData_1 = 0;//无车的站点
     		var yData_2 = 0;//可用车小于等于4的站点
     		var yData_3 = 0;//可用车小于50%的站点
     		var yData_4 = 0;//可用车大于50%的站点
     		var yData_5 = 0;//没有可用桩，只有车的站点
     		//遍历stations数组
     		var statusStation = statusData.data.stations;
     		for ( var index in statusStation) {
     			//获取当前元素-->一个站点数据
     			var statu = statusStation[index];
     			//获取nba
     			var nba = statu.num_bikes_available;
     			//获取nda
     			var nda = statu.num_docks_available;
     			//console.log("nba:"+nba+" nda:"+nda);
     			//进行判断该对yData的哪个数据进行+1
     			var abi = nba / (nba + nda);
     			if (nba + nda == 0 || nba == 0) {
     				yData_1++;
     				//这里没有return所以需要用else if 否则会执行完一个if又进下一个if
     				//也可以五个if里面写break
     			} else if (nba < 5) {
     				yData_2++;
     			} else if (abi <= 0.5) {
     				yData_3++;
     			} else if (abi < 1) {
     				yData_4++;
     			} else if (nda == 0) {
     				yData_5++;
     			}
     		}
     		//将5个局部变量添加到yData中
     		yData = [ yData_1, yData_2, yData_3, yData_4, yData_5 ];
     		console.log(yData);
     		//基于xData和yData生成pieData中的内容
     		/*var yData=[100,150,200,300,217];
     		var pieData=[
     		{value: 100, name: '无车'},
     		{value: 150, name: '<=4辆'},
     		{value: 200, name: '<=50%'},
     		{value: 300, name: '>50%'},
     		{value: 217, name: '满车'}
     		]
     		 */
     		for ( var index in xData) {
     			var obj = {
     				value : yData[index],
     				name : xData[index]
     			};
     			//放进pieData
     			pieData.push(obj);
     
     		}
     
     		//初始化左表
     		initLeft();
     		//初始化右表
     		initRight();
     	});
     	/**
     	 *为刷新按钮绑定点击时间
     	 *动态获取新的状态数据，并更新
     	 */
     	function updateData() {
     		//重新
     		var statusUrl = "https://gbfs.citibikenyc.com/gbfs/en/station_status.json";
     		$.get(statusUrl, function(statusData) {
     			console.log(statusData);
     			var timeStamp = statusData.last_updated * 1000;
     			var date = timeStampToDate(timeStamp);
     			$("#dateId").text("数据时间：" + date);			//获取station数组长度
     			var count = statusData.data.stations.length;
     			$("#countId").text("站点总数：" + count);
     			var yData_1 = 0;//无车的站点
     			var yData_2 = 0;//可用车小于等于4的站点
     			var yData_3 = 0;//可用车小于50%的站点
     			var yData_4 = 0;//可用车大于50%的站点
     			var yData_5 = 0;//没有可用桩，只有车的站点
     			//遍历stations数组
     			var statusStation = statusData.data.stations;
     			for ( var index in statusStation) {
     				//获取当前元素-->一个站点数据
     				var statu = statusStation[index];
     				//获取nba
     				var nba = statu.num_bikes_available;
     				//获取nda
     				var nda = statu.num_docks_available;
     				//console.log("nba:"+nba+" nda:"+nda);
     				//进行判断该对yData的哪个数据进行+1
     				var abi = nba / (nba + nda);
     				if (nba + nda == 0 || nba == 0) {
     					yData_1++;
     					//这里没有return所以需要用else if 否则会执行完一个if又进下一个if
     					//也可以五个if里面写break
     				} else if (nba < 5) {
     					yData_2++;
     				} else if (abi <= 0.5) {
     					yData_3++;
     				} else if (abi < 1) {
     					yData_4++;
     				} else if (nda == 0) {
     					yData_5++;
     				}
     			}
     			//将5个局部变量添加到yData中
     			yData = [ yData_1, yData_2, yData_3, yData_4, yData_5 ];
     			console.log(yData);
     			for ( var index in xData) {
     				var obj = {
     					value : yData[index],
     					name : xData[index]
     				};
     				//放进pieData
     				pieData.push(obj);
     
     			}
     
     			updateLeft(yData);
     			updateRight(pieData);
     		});
     	}
     
     	/**
     	 * 将时间戳转变成时间字符串的方法
     	 * timeStamp 13位的时间戳数据
     	 * 返回数据格式 yyyy-MM-dd HH:mm:ss
     	 */
     	function timeStampToDate(timeStamp) {
     		var d = new Date(timeStamp); //根据时间戳生成的时间对象
     		var date = (d.getFullYear()) + "-" + (d.getMonth() + 1) + "-"
     				+ (d.getDate()) + " " + (d.getHours()) + ":" + (d.getMinutes())
     				+ ":" + (d.getSeconds());
     		return date;
     	}
     ```

7. 实现刷新功能：

   - 分析：
     - ![](https://img.99couple.top/20200601191646.png)
   - 实现刷新有两种方法，第一种是直接将上述所有代码封装成函数，添加点击事件，触发函数，但是效率低，而且浪费资源。因为每次调用都相当于重新绘图。我们实际上只需要做到刷新数据即可。也就是第二种方法：调用官方的异步加载数据API（setOption方法）。我们采用第二种。

   - 实现思路：

     - 需要封装一个刷新函数，通过点击事件来触发他。里面还应该包含两个独立的刷新函数的调用，左表和右表的数据刷新。
     - 我们将leftChart和rightChart从初始化函数中拿出来，定义成全局变量，也就是将左表和右表看成两个对象。
     - 将之前写的异步加载数据的代码复用，写成一个新的updateData函数，里面调用初始化函数改成刷新左右表的数据函数。
     - 左右表的数据刷新函数其实就是在更新y的数据，所以我们只需要将在update函数中拿到的新数据分别作为参数传进来，重写option里的series即可，然后再调用setOption方法，将新的option穿进去即可。
     - 综上，其实我们就是把写好的代码复制粘贴，封装进新函数，再新加了左右表的数据刷新函数而已，只是单纯的避免对表的重绘。

     

     

     

     

     