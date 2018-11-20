package com.briup.web.client.impl;

import com.briup.util.impl.ConfigurationImpl;

public class Test {
	public static void main(String[] args) {
			ConfigurationImpl configurationImpl = new ConfigurationImpl();
			try {
				configurationImpl.getClient().send(configurationImpl.getGather().gather());;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
