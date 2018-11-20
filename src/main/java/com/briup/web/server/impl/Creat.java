package com.briup.web.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Creat {
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
				String s = "create table e_detail_"+ i +"(name varchar2(20),srcId varchar2(5),dstId varchar2(5),devid varchar2(5),sersorAddress varchar2(7),count number(2),cmd  varchar2(5),status number(2),data number(9,4),gather_date date)";
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
