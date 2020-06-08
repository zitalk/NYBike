/**
 * 为刷新按钮绑定的点击事件
 * 动态获取新的状态数据，并更新页面内容
 * @returns
 */
function updateData() {
	//重新
$.get(statusUrl, function(statusData) {
		initPart1(statusData);
		var yData = getYData(statusData.data.stations);

		var pieData = getPieData(xData, yData);

		updateLeft(yData);
		updateRight(pieData);
	});
}

/**
 * 更新左侧图表的方法
 * @param yData
 * @returns
 */
function updateLeft(yData) {
	var option = {

		series : [ {
			data : yData
		} ]
	};
	leftChart.setOption(option);

}
/**
 * 更新右侧图表的方法
 * @param pieData
 * @returns
 */
function updateRight(pieData) {
	var option = {
		series : [ {
			data : pieData.sort(function(a, b) {
				return a.value - b.value;
			})

		} ]
	};
	rightChart.setOption(option);
}

/**
 * 初始化页面图表
 * @returns
 */
function init() {
	//发送AJAX请求，获取站点状态数据
	$.get(statusUrl, function(statusData) {
		console.log(statusData);
		initPart1(statusData)//初始化站点日期和站点数量
		var yData = getYData(statusData.data.stations);
		var pieData = getPieData(xData, yData);
		//初始化左表
		initLeft(yData);
		//初始化右表
		initRight(pieData);
	});

}

/**
 * 初始化数据时间和站点数量
 * @param statusData 站点状态数据
 * @returns 站点类型和站点数量的对应关系
 */
function initPart1(statusData) {
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

}
/**
 * 计算yData
 * @param stations 封装了所有站点状态信息的数组
 * @returns 封装了各类站点数量的数组
 */
function getYData(stations) {
	//声明5个局部变量，分别表示5种类型站点对应的量
	var yData_1 = 0;//无车的站点
	var yData_2 = 0;//可用车小于等于4的站点
	var yData_3 = 0;//可用车小于50%的站点
	var yData_4 = 0;//可用车大于50%的站点
	var yData_5 = 0;//没有可用桩，只有车的站点
	//遍历stations数组
	for ( var index in stations) {
		//获取当前元素-->一个站点数据
		var statu = stations[index];
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
	return [ yData_1, yData_2, yData_3, yData_4, yData_5 ];
}
/**
 * 计算pieData
 * @param xData 站点类型
 * @param yData 不同类型站点的数量
 * @returns 站点类型和站点数量的对应关系
 */
function getPieData(xData, yData) {
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
	var pieData = [];
	for ( var index in xData) {
		var obj = {
			value : yData[index],
			name : xData[index]
		};
		//放进pieData
		pieData.push(obj);

	}
	return pieData;
}
/**
 * 初始化左侧图表
 * @param yData y轴数据
 * @returns
 */
function initLeft(yData) {
	leftChart = echarts.init(document.getElementById('left_container'));

	// 指定图表的配置项和数据 var opts={}
	var option = {
		title : {
			text : '不同类型站点数量',
			left : 'right'
		},
		tooltip : {},
		legend : {
			data : [ '站点数量' ]
		},
		xAxis : {
			data : xData
		},
		yAxis : {},
		series : [ {
			name : '站点数量',
			type : 'bar',
			data : yData
		} ]
	};

	// 使用刚指定的配置项和数据显示图表。
	leftChart.setOption(option);
}
/**
 * 初始化右图表
 * @param pieData 饼图数据
 * @returns
 */
function initRight(pieData) {
	rightChart = echarts.init(document.getElementById('right_container'));

	var option = {
		// 饼图的背景颜色
		backgroundColor : '#2c343c',

		title : {
			text : '不同类型站点占比',
			textStyle : {
				color : '#ccc'
			}
		},

		tooltip : {
			trigger : 'item',
			formatter : '{a} <br/>{b} : {c} ({d}%)'
		},
		// 影响饼图不同区域的颜色深浅
		visualMap : {
			show : false,
			min : 80,
			max : 500,
			inRange : {
				colorLightness : [ 0, 1 ]
			}
		},
		series : [ {
			name : '站点类型',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '50%' ],// 水平位置，垂直位置
			data : pieData.sort(function(a, b) {
				return a.value - b.value;
			}),
			roseType : 'radius',
			label : {
				color : 'rgba(255, 255, 255, 0.3)'
			},
			labelLine : {
				lineStyle : {
					color : 'rgba(255, 255, 255, 0.3)'
				},
				smooth : 0.2,
				length : 10,
				length2 : 20
			},
			itemStyle : {
				color : '#c23531',
				shadowBlur : 200,
				shadowColor : 'rgba(0, 0, 0, 0.5)'
			},
			//动画效果
			animationType : 'scale',
			animationEasing : 'elasticOut',
			animationDelay : function(idx) {
				return Math.random() * 200;
			}
		} ]
	};

	// 使用刚指定的配置项和数据显示图表。
	rightChart.setOption(option);
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