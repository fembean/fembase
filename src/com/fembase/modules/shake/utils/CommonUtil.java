package com.fembase.modules.shake.utils;

import java.util.Random;

public class CommonUtil {

	private static Random rand = null;  
	/**
	 * 返回一个范围在[0, max)的随机数  
	 * @param max
	 * @return
	 */
    public static int nextInt(int max) {  
        if (rand == null) {  
            synchronized (CommonUtil.class) {  
                if (rand == null) {  
                    rand = new Random(System.currentTimeMillis());  
                }  
            }  
        }  
        return rand.nextInt(max);  
    }
    
    public static void main(String[] args) {
    	for(int i=0; i<100 ; i++){
    		System.out.println(nextInt(10));
    	}
	}
}
