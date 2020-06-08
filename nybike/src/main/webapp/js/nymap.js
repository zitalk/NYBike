//添加一个全局变量，表示当前使用的是大图标还是小图标
var isBigIcon = true;
// 保存所有marker的数组
var markerArray = [];
// 声明站点信息的URL
// var info_url="https://gbfs.citibikenyc.com/gbfs/en/station_information.json";
var info_url = "http://106.75.28.204/dataserver/rtdata/info";// 老师的服务器内的数据，避免网络问题
// 声明站点状态的URL
// var status_url="https://gbfs.citibikenyc.com/gbfs/en/station_status.json";
var status_url = "http://106.75.28.204/dataserver/rtdata/status";
// 声明一个保存站点bikeLevel的map集合
var bikeLevelMap = new Map();
// 声明保存信息窗所需数据的Map
var iwDataMap = new Map();

var bigIconSize = new BMap.Size(46, 50);
var smallIconSize = new BMap.Size(10, 10);

var smallIconOpts = { // icon配置参数
	/*
	 * 图标的定位锚点。此点用来决定图标与地理位置的关系， 是相对于图标左上角的偏移值，默认等于图标宽度和高度的中间值
	 */
	anchor : new BMap.Size(23, 50),
	// 设置图片大小
	imageSize : new BMap.Size(46, 50)
}
var bigIconPots = { // icon配置参数
	/*
	 * 图标的定位锚点。此点用来决定图标与地理位置的关系， 是相对于图标左上角的偏移值，默认等于图标宽度和高度的中间值
	 */
	anchor : new BMap.Size(5, 10),
	// 设置图片大小
	imageSize : new BMap.Size(10, 10)
}

var map = new BMap.Map("container");// 创建百度地图实例，绑定地图容器的id
var point = new BMap.Point(-73.985685, 40.748424);
map.centerAndZoom(point, 15);// 初始化地图，设置中心点坐标和地图级别 //创建坐标点实例，设定该坐标经纬度(经度，维度)
map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放

var navControl = new BMap.NavigationControl({// 平移缩放控件
	anchor : BMAP_ANCHOR_BOTTOM_RIGHT,// 显示在右下角
	offset : new BMap.Size(10, 10),// 偏移量
// type:BMAP_NAVIGATION_CONTROL_LARGE//显示完整的平移缩放空间
});
map.addControl(navControl);

// 添加地图缩放级别改变的监听
map.addEventListener("zoomend", function() {
	if (this.getZoom() > 14 && isBigIcon == false) {
		switchIcon(true);
		isBigIcon = true;// 使用大图标
	}
	if (this.getZoom() < 15 && isBigIcon == true) {
		switchIcon(false);
		isBigIcon = false;// 使用小图标
	}
});

// 发送AJAX请求获取实时站点状态数据
$.get(status_url, function(statusData) {
	console.log(statusData);
	// 获取每个stations
	var statusStation = statusData.data.stations;
	for ( var index in statusStation) {
		// 每个站点
		var statu = statusStation[index];
		// 站点id
		var station_id = statu.station_id;
		// 可用车数量
		var nba = statu.num_bikes_available;
		// 可用桩数量
		var nda = statu.num_docks_available;
		var bikeLevel = getBikeLevel(nba, nda);
		// 将站点的id和bikeLevel存入map集合
		bikeLevelMap.set(station_id, bikeLevel);
		// 声明保存iwData的空实例
		var iwData = {};
		// 为iwData添加一个nba属性，值等于可用车数量
		iwData.nba = statu.num_bikes_available;
		iwData.nda = statu.num_docks_available;
		// 将iwData实例存入map集合
		iwDataMap.set(station_id, iwData);

	}
	// 发送AJAX请求获取实时站点信息数据
	$.get(info_url, function(infoData) {

		// 从infoData中获取station数组
		var infoStations = infoData.data.stations;
		for ( var index in infoStations) {// 遍历station数组

			var station = infoStations[index];
			// 获取lon lat属性值 创建BMap.Point实例
			var point = new BMap.Point(station.lon, station.lat);
			// 获取该元素的id属性值
			var station_id = station.station_id;
			// 从bikeLevelMap中获取该站点的对应bikeLevel
			var bikeLevel = bikeLevelMap.get(station_id);
			// 从iwData中获取该站点对应的iwData的实例
			var iwData = iwDataMap.get(station_id);
			// 将name和shortName保存到iwData中
			iwData.name = station.name;
			iwData.shortname = station.short_name;
			// 调用addMarker方法，生成站点标注
			addMarker(point, bikeLevel, station_id);
		}

	});

});

