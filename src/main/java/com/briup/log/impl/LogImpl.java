package com.briup.log.impl;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.log.Log;

public class LogImpl implements Log{
	public static Logger logger = Logger.getLogger(LogImpl.class);
	//只要有一个就够用了，重复生成浪费资源
	
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void debug(String message) {
		// TODO Auto-generated method stub
		logger.debug(message);
	}

	@Override
	public void info(String message) {
		// TODO Auto-generated method stub
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		// TODO Auto-generated method stub
		logger.warn(logger);
	}

	@Override
	public void error(String message) {
		// TODO Auto-generated method stub
		logger.error(message);
		
	}

	@Override
	public void fatal(String message) {
		// TODO Auto-generated method stub
		logger.fatal(message);
		
	}

}
