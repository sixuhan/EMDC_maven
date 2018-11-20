package com.briup.util.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.print.attribute.standard.MediaName;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.log.Log;
import com.briup.log.impl.LogImpl;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationAware;
import com.briup.util.WossModuleInit;
import com.briup.web.client.Client;
import com.briup.web.client.Gather;
import com.briup.web.client.impl.ClientImpl;
import com.briup.web.client.impl.GatherImpl;
import com.briup.web.server.DBStore;
import com.briup.web.server.Server;
import com.briup.web.server.impl.DBStoreImpl;
import com.briup.web.server.impl.ServerImpl;

public class ConfigurationImpl implements Configuration{
	private Map map = new HashMap<String, WossModuleInit>();
	public ConfigurationImpl() {
		this("src/main/java/com/briup/util/config.xml");
	}
	public ConfigurationImpl(String add) {
		SAXReader saxReader = new SAXReader();
		Properties properties = new Properties();
		String key;
		String value;
		try {
			Document document = saxReader.read(new File(add));
			//获取根结点
			Element rootElement = document.getRootElement();
			//获取子元素
			List<Element> elements = rootElement.elements();
			//遍历子元素
			for(Element e : elements) {
				String location = e.attributeValue("class"); //获取属性，这个属性包含全限定名，通过这个全限定名，反射得到类型对象
				WossModuleInit o = (WossModuleInit)Class.forName(location).newInstance();
				String name = e.getName();  //获取对象名
				map.put(name, o);
				System.out.println("对象:" + o + " name:" + name);
				List<Element> elements2 = e.elements();
				for(Element e2 : elements2) {
					key = e2.getName();
					value = e2.getText();
					properties.setProperty(key, value);
				}
				o.init(properties);
				
				if(o instanceof ConfigurationAware) {
					((ConfigurationAware)o).setConfiguration(this);					
				}
			
			}
//				Set entrys = map.entrySet();
//				Iterator iter = entrys.iterator();
//				Map.Entry entry;
//				while(iter.hasNext()){
//					entry = (Map.Entry)iter.next();
//					if (entry.getValue() instanceof WossModuleInit) {
//						WossModuleInit oo = (WossModuleInit)entry.getValue();
//						oo.init(properties);
//					}
//					if(entry.getValue() instanceof ConfigurationAware) {
//						((ConfigurationAware)entry.getValue()).setConfiguration(this);	
//					}          
//				} 
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public Log getLogger() throws Exception {
		// TODO Auto-generated method stub
		
		return (Log)map.get("logger");
	}

	@Override
	public Server getServer() throws Exception {
		// TODO Auto-generated method stub
		return (Server)map.get("server");
	}

	@Override
	public Client getClient() throws Exception {
		// TODO Auto-generated method stub
		return (Client)map.get("client");
	}

	@Override
	public DBStore getDbStore() throws Exception {
		// TODO Auto-generated method stub
		return (DBStore)map.get("dbstore");
	}

	@Override
	public Gather getGather() throws Exception {
		// TODO Auto-generated method stub
		return (Gather)map.get("gather");
	}
	public static void main(String[] args) {
		new ConfigurationImpl();
	}
}
