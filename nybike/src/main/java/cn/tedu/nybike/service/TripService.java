package cn.tedu.nybike.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.tedu.nybike.dao.TripDAO;
import cn.tedu.nybike.entity.DateInfo;
import cn.tedu.nybike.entity.DateInfoVO;
import cn.tedu.nybike.entity.TripDayCount;
import cn.tedu.nybike.entity.TripDayCountVO;
import cn.tedu.nybike.entity.TripDayCountVO2;
import cn.tedu.nybike.entity.TripDayHourCount;
import cn.tedu.nybike.entity.TripDayHourCountVO;

public class TripService {

	TripDAO tripdao = new TripDAO();
	
	
	
	/**
	 * 基于日期信息，查询日期小时信息
	 * @param dateArray 日期信息数组
	 * @return  vo对象给Servlet
	 */
	public TripDayHourCountVO findDayHourCount(String []dateArray) {
		//调用DAO的listDayHourCount（dateArray）方法，从数据库查数据
		List<TripDayHourCount> list = tripdao.listDayHourCount(dateArray);
		//创建xData
		List<String> xData = new ArrayList<String>(24);
		//创建yDataMap  不需要顺序
		Map<String, List<Integer>> yDataMap = new HashMap<String, List<Integer>>(dateArray.length);
		//向xData中添加0,1,2,3,...
		for(int i=0;i<=23;i++) {
			xData.add(i+"");
		}
		//向yDataMap中添加数据 以天 区分一个个数据
			//遍历dao查询到的list 
		    //拿到 TripDayHourCount [tripYear=2020, tripMonth=4, tripDay=1, tripDayOfWeek=4, tripHour=23, gender=2, tripCount=27]
			//整成"year-month-day",List<Integer>
		int tempCount = 0;//每小时骑行总数的临时变量
		int tempHour = 0;//小时信息的临时变量
		int dateCount = 0; //数据条数的临时变量
		//临时保存统计到的一天24小时的骑行数量
		List<Integer> tempList = new ArrayList<Integer>(24);
		for(TripDayHourCount tdhc:list) {
			if(tempHour==tdhc.getTripHour()) {
				//同一小时内,数量相加即可
				tempCount+=tdhc.getTripCount();
			}else {
                //到了新的小时   先保存上一个小时的记录
				tempList.add(tempCount);
				//修改小时信息
				tempHour = tdhc.getTripHour();
				//统计新的小时数据
				tempCount = tdhc.getTripCount();
			}
			//已经处理完一条记录
			dateCount++;
			if(dateCount==72) {
				//已经处理了一天的数据
				//拼接成日期标识
				String tempDate = ""+tdhc.getTripYear()+tdhc.getTripMonth()+tdhc.getTripDay()+"";
				//将最后这个小时的数据添加到tempList  避免判断
				tempList.add(tempCount);
				//向map中保存数据
				yDataMap.put(tempDate, tempList);
				//重置各个临时变量
				tempCount = 0;
				tempHour = 0;
				dateCount = 0;
				tempList = new ArrayList<Integer>(24);
			}
		}
		//创建vo对象
		//向vo中添加xData和yDataMap
		TripDayHourCountVO vo = new TripDayHourCountVO(xData, yDataMap);
		//返回vo对象
		return vo;
	}
	
	/**
	 *去重查询td_day_hour_count表中
	 *所有的日期信息 
	 * @return
	 */
	public DateInfoVO findDateInfo() {
		//调用dao的方法，获取List<DateInfo>
		List<DateInfo> list = tripdao.listDateInfo();
		//创建Map<String, List<String>>   dateInfoMap
		//创建Map时考虑Map是否需要有顺序 ？    需要
		//常用的Map有  HashMap ×  TreeMap ×   LinkedhashMap √
		Map<String, List<String>> dateInfoMap = new LinkedHashMap<String, List<String>>();
		//创建List<String>,保存一年的日期信息
		List<String> tempList = new LinkedList<String>();
		//创建tempYear=0
		int tempYear = 0;
		//遍历List<DateInfo> 向DateInfoMap中填充数据
		for(DateInfo di:list) {
			//获取year   -> 处理跨年的问题  参考向数据库填充数据的思路
			int year = di.getYear();
			//获取tempYear是否等于0   ->tempYear=year
			if(tempYear==0) {
				tempYear=year;
			}else if(tempYear!=year) {
				//获取tempYear是否不等于year  且tempYear！=0
				//一年的整完了，将前一年的数据存入Map集合
				dateInfoMap.put(tempYear+"年", tempList);
				//tempYear=year
				tempYear=year;
				//tempList=new LinkedList（）；
				tempList=new LinkedList<String>();
				
			}
			//获取month、day、dayOfWeekStr，拼接成“0401（周一）”
			StringBuffer buffer = new StringBuffer();
			buffer.append(di.getMonth()<10?"0"+di.getMonth():di.getMonth());
			buffer.append(di.getDay()<10?"0"+di.getDay():di.getDay());
			buffer.append("(").append(di.getDayOfWeekStr()).append(")");
			//将拼接生成的字符串保存到集合List<String>
			tempList.add(buffer.toString());
		
		}
		//手动将最后一年的数据存入Map
		dateInfoMap.put(tempYear+"年", tempList);
		//创建DateInfoVO对象，封装Map<String, List<String>>
		DateInfoVO vo = new DateInfoVO(dateInfoMap);
		
		//返回DateInfoVO对象
		return vo;
	}
	
	
	

	/**
	 * 将数据封装成前端所需格式 并返回
	 * 
	 * @return 前端所需的数据对象
	 */
	public TripDayCountVO findDayCount() {
		List<TripDayCount> list;
		//获取到DAO的数据
		try {
			list = tripdao.listDayCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//定义一个xData
		List<String> xData = new ArrayList<String>();
		
		//定义一个yData
		List<Integer> yData = new ArrayList<Integer>();
		//遍历
		for(TripDayCount tdc:list) {
			
			//获取month
			
			//获取day
			//拼接
			//赋给xData
			xData.add(tdc.getMonth()+"-"+tdc.getDay());
			//获取count
			//赋给yData
			yData.add(tdc.getCount());
		}
	    //封装成vo对象
		TripDayCountVO vo = new TripDayCountVO(xData, yData);
		//返回vo对象
		
		return vo;
	}
	
	
	public TripDayCountVO2 findDayCount2() {
		List<TripDayCount> list;
		//获取到DAO的数据
		try {
			list = tripdao.listDayCount();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		//定义一个xData  用set集合去重
		Set<String> xDataSet = new LinkedHashSet<String>();
		
		//定义一个yData1
		List<Integer> yData1 = new ArrayList<Integer>();
		//定义一个yData2
		List<Integer> yData2 = new ArrayList<Integer>();
		//遍历
		int index = 1;
		for(TripDayCount tdc:list) {
			
			//获取month
			
			//获取day
			//拼接
			//赋给xData
			xDataSet.add(tdc.getMonth()+"-"+tdc.getDay());
			//获取count
			//赋给yData
			
			if(index<=30) {
				
				yData1.add(tdc.getCount());
				
			}else {
				yData2.add(tdc.getCount());
				
			}
			index++;
		}
	    //封装成vo对象
		TripDayCountVO2 vo = new TripDayCountVO2(xDataSet, yData1,yData2);
		//返回vo对象
		
		return vo;
	}
	
}
