package cn.tedu.nybike.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.nybike.entity.TripDayCount;
import cn.tedu.nybike.util.DBUtils;

public class JDBCDemo4 {

	public static void main(String[] args) {

		List<TripDayCount> list = listDayCount();
		for (TripDayCount tdc : list) {
			System.out.println(tdc);
		}
	}

	public static List<TripDayCount> listDayCount() {
		List<TripDayCount> list = new ArrayList<TripDayCount>(31);

		try {

			// 从数据库连接池获取连接
			Connection con = DBUtils.getConn();
			// 声明SQL执行器
			Statement st = con.createStatement();
			// 执行SQL语句
			String sql = "select * from tb_day_count ";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				int year = rs.getInt(1);
				int month = rs.getInt(2);
				int day = rs.getInt(3);
				int count = rs.getInt(4);

				// 创建TripDayCount对象，封装一行数据
				TripDayCount tdc = new TripDayCount(year, month, day, count);
				// 放进list里面
				list.add(tdc);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return list;
	}

}
