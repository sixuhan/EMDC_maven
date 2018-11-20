package com.briup.web.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Drop {
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String user = "briup";
	private static String password = "briup";
	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	public static void main(String[] args) {
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, user, password);
			Statement statement = connection.createStatement();
			
			for(int i=1; i<32; i++) {
				String s = "drop table e_detail_"+ i;
				statement.execute(s);
				System.out.println(i+" 成功");
				}
			
		}catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
