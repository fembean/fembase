package com.fembase.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YoumiLog {
private static Log log = LogFactory.getLog(YoumiLog.class);
	
	public static void log(String str){
		log.info(str);
	}
}