/**
 * 实现标注图标类型切换 smallToBig ： true--->进行大换小 false-->进行小换大
 * 
 */
function switchIcon(smallToBig) {
	if (smallToBig == true) {
		// alert("执行小换大");
		for ( var index in markerArray) {
			var marker = markerArray[index];
			// 从之前marker中获取之前的icon中的imageurl
			// img/bi_0.png
			// img/si_0.png
			var oldImageUrl = marker.getIcon().imageUrl;
			// replace(正则表达式-用于选中被替换的元素，替换成的值)
			// img/si_0.png--->img/bi_0.png
			var newImageUrl = oldImageUrl.replace(/s/, "b");
			var myIcon = new BMap.Icon(newImageUrl, bigIconSize, smallIconOpts); // icon类的带参构造，创建一个实例
			// 调用marker的setIcon方法，注入新的Icon
			marker.setIcon(myIcon);

		}
	} else {
		// alert("执行大换小");
		// 遍历所有的marker
		for ( var index in markerArray) {
			var marker = markerArray[index];
			// 创建一个新的icon

			// 从之前marker中获取之前的icon中的imageurl
			// img/bi_0.png
			// img/si_0.png
			var oldImageUrl = marker.getIcon().imageUrl;
			// replace(正则表达式-用于选中被替换的元素，替换成的值)
			// img/bi_0.png--->img/si_0.png
			var newImageUrl = oldImageUrl.replace(/b/, "s");
			var myIcon = new BMap.Icon(newImageUrl, smallIconSize, bigIconOpts); // icon类的带参构造，创建一个实例
			// 调用marker的setIcon方法，注入新的Icon
			marker.setIcon(myIcon);

		}

	}
}

/**
 * 在地图上生成一个marker的方法 point ：marker在地图上的坐标点 bikeLevel ： marker使用的那张图片，取0-4
 * id:当前站点id
 */
function addMarker(point, bikeLevel, station_id) { // 创建图标对象
	var opts = { // icon配置参数
		/*
		 * 图标的定位锚点。此点用来决定图标与地理位置的关系， 是相对于图标左上角的偏移值，默认等于图标宽度和高度的中间值
		 */
		anchor : new BMap.Size(23, 50),
		// 设置图片大小
		imageSize : new BMap.Size(46, 50)
	}
	var myIcon = new BMap.Icon("./img/bi_" + bikeLevel + ".png", new BMap.Size(
			46, 50), opts); // icon类的带参构造，创建一个实例
	// 创建标注对象并添加到地图
	var marker = new BMap.Marker(point, {
		icon : myIcon
	});
	markerArray.push(marker);

	// 为Marker点击监听
	marker.addEventListener("click", function() {
		// 获取当前marker对应的infowindow实例
		var infoWindow = getInfoWindow2(station_id);
		// 在当前Marker位置显示信息窗
		this.openInfoWindow(infoWindow);

	});

	map.addOverlay(marker);
}

/**
 * 根据可用车数量和可用桩数量计算bikeLevel nba ：可用车数量 nda ：可用桩数量 return bikeLevel属于0-4
 */
