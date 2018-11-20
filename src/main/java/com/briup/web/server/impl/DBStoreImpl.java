package com.briup.web.server.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.log.impl.LogImpl;
import com.briup.util.Configuration;
import com.briup.web.server.DBStore;

public class DBStoreImpl implements DBStore{
	private Log log;
	private Configuration con;
	private static String driver = null;
	private static String user = null;
	private static String password = null;
	private static String url = null;
	private static int batch_size = 0;
	
	
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		driver = properties.getProperty("driver");
		password = properties.getProperty("password");
		batch_size = Integer.parseInt(properties.getProperty("batch_size"));			
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con = con;
	}

	@Override
	public void saveDb(Collection<Environment> coll) throws Exception {
		log = con.getLogger();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName(driver);            //注册驱动
			log.info("注册驱动成功");
			connection = DriverManager.getConnection(url, user, password);
			log.info("连接Oracle数据库");
			Calendar cal = Calendar.getInstance(); 
			int i = cal.get(Calendar.DAY_OF_MONTH);        //通过Calendar类,获得今天是这个月的第几天
			int index = 0;
			String sql = "insert into e_detail_" + i + " values(?,?,?,?,?,?,?,?,?,?)" ;   //构建同构sql语句
			ps = connection.prepareStatement(sql);     
			System.out.println(sql);
			log.info("数据插入中，请稍后");
			for(Environment e : coll) {						
				ps.setString(1, e.getName());
				ps.setString(2, e.getSrcId());
				ps.setString(3, e.getDstId());
				ps.setString(4, e.getDevId());
				ps.setString(5, e.getSersorAddress());
				ps.setInt(6, e.getCount());
				ps.setString(7, e.getCmd());
				ps.setInt(8, e.getStatus());
				ps.setFloat(9, e.getData());
				ps.setTimestamp(10, e.getGather_date());
				ps.addBatch();
				index++;
				if(index%batch_size==0) {
					ps.executeBatch();
					//清空批处理集合
					ps.clearBatch();
				}
			}
			ps.executeBatch();
			log.info("数据插入完成");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(ps != null) {
				ps.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}

}
