package com.fembase.modules.user.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
	public static String  getMonthLastDay(String date){
		int year=2015;
		int month=1;
		
		if(null!=date&&!"".endsWith(date)){			
			year=Integer.parseInt(date.split("-")[0]);
			month=Integer.parseInt(date.split("-")[1]);
		}	
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,1);
		//cal.add(Calendar.DAY_OF_MONTH, -1);
		 Date firstDate = cal.getTime();
		return tempDate.format(firstDate)+" 00:00:00";
	}

	
	public static void main(String arg[]){
		
		System.out.println(getMonthLastDay("2015-10"));
	}
}
