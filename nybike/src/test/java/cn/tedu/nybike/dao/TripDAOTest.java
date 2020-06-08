package cn.tedu.nybike.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.tedu.nybike.entity.TripDayCount;



public class TripDAOTest {

	TripDAO dao = new TripDAO();
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
