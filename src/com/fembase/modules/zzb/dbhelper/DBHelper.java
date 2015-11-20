package com.fembase.modules.zzb.dbhelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DBHelper {  
    public static final String url = "jdbc:mysql://127.0.0.1/fembase?useUnicode=true&characterEncoding=utf-8";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "92ca7056ed";  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
    public DBHelper(){}
    public DBHelper(String sql) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接  
            pst = (PreparedStatement) conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public Connection getConnect(){
    	Connection conn=null;
        try {  
            Class.forName(name);//指定连接类型  
            conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接  
           // pst = (PreparedStatement) conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
        return conn;
    	
    }
    
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static void main(String arg[]){
    	
    	   String sql = null;  
    	   DBHelper db1 = null;  
    	    ResultSet ret = null;
    	    sql = "select * from sign_in";//SQL语句  
            db1 = new DBHelper(sql);//创建DBHelper对象 
            
            try {  
                ret = db1.pst.executeQuery();//执行语句，得到结果集  
                while (ret.next()) {  
                    String uid = ret.getString(1);  
                    String sign_count = ret.getString(2);  
                    String last_signin = ret.getString(3);  
                    String continuous = ret.getString(4);  
                    System.out.println(uid + "\t" + sign_count + "\t" + last_signin + "\t" + continuous );  
                }//显示数据  
                ret.close();  
                db1.close();//关闭连接  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
  

