package com.fembase.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	
	private static final Logger LOGGER = Logger.getLogger(HttpUtils.class);
	
	/**
	 * http post请求
	 * @param url
	 * @param parmMap 参数map
	 * @return
	 */
	public static String doPost(String url,Map<String, String> parmMap){
		String result = null;
        HttpPost httpPost=new HttpPost(url);
		if(parmMap != null){
			List<NameValuePair> nvps=new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> entry:parmMap.entrySet()){
				nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		}
	    HttpResponse response;
	    try{   
	        HttpClient client = new DefaultHttpClient();  
			response = client.execute(httpPost);
			LOGGER.info("dispatch,url="+url+"&status="+response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == 200)  
	        {  
				byte[] bytes = EntityUtils.toByteArray(response.getEntity());
				result = new String(bytes, "UTF-8");
				//System.out.println(result);
	        }
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * httpGet请求
	 * @param url
	 * @return
	 */
	public static String doGet(String url){
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response;
		try{   
	        HttpClient client = new DefaultHttpClient();  
			response = client.execute(httpGet);
			LOGGER.info("dispatch,url="+url+"&status="+response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == 200)  
	        {  
				byte[] bytes = EntityUtils.toByteArray(response.getEntity());
				result = new String(bytes, "UTF-8");
				//System.out.println(result);
	        }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	   return result;
	}
	
	public static void main(String[] args) {
		/*String doPost = doPost("http://freejpoint2.100fi.com/freejpoint2-server/invitefriend/showcode.do", null);
		System.out.println(doPost);*/
		String doGet = doGet("http://freejpoint2.100fi.com/freejpoint2-server/invitefriend/showcode.do");
		System.out.println(doGet);
	}
}
