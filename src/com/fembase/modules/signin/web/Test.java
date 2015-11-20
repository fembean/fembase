package com.fembase.modules.signin.web;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Test extends  QuartzJobBean {

	private int timeout;  
	private static int i = 0;  
	//调度工厂实例化后，经过timeout时间开始执行调度  
	public void setTimeout(int timeout) {  
	this.timeout = timeout;  
	}  
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
	
		
	}
	
	public void executeAction(){
			System.out.println("xml 配置方式 ");
	}
}
