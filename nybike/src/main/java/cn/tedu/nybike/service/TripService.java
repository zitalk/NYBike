package cn.tedu.nybike.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.tedu.nybike.dao.TripDAO;
import cn.tedu.nybike.entity.TripDayCount;
import cn.tedu.nybike.entity.TripDayCountVO;
import cn.tedu.nybike.entity.TripDayCountVO2;

public class TripService {

	TripDAO tripdao = new TripDAO();

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
