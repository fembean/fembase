package com.fembase.modules.zzb.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fembase.modules.zzb.IpEmtity;
import com.fembase.modules.zzb.dbhelper.DBHelper;
import com.fembase.modules.zzb.service.DealIpEmtityService;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DealIpEmtityServiceImpl implements DealIpEmtityService{

	
	@Override
	public boolean isExit(String ip) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(null ==ip ||"".equals(ip)){
			System.out.println("ip 为空不进保存");
			return false;
		}
		
		String sql = null;  
   	    DBHelper db1 = null;  
   	    ResultSet ret = null;
   	    sql = "select * from ip_record where ip='"+ip+"'";//SQL语句  
        db1 = new DBHelper(sql);//创建DBHelper对象 
        String ips = null;
        try {  
            ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	ips = ret.getString(2);
            	if(null!=ips){
            		 flag=true;
                     break;
            	}               
            }//显示数据  
            ret.close();  
            db1.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
		
		return flag;
	}

	@Override
	public boolean inserEmtity(IpEmtity ip) {
		// TODO Auto-generated method stub
		SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = null; 
		DBHelper db1 = null; 
		Connection conn = null;  
		Statement stmt = null;
   	    sql = "insert into ip_record(id,ip,port,area,type,requestType,requestTime,createTime)"
   	    		+ " values (id,'"+ip.getIp()+"','"+ip.getPort()+"','"+ip.getArea()+"','"+ip.getType()+"','"+ip.getRequestType()+"','"+ip.getRequestTime()+"','"+myFmt2.format(new Date())+"')";//SQL语句  
      
   	    System.out.println("sql="+sql);
		try {
			  db1 = new DBHelper();//创建DBHelper对象 
			  conn=db1.getConnect();
			  stmt=(Statement) conn.createStatement();
			  stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try{
			stmt.close();
			conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
        
		
		return false;
	}

}
