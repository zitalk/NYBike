// 基于准备好的dom，初始化echarts实例 var map=new BMap.Map("container")
		// 声明一个echarts图表的实例，命名为myChart
		// init()方法中，传入的是容器的js对象
		// document.getElementById("main") -> 获取当前页面中id值为main的组件的对象
		var myChart = echarts.init(document.getElementById('main'));

		/*
		 * 支付宝：
		 *     支出：[2562.56,2740.20,23178.94,5309.93,2446.42,2694.21,4825.41,2943.75,8916.72,3989.06,4927.69,3305.63]
		 *     收入：[3608.80,3120.08,25084.88,4052.53,2320.40,4203.01,3822.00,4370.00,5610.40,4622.26,4717.00,4089.22]
		 */
		var xData = [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
				"十月", "十一月", "十二月" ];
		var yData = [ 2562.56, 2740.20, 23178.94, 5309.93, 2446.42, 2694.21,
				4825.41, 2943.75, 8916.72, 3989.06, 4927.69, 3305.63 ];
		var yData2 = [ 3608.80, 3120.08, 25084.88, 4052.53, 2320.40, 4203.01,
				3822.00, 4370.00, 5610.40, 4622.26, 4717.00, 4089.22 ];
		var option = {
			title : {
				text : '收入支出表'
			},
			tooltip: {},
            legend: {
                data:['支出','收入']
            },
			xAxis : {
				data : xData
			},
			yAxis : {},
			series : [ {
				name : '支出',
				type : 'bar',//支出
				smooth : true,
				data : yData
			}, {
				name : '收入',
				type : 'bar',//收入
				data : yData2
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option); 