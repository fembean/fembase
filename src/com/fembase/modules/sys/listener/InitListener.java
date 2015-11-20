package com.fembase.modules.sys.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fembase.common.utils.MemcachedUtil;

public class InitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//MemcachedUtil.init();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//MemcachedUtil.destroyAll();
	}


}
