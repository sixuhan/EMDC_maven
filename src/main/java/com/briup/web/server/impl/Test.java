package com.briup.web.server.impl;

import java.util.Collection;

import com.briup.bean.Environment;
import com.briup.util.impl.ConfigurationImpl;

public class Test {
	public static void main(String[] args) {
		
		ConfigurationImpl configurationImpl = new ConfigurationImpl();
		try {
			configurationImpl.getDbStore().saveDb(configurationImpl.getServer().reciver());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
