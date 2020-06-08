package cn.tedu.nybike.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo1 {

	public static void main(String[] args) {

		// 声明数据库所需变量
		String url = "jdbc:mysql://localhost:3306/nybikedb";
		String driverName = "com.mysql.jdbc.Driver";
		String username = "root";
		String password = "123456";

		try {
			Class.forName(driverName);

			Connection con = DriverManager.getConnection(url, username, password);
			Statement st = con.createStatement();
			String sql = "select * from tb_day_count ";

			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				int year = rs.getInt(1);
				int month = rs.getInt(2);
				int day = rs.getInt(3);
				int count = rs.getInt(4);
				System.out.println(year + "-" + month + "-" + day + ":" + count);
				
			}
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
