package com.fembase.modules.zzb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.fembase.modules.zzb.service.DealIpEmtityService;
import com.fembase.modules.zzb.service.impl.DealIpEmtityServiceImpl;

public class HttpRequest {
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ; charset=UTF-8");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    public static void main(String arg[]){
    	
    	DealIpEmtityService service=new DealIpEmtityServiceImpl();
    	
    	String url="http://www.xicidaili.com/";   	
    	String respon=sendGet(url,"");
    	Pattern p = Pattern.compile("<table[^>]*>[\\s\\S]*</table>"); 
    	//Pattern p = Pattern.compile("(?i)<table[^>]*?>(?:(?!</?table>)[\\s\\S])*?searchForm(?:(?!</?table>)[\\s\\S])*?(?><table[^>]*?>(?<Open>)|</table>(?<-Open>)|(?:(?!</?table\\b)[\\s\\S])*)*(?(Open)(?!))</table>");
    	Matcher m = p.matcher(respon);
    	
    	while(m.find()){		
    		//System.out.println("====="+m.group());
    		Document doc = Jsoup.parse(m.group());
            Elements trs = doc.select("table").select("tr");
            for(int i = 0;i<trs.size();i++){
                Elements tds = trs.get(i).select("td");
               /* for(int j = 0;j<tds.size();j++){
                    String text = tds.get(j).text();
                    System.out.println(text);
                }*/
                int j=tds.size();
                if(j>=5){
                	if("高匿".equals(tds.get(4).text())){
                		if(service.isExit(tds.get(1).text())){
                			
                			System.out.println("已存在 ip="+tds.get(1).text());
                			continue;
                		}
                    	IpEmtity emtity=new IpEmtity(tds.get(1).text(), tds.get(2).text(),tds.get(3).text(),tds.get(4).text(), tds.get(5).text(), tds.get(6).text());
                       service.inserEmtity(emtity);
                    	System.out.println("获取成功 result=【"+emtity.toString()+"】");
                	}
                      }
                
            }
    	}
    	
    }
}
