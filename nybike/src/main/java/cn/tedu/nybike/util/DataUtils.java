package cn.tedu.nybike.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataUtils {

	public static void main(String[] args) throws SQLException {
		insertDefaultData();
	}
	
	/**
	 * 遍历tb_day_hour_count表中的数据
	 * 查询出缺失的数据
	 * 并插入默认数据
	 * @throws SQLException 
	 */
	public static void insertDefaultData() throws SQLException {
		Integer year=0;
		Integer month=0;
		Integer day=0;
		Integer dayOfWeek=0;
		Integer hour=0;
		int[] tempArr={-1,-1,-1};
		List<String> insertSqls=new LinkedList<String>();
		// 查询tb_day_hour_count中的全部数据
		String sql1="select * from tb_day_hour_count "
				+ "order by trip_year, trip_month, trip_day, "
				+ "trip_hour, gender";
		Connection conn=DBUtils.getConn();
		PreparedStatement ps=conn.prepareStatement(sql1);
		ResultSet rs=ps.executeQuery();
		// 遍历查询到的数据
		while(rs.next()) {
			if(hour==rs.getInt("trip_hour")) {// 同一个小时内
				// 保存count的值
				tempArr[rs.getInt("gender")]=rs.getInt("trip_count");
			}else { // 进入下一个小时的数据
				// 检查上一个小时的数据
				for(int index=0;index<tempArr.length;index++) {
					// 针对缺少的那一条记录，生成一条插入默认值的SQL语句
					if(tempArr[index]==-1) {
						StringBuffer buffer=new StringBuffer("insert into tb_day_hour_count values(");
						buffer.append(year).append(",").append(month).append(",")
						.append(day).append(",").append(dayOfWeek).append(",")
						.append(hour).append(",").append(index).append(",")
						.append(0).append(")");
						// 将该SQL语句保存起来
						insertSqls.add(buffer.toString());
					}
				}
				// 处理本小时的数据
				hour=rs.getInt("trip_hour");
				tempArr[0]=tempArr[1]=tempArr[2]=-1;
				// 保存count的值
				tempArr[rs.getInt("gender")]=rs.getInt("trip_count");
			}
			// 保存year,month,day,dayOfWeek的值
			year=rs.getInt("trip_year");
			month=rs.getInt("trip_month");
			day=rs.getInt("trip_day");
			dayOfWeek=rs.getInt("trip_dayofweek");
		}
		
		// 向tb_day_hour_count中插入补充的默认数据
		// 遍历保存了SQL语句的集合
		Statement st=conn.createStatement();
		for(String sql:insertSqls) {
			System.out.println(sql);
			// 将SQL语句添加到批中
			st.addBatch(sql);
		}
		// 执行批
		int[] result=st.executeBatch();
		int count=0;
		for(int i:result) {
			count+=i;
		}
		System.out.println("本次执行"+insertSqls.size()+"条语句，成功"+count+"条语句");
		// 关流
		ps.close();
		st.close();
		conn.close();
	}

}
