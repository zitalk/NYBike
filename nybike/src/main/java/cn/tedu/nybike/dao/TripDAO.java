package cn.tedu.nybike.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



import cn.tedu.nybike.entity.DateInfo;
import cn.tedu.nybike.entity.TripDayCount;
import cn.tedu.nybike.entity.TripDayHourCount;
import cn.tedu.nybike.util.DBUtils;

/**
 * 骑行数据的持久层 封装了对骑行数据的CURD操作的方法
 * 
 * @author 81895
 *
 */
public class TripDAO {
	/**
	 * 基于日期信息查询小时骑行数据
	 * @param dateArray 日期信息的数组
	 * @return 小时骑行数据
	 */
	public List<TripDayHourCount> listDayHourCount(String []dateArray){
		//声明SQL语句  按年 月 日进行查询
		String sql = "select * from tb_day_hour_count "+
		              "where trip_year=? and trip_month=? and trip_day=? "+
				     "order by trip_year,trip_month,trip_day,trip_hour";
        //声明保存结果的集合		
		List<TripDayHourCount> list = new ArrayList<TripDayHourCount>(dateArray.length);
        //执行查询
		try(Connection con = DBUtils.getConn();
			PreparedStatement ps = con.prepareStatement(sql)){
			
			//遍历dateArray数组
			for(String date:dateArray) {
				// 2020-04-01
				String []paraArray = date.split("-");
				//向ps绑定参数
				ps.setInt(1, Integer.parseInt(paraArray[0]));
				ps.setInt(2, Integer.parseInt(paraArray[1]));
				ps.setInt(3,Integer.parseInt(paraArray[2]));
				//执行查询
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					//获取数据
					//封装为List<TripDayCount>集合
					//利用无参构造来新建，然后set进去
					TripDayHourCount tdhc = new TripDayHourCount();
					tdhc.setTripYear(rs.getInt("trip_year"));
					tdhc.setTripMonth(rs.getInt("trip_month"));
					tdhc.setTripDay(rs.getInt("trip_day"));
					tdhc.setTripDayOfWeek(rs.getInt("trip_dayofweek"));
					tdhc.setTripHour(rs.getInt("trip_hour"));
					tdhc.setGender(rs.getInt("gender"));
					tdhc.setTripCount(rs.getInt("trip_count"));
					//放入集合
					list.add(tdhc);
				}
	            //释放rs
				rs.close();
			}
			
		}catch (Exception e) {
             e.printStackTrace();   
		}
		return list;
	}

	/**
	 * 查询tb_day_hour_count表中的所有日期信息
	 * 
	 * @return
	 */
	public List<DateInfo> listDateInfo() {
		// 声明SQL语句 distinct去重
		String sql = "select distinct trip_year,trip_month,trip_day," + "trip_dayofweek from tb_day_hour_count";// 占不下了拼接一下
		List<DateInfo> list = new LinkedList<DateInfo>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = DBUtils.getConn();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				// 创建无参构造
				DateInfo di = new DateInfo();
				// 对应字段赋值
				di.setYear(rs.getInt("trip_year"));
				di.setMonth(rs.getInt("trip_month"));
				di.setDay(rs.getInt("trip_day"));
				// 会同时自动生成DayOfWeekStr
				di.setDayOfWeek(rs.getInt("trip_dayofweek"));
				// 将数据保存到集合中
				list.add(di);

			}
			rs.close();
			st.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 查询每日骑行总数
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<TripDayCount> listDayCount() throws SQLException {
		List<TripDayCount> list = new ArrayList<TripDayCount>(31);

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

		return list;
	}
}
