package com.fembase.common.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fembase.common.config.Global;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcachedUtil {
   
	private static MemcachedClient memcachedClient;
	
	public static MemcachedClient getMemClient(String mcName) {
        return memcachedClient;
    }
    public static void destroyAll() {
        if(!memcachedClient.isShutdown()) {
            try {
            	memcachedClient.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void init() {
    	try {
    		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(Global.getConfig("memcached.Addr")));
    		builder.setConnectionPoolSize(5);
    		memcachedClient = builder.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * add方法（key不允许重复，重复则报错）
	 * @param key  
	 * @param value
	 * @param seconds 单位（秒）ps:（一天=24*60*60）
	 * @return 返回boolean
	 */
	public static boolean add(String key, Object value, int seconds) {  
		boolean state = false;
		try {
			state = memcachedClient.add(key, seconds, value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return state;
    } 
	
	/**
	 * set方法（key允许重复，重复则覆盖原值）
	 * @param key  
	 * @param value
	 * @param seconds 单位（秒）ps:（一天=24*60*60）
	 * @return 返回boolean
	 */
	public static boolean set(String key, Object value, int seconds) {  
		boolean state = false;
		try {
			state = memcachedClient.set(key, seconds, value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return state;
    }
	
	/**
	 * 更新数据
	 * @param key
	 * @param value
	 * @param seconds
	 * @return 返回boolean
	 */
	public static boolean update(String key, Object value, int seconds) {  
		boolean state = false;
		try {
			state = memcachedClient.replace(key, seconds, value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return state;
    }
	
	/**
	 * 删除数据
	 * @param key
	 * @return boolean
	 */
	public static boolean delete(String key){
		boolean state = false;
		try {
			state = memcachedClient.delete(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return state;
	}
	
	/**
	 * 根据key获取obj
	 * @param key
	 * @return
	 */
	public static Object get(String key) {  
        try {
			return  memcachedClient.get(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    } 
}
