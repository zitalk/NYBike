package cn.tedu.nybike.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.tedu.nybike.entity.DateInfo;
import cn.tedu.nybike.entity.TripDayCount;
import cn.tedu.nybike.entity.TripDayHourCount;



public class TripDAOTest {

	TripDAO dao = new TripDAO();
	String []dateArray = {"2020-04-01","2019-02-05"};
	@Test
	public void listDayHoutCount() {
		List<TripDayHourCount>  list = dao.listDayHourCount(dateArray);
		list.forEach(System.out::println);
		System.out.println("size= "+list.size());
	}
	@Test
	public void listDateInfo() {
		List<DateInfo> list = dao.listDateInfo();
		//遍历集合jdk1.8支持的写法
		list.forEach(System.out::println);
	}
	
	
	/**
	 * 测试TripDAO中的listDayCount方法的方法
	 * @throws SQLException
	 */
	@Test
	public void listDayCount()throws SQLException {
		List<TripDayCount> list = dao.listDayCount();
		list.forEach(item->System.out.println(item));
	}
	
	
}
