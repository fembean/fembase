package com.fembase.modules.sts.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.security.MessageDigest; 
import java.security.GeneralSecurityException; 
 
public class AdxmiSign {
    /**
     * Signature Generation Algorithm
     *
     * @param HashMap<String,String> params 	Request paramenters set, all parameters need to convert to string type before this
     * @param String                 dev_server_secret 	The secret key from Adxmi Developer Control Panel Setting
     * @return String
     * @throws IOException
     */
    public static String getSignature(HashMap<String, String> params, String dev_server_secret) throws IOException {
        // Sort the parameters in ascending order
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
 
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // Traverse the set after sorting, connect all the parameters as "key=value" format
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(dev_server_secret);
        //System.out.println(basestring.toString());
        // Calculate signature using MD5
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
        // Convert the MD5 output binary result to lowercase hexadecimal result.
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }
 
    /**
     * Calculate signature with a completed unsigned URL, append the signature at the end of this URL.
     * 
     * @param String url 	The completed unsigned URL
     * @param String dev_server_secret  	Secret key for calculating signature
     * @return String
     * @throws IOException, MalformedURLException
     */
    public static String getUrlSignature(String url, String dev_server_secret) throws IOException, MalformedURLException {
        try {
            URL urlObj = new URL(url);
            String query = urlObj.getQuery();
            String[] params = query.split("&");
            Map<String, String> map = new HashMap<String, String>();
            for (String each : params) {
                String name = each.split("=")[0];
                String value;
                try {
                    value = URLDecoder.decode(each.split("=")[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    value = "";
                }
                map.put(name, value);
            }
            String signature = getSignature((HashMap<String, String>) map, dev_server_secret);
            return url + "&sign=" + signature;
        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
 
    /**
     * Check the completed URL with signature parameter. Check the signature is correct or not.
     * 
     * @param String url 	The completed URL with signature parameter
     * @param String dev_server_secret 	Secret key for calculating signature
     * @return boolean
     */
    public static boolean checkUrlSignature(String signedUrl, String dev_server_secret) {
        String urlSign = "";
        try {
            URL urlObj = new URL(signedUrl);
            String query = urlObj.getQuery();
            String[] params = query.split("&");
            Map<String, String> map = new HashMap<String, String>();
            for (String each : params) {
                String name = each.split("=")[0];
                String value;
                try {
                	if(each.split("=").length==2){
                		value = URLDecoder.decode(each.split("=")[1], "UTF-8");
                	}else{
                		value = "";
                	}
                    
                } catch (UnsupportedEncodingException e) {
                    value = "";
                }
                if ("sign".equals(name)) {
                    urlSign = value;
                } else {
                    map.put(name, value);
                }
            }
            if ("".equals(urlSign)) {
                return false;
            } else {
                String signature = getSignature((HashMap<String, String>) map, dev_server_secret);
                return urlSign.equals(signature);
            }
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static void main(String[] arg) throws IOException{
    	
//    	checkUrlSignature("http://api.demo.net/postback?order=YM140927--uPMAL-c7&app=9076333dcfc7f490&ad=AdName&adid=4188&user=1067748&"
//    			+ "chn=0&points=979&revenue=1.96&time=1411751092&device=0AD80C3C-D320-AC2B-5FD3-994E2FA7A153&storeid=555610791&"
//    			+ "sign=83a43630aee484db4c9e7470c36ffada","21bd64dc2eaf91f7");
    	
    	HashMap<String, String> map=new HashMap<String, String>();
//    	map.put("sign", "83a43630aee484db4c9e7470c36ffada");
    	map.put("app", "15e99a716c16825a");
    	map.put("time", "1428303333");
    	map.put("storeid", "");
    	map.put("adid", "661898700779425792");
    	map.put("order", "YM150406kQJX2O8353");
    	map.put("device", "355136058614887");
    	map.put("ad", "Clash of Kings-Glispa-HK");
    	map.put("points", "24");
    	map.put("chn", "0");
    	map.put("user", "QuKSAq7WEMQl");
    	map.put("revenue", "0");
    	String str=getSignature(map,"33k7hpiagow7y14i");
    	System.out.println(str);
    	
//    	getUrlSignature("http://api.demo.net/postback?order=YM140927--uPMAL-c7&app=9076333dcfc7f490&ad=AdName&adid=4188&user=1067748&chn=0&points=979&revenue=1.96&time=1411751092&device=0AD80C3C-D320-AC2B-5FD3-994E2FA7A153&storeid=555610791"
//    			,"21bd64dc2eaf91f7");
    }
}
