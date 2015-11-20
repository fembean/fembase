package com.fembase.modules.signin.web;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Service
public class QuartTest extends QuartzJobBean {

	
	public void quartTest1(){
		
		System.out.println("spring  xml  扫描方式");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
}