function getBikeLevel(nba, nda) {
	/**
	 * 可用车站点原则： 声明可用车指数：abi（available bike index），abi=可用车数量/（可用车数量+可用桩数量）
	 * 可用车为0：红 return 0 可用车数量小于5：黄 return 1 abi<=0.5：少绿 return 2 abi>0.5：多绿
	 * return 3 可用桩为0：全绿 return 4
	 */

	if (nba + nda == 0 || nba == 0) {
		return 0;
	}
	if (nba < 5) {
		return 1;
	}
	var abi = nba / (nba + nda);
	if (abi <= 0.5) {
		return 2;
	}
	if (abi < 1) {
		return 3;
	}
	if (nda == 0) {
		return 4;
	}
}

/**
 * 返回该marker的infowindow实例
 * 
 */
function getInfoWindow(station_id) {
	// stationname <--infoData
	// num_bikes_available <--statusData
	// num_docks_available <--statusData
	// short_name <--infoData

	var iwData = iwDataMap.get(station_id);

	// 信息窗
	var sContent = "<div class='mapbox-content'>"
			+ "<div class='mapbox-content-top'>"
			+ "<span class='window_lastUpdate'> 0 ms ago </span>"
			+ "<span class='window_info_button'></span>" + "</div>"
			+ "<div class='mapbox-content-header'>"
			+ "<h1 class='mapbox-content-header-stationName'>"
			+ iwData.name
			+ "</h1>"
			+ "</div>"
			+ "<div class='mapbox-content-detail'>"
			+ "<div class='mapbox-content-detail-bikes-available'>"
			+ "<span class='mapbox-content-detail-bikes-available-val'> "
			+ iwData.nba
			+ " </span>"
			+ "<span class='mapbox-content-detail-bikes-available-lbl'>Bikes</span>"
			+ "</div>"
			+ "<div class='mapbox-content-detail-docks-available'>"
			+ "<span class='mapbox-content-detail-docks-available-val'> "
			+ iwData.nda
			+ " </span>"
			+ "<span class='mapbox-content-detail-docks-available-lbl'>Docks</span>"
			+ "</div>"
			+ "</div>"
			+ "<div class='mapbox-content-footer'>"
			+ "<span class='mapbox-content-footer-shortName'> Bike station:"
			+ iwData.shortName + " </span>" + "</div>" + "</div>";

	var infoWindow = new BMap.InfoWindow(sContent); // 创建信息窗口对象
	return infoWindow;
}

/**
 * 根据参数，返回该Marker对应的infoWindow实例 演示通过字符串替换的方式生成sContent id : Marker对应的站点的id
 */
function getInfoWindow2(station_id) {
	// 从iwDataMap中获取当前站点对应的信息窗数据
	var iwData = iwDataMap.get(station_id);

	var sContent = "<div class='mapbox-content'>"
			+ "<div class='mapbox-content-top'>"
			+ "<span class='window_lastUpdate'> 0 ms ago </span>"
			+ "<span class='window_info_button'></span>"
			+ "</div>"
			+ "<div class='mapbox-content-header'>"
			+ "<h1 class='mapbox-content-header-stationName'>[station_name]</h1>"
			+ "</div>"
			+ "<div class='mapbox-content-detail'>"
			+ "<div class='mapbox-content-detail-bikes-available'>"
			+ "<span class='mapbox-content-detail-bikes-available-val'>[num_bikes_available]</span>"
			+ "<span class='mapbox-content-detail-bikes-available-lbl'>Bikes</span>"
			+ "</div>"
			+ "<div class='mapbox-content-detail-docks-available'>"
			+ "<span class='mapbox-content-detail-docks-available-val'>[num_docks_available]</span>"
			+ "<span class='mapbox-content-detail-docks-available-lbl'>Docks</span>"
			+ "</div>"
			+ "</div>"
			+ "<div class='mapbox-content-footer'>"
			+ "<span class='mapbox-content-footer-shortName'> Bike station:[short_name]</span>"
			+ "</div>" + "</div>";

	sContent = sContent.replace("[station_name]", iwData.name).replace(
			"[num_bikes_available]", iwData.nba).replace(
			"[num_docks_available]", iwData.nda).replace("[short_name]",
			iwData.shortName);

	var infoWindow = new BMap.InfoWindow(sContent); // 创建信息窗口对象

	return infoWindow;
}