package com.fembase.modules.signin.web;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("zhujiequart")
public class QuartZhuJ {
	@Scheduled(cron="0/2 * * * * ?")
	public void zhuieQuart(){
		
		
		System.out.println("注解的方式执行 定时任务   ");
	}

}
