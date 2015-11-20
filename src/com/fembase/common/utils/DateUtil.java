package com.fembase.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    DateFormat _yyyyMMdd_;

    DateFormat _yyyy_MM_dd_hms_;

    DateFormat _yyyyMMddhhmmss_;

    private DateUtil() {
        this._yyyyMMdd_ = new SimpleDateFormat("yyyy-MM-dd");
        this._yyyy_MM_dd_hms_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this._yyyyMMddhhmmss_ = new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static Date parseYMDHMS(String str) {
        try {
            return threadLocal.get()._yyyy_MM_dd_hms_.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseYMD(String str) {
        try {
            return threadLocal.get()._yyyyMMdd_.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatYMD(Date date) {
        return threadLocal.get()._yyyyMMdd_.format(date);
    }

    public static String formatYMDHMS(Date date) {
        return threadLocal.get()._yyyy_MM_dd_hms_.format(date);
    }

    public static String formatyyyymmddhhmmss(Date date) {
        return threadLocal.get()._yyyyMMddhhmmss_.format(date);
    }

    public static int dayDiff(Date d1, Date d2) {
    	
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        long day1_timems = c1.getTimeInMillis();
        
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        long day2_timems = c2.getTimeInMillis();
        
        long msdiff = day1_timems - day2_timems;
        int daydiff = (int) (msdiff / (24 * 60 * 60 * 1000));
        return Math.abs(daydiff);
    }
    
    public static final Date parseTimems(long timems) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timems);
        return c.getTime();
    }
    
    private static final MyThreadLocal threadLocal = new MyThreadLocal();

    private static class MyThreadLocal extends ThreadLocal<DateUtil> {

        @Override
        protected DateUtil initialValue() {
            return new DateUtil();
        }

    }
    
    public static Date getBeginAtDate(Date date){
    	java.sql.Date dateSql= new java.sql.Date(date.getTime());
    	String dateString =dateSql.toString();
		String beginAtDateString=dateString+" 00:00:00";
		//String str2 = dateString+" 23:59:59";
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate=null;
		try {
			startDate = formatDate.parse(beginAtDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startDate;
    }
    
    public static Date getEndAtDate(Date date){
    	java.sql.Date dateSql= new java.sql.Date(date.getTime());
    	String dateString =dateSql.toString();
		String endAtDateString=dateString+" 23:59:59";
		//String str2 = dateString+" 23:59:59";
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate=null;
		try {
			endDate = formatDate.parse(endAtDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endDate;
    }
    /**
     * date:传进来的时间 len:需要改变的分数，正负均可
     */
    public static Date moveHour(String date, int len)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            cal.add(Calendar.HOUR, len);
            return cal.getTime();
        }
        catch (Exception e)
        {
        	
            return null;
        }
    }
    
    /**
     * date:传进来的时间 len:需要改变的天数，正负均可
     */
    public static String getDay(String date, int len)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            cal.add(Calendar.DATE, len);
            return sdf.format(cal.getTime());
        }
        catch (Exception e)
        {
            return date;
        }
    }
    
    /**
     * 获取每一天的某一时间
     * @param time 某一时刻
     * @return
     */
    
    public static String getDateCurrentTime(Date date,String time){
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         return format.format(date)+" "+time;
    }
    

    public static String formatDate(Date date, String formatStr)
    {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }
    
    /**
     * 取得当前的日期时间
     * 
     * @param str 字符串
     * @param format 格式
     * @return 取得当前的日期时间 如果格式不对则返回null
     */
    public static java.util.Date toDateFromStr(String str, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat();
        try
        {
            sdf.applyPattern(format);
            return sdf.parse(str);
        }
        catch (ParseException e)
        {

            return null;
        }
    }
    
    public static void main(String[] args) {
    	//System.out.println(dayDiff(parseYMDHMS("2015-04-16 23:58:44"),parseYMDHMS("2015-04-18 23:27:17")));
	    // System.out.println(getDay("2015-09-20 00:00:00",-1));
    	System.out.println(toDateFromStr("2015-09-20 00:00:00", "yyyy-MM-dd HH:mm:ss"));
    }

}
