package com.briup.web.client.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.briup.bean.Environment;
import com.briup.util.Configuration;
import com.briup.web.client.Gather;

public class GatherImpl implements Gather{

	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfiguration(Configuration con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Environment> gather() throws Exception {
		//1.采集环境数据信息：（从文件radwtmp读取数据）
		/**
		 *需要考虑以下问题
		 * 1.如何读取文件(选什么io流，需要一行一行数据)
		 * io流 ：BufferedReader readLine()
		 * RandomAccessFile 随机文件读取流 readLine()		
		 * 2.读取到一行数据后如何解析（分割字符串）
		 * 	正则表达式"[ | ]"   分割后得到数组strs
		 * 3.得到的每部分数据如何与Environment对象对应
		 * 	判断得到数据是何种环境类型stars【3】.equals("16");
		 * 	16.equals("stars[3]") 避免空指针异常
		 * 	substri 包头不包尾
		 * 4.将得到的数据封装成Environment对象
		 * 5.将得到的Environment对象保存到集合
		 */
		BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/briup/file/radwtmp"));
		ArrayList<Environment> environment= new ArrayList();
		String msg;             //将文件中的一行数据保存进msg
		String[] split;			//字符数组，用以保存拆分出的数据
		String srcid;           //发送端id
		String dstid;				//树莓派id
		String devid;			//实验箱id
		String sersorAddress; //模块传感器地址
		int count;				// 传感器数量
		String cmd;          //指令标号
		String data; 		//数据信息
		int status;			//状态信息
		Date date;			//最原始的数据
		float siduzhi;		//湿度传感器10进制的值
		float wenduzhi;		//湿度传感器10进制的值
		Environment em;  //临时存放environment 对象
		Timestamp gather_date; //时间戳
		while((msg = br.readLine()) != null) {
			split = msg.split("[|]");
			srcid = split[0];
			dstid = split[1];
			devid = split[2];
			sersorAddress = split[3];
			count = Integer.parseInt(split[4]);
			cmd = split[5];
			data = split[6];
			status = Integer.parseInt(split[7]);
			gather_date = new Timestamp(Long.parseLong(split[8]));
			if("16".equals(sersorAddress)) {
				int value1 = Integer.parseInt(data.substring(0, 4),16);
				int value2 = Integer.parseInt(data.substring(4, 8),16);
				siduzhi = (float) (((float)value2*0.00190735)-6);
				wenduzhi = (float)(((float)value1*0.00268127)-46.85);
				em = new Environment("temperature", srcid, dstid, devid, sersorAddress, count, cmd, status, wenduzhi, gather_date);
				environment.add(em);
				em = new Environment("humidity", srcid, dstid, devid, sersorAddress, count, cmd, status, siduzhi, gather_date);				
				environment.add(em);
			}
			if("256".equals(sersorAddress)) {
				int value = Integer.parseInt(data.substring(0, 4), 16);
				em = new Environment("light", srcid, dstid, devid, sersorAddress, count, cmd, status, value, gather_date);
				environment.add(em);
			}
			if("1280".equals(sersorAddress)) {
				int value = Integer.parseInt(data.substring(0, 4), 16);
				em = new Environment("CO", srcid, dstid, devid, sersorAddress, count, cmd, status, value, gather_date);
				environment.add(em);
			}
		}
		return environment;
	}
}
