# 需求分析：

1. 自定义图片显示标注：

   - 将素材包放入项目工作目录下，便于使用。

   - 了解标注的icon可视区域，以可视区域下方中点为marker的中心点。

   - icon与图片的关系就是相框与相片的关系，并且image以icon的左上角的为原点。

   - ![](https://img.99couple.top/20200527143626.png)

   - 官方API手册：

   - ```javascript
      function addMarker(point) { // 创建图标对象  
       			var opts = { //icon配置参数
       				/*图标的定位锚点。此点用来决定图标与地理位置的关系，
       				 是相对于图标左上角的偏移值，默认等于图标宽度和高度的中间值*/
       				anchor : new BMap.Size(23, 50),
       				//设置图片大小
       				imageSize : new BMap.Size(46, 50)
       			}
       			var myIcon = new BMap.Icon("img/bi_0.png", new BMap.Size(46, 50), opts); //icon类的带参构造，创建一个实例
       			// 创建标注对象并添加到地图   
       			var marker = new BMap.Marker(point, {
       				icon : myIcon
       			});
       			map.addOverlay(marker);
       		}
     ```

   - icon可视区域的锚点：

     - 为什么icon锚点的定位宽是图片的一半？高度不变？
     - 因为默认下是以左上角为原点。我们设置的icon区域和图片大小一致。为了将用户体验提升，我们将原点移动到下面的中间点。此时用户体验最佳。marker是定位点，不可以动。所以我们改动icon，也即是图标的锚点。
     - ![](https://img.99couple.top/20200527143654.png)

2. 在地图上显示所有站点的marker：

   - 基于上一步的addMarker方法，我们只需要拿到所有的站点经纬度信息，这也就是我们为什么选择纽约市共享单车的原因，因为他的数据是完全开放的。
   - [官网数据]("https://www.citibikenyc.com/system-data")
   - [实时数据共享规范]("https://github.com/NABSA/gbfs/blob/master/gbfs.md")
   - [数据下载]("https://gbfs.citibikenyc.com/gbfs/en/station_information.json")
   - 在nymap.html中获取station_information.json的数据（利用jQuery的API，在网页中发送AJAX请求给服务器，获取json数据）
   - ![](https://img.99couple.top/20200527175329.png)
   - 利用JavaScript解析数据，从中得到data.station数组，遍历数组。其中的lat和lon字段就是经纬度信息。
   - 再利用得到的经纬度信息创建一个BMap.Point（lon，lat）实例，再调用addMarker（point）方法，在地图的目标位置生成一个Marker。
   
3. 使用不同的图片显示标注：

   - 需求：在显示站点的标注时，根据当前站点可用车的情况和可用桩的情况，采用不同的图片来显示站点标注
   - 明确具体的需求：什么情况下，用哪种图片显示站点？
   - 可用车站点原则：
     - 声明可用车指数：abi（available bike index），abi=可用车数量/（可用车数量+可用桩数量）
     - 可用车为0：红
     - 可用车数量小于5：黄
     - abi<=0.5：少绿
     - abi>0.5：多绿
     - 可用桩为0：全绿
   - ![](https://img.99couple.top/20200527174818.png)

# 补充知识：

1. 路径问题：
   - 相对路径：指 当前资源 到 目标资源 的路径，在访问时会由于当前资源的不同而变化。实际上是浏览器遇到了资源链接（URL），自动给服务器发送请求，而路径就是当前资源的父路径后面接上了你的URL，这也就解释了我们为什么不可以在URL开头写/。
     - （./指的是当前路径）（../指的是回上一级目录）
   - 绝对路径：完整的资源定位符（完整的URL）
   - 优劣：
     - 相对路径：表述简洁，文件的相对位置不改变就不需要变。不需要以 / 或者协议开头。
     - 绝对路径：不会因为当前文件的位置受影响。需要以 / 或者协议开头。
   
2. JSON：
   
   - JSON是当前非常流行的文档格式（保存数据的格式），特别适合保存具有层级关系的数据。易于人阅读，也易于机器解析和生成，并有效的提升了网络传输效率。
   
   - JSON语法：
   
   - ```json
     {
         "student":[
             "group":{
             "groupname":"第一组",
             "groupmember":[
             
         ]
         
             }
         ]
     }
     ```
   
   - JSON优势：
   
     - 结构简洁，封装的数据，比XML更省空间，具有更高的数据传输效率。
   
     - JSON是ES规范中的子集，故JavaScript支持直接解析JSON格式的数据，读取数据非常方便。
   
     - 示例：
   
     - ```json
       {
           "student":[
               {
                   "groupname":"第一组",
                   "groupmember":[
                       {
                           "name":"令狐冲",
                           "age":18,
                           "gender":"男",
                           "skill":"独孤九剑"  
                       },
                       {
                           "name":"东方不败",
                           "age":20,
                           "gender":"男",
                           "skill":"葵花宝典"
                       }
                   ]
               }
           ]
       }
       ```
   
3. XML：
   - 在XML中，使用标签体来保存数据的内容，使用标签来保存数据的性质和层次。
   
   - 在XML中，所有标签的名称是没有事先规定的，是用户根据自我需要市级编写的。
   
   - XML适用于两个核心场景：
     - 保存和传输有结构性的数据（目前逐步被JSON取代）
     - 作为配置文件使用（现在仍在使用）
     
   - XML解析：从XML格式的字符串中解析出其中的数据。
   
   - 示例：
   
   - ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <student>
         <group>
             <groupname>第一组</groupname>
             <groupmember>
                 <member>
                     <name>令狐冲</name>
                     <age>18</age>
                     <gender>男</gender>
                     <skill>独孤九剑</skill>
                 </member>
                 <member>
                     <name>东方不败</name>
                     <age>20</age>
                     <gender>男</gender>
                     <skill>葵花宝典</skill>
                 </member>
             </groupmember>
         </group>
     </student>
     ```
   
4. 利用jQuery发送AJAX语法：

   - ```javascript
     $.get(url,params,function(result){
     
     })
     url：是本次请求的目标地址
     params：是本次请求携带的请求参数
     function(){}：是本次请求的回调函数，当浏览器收到了服务器发来的针对本次请求的响应时，会自动调用function(){}方法，执行其中的代码。
     function中的参数result，代表的是本次响应收到的实际的数据。result只是一个变量名，这里使用任何变量名都可以。
     ```