package com.briup.web.client.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.briup.bean.Environment;

public class Test {

	public static void main(String[] args) {
		try {
			Collection<Environment> gather = new GatherImpl().gather();
//			for(Environment e: gather) {
//				System.out.println(e);
//			}			
			System.out.println(gather.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
