package com.briup.web.server.impl;

import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


import com.briup.bean.Environment;
import com.briup.log.Log;
import com.briup.log.impl.LogImpl;
import com.briup.util.Configuration;
import com.briup.web.server.Server;

/*
 *  服务器应该一直开启，同时接收多个客户端发送数据
 */
public class ServerImpl implements Server{
	private Log log;
	private Configuration con;
	private ServerSocket socket = null;
	private Socket acceptsocket = null;
	private ObjectInputStream objectInputStream = null;
	public static int port = 0;

	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		port = Integer.parseInt(properties.getProperty("port"));
	
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		this.con = con;
	}

	@Override
	public Collection<Environment> reciver() throws Exception {
		//1.构建服务器
				//2.等待客户端连接
				//3.反序列化
				log = con.getLogger();
				socket = new ServerSocket(port);         //创建socket，绑定监听端口
				log.info("服务器端初始化完成，正在监听");
				acceptsocket = socket.accept();          //监听到连接，创建通信用socket
				log.info("监听到客户端连接，创建通信用socket");
				objectInputStream = new ObjectInputStream(new BufferedInputStream(acceptsocket.getInputStream()));
				//创建传输用流对象
				log.info("服务器端创建传输用流对象，准备接收数据");
				ArrayList<Environment> en = (ArrayList<Environment>)objectInputStream.readObject();
				//接收数据
				log.info("数据已接收");
				if(objectInputStream != null) {
					objectInputStream.close();
				}			
				System.out.println(en.size());
				return en;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
				if(acceptsocket != null) {
					try {
						acceptsocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
	}

}
