package com.briup.web.client.impl;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.log.impl.LogImpl;
import com.briup.util.Configuration;
import com.briup.util.impl.ConfigurationImpl;
import com.briup.web.client.Client;
import com.briup.web.client.Gather;

public class ClientImpl implements Client{
	private Configuration con;
	private Log log;
	public static String ip = null;
	public static int port = 0;
	
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		port = Integer.parseInt(properties.getProperty("port"));	
		ip = properties.getProperty("ip");
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con = con;
	}

	@Override
	public void send(Collection<Environment> coll) throws Exception {
		// TODO Auto-generated method stub
		//1.连接服务器
				//2.客户端给服务端发送一个集合对象
					//如何发送一个对象
					//什么流可以写出一个对象
				log = con.getLogger();
				Socket socket = null;
				ObjectOutputStream ots = null;
				try {
//					log.info("客户端准备连接。");
					socket = new Socket(ip, port);          //连接服务器
					log.info("客户端连接成功，等待创建客户端传输流对象。");
					ots = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream())); //创建与服务器端传输数据的流对象
					log.info("客户端传输流对象创建完毕，等待发送数据");
					ots.writeObject(coll);      //发送数据
					log.info("数据已发送");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(ots != null) {
						ots.close();
					}
					if(socket != null) {
						socket.close();
					}				
				}
				
				
		
	}

}
