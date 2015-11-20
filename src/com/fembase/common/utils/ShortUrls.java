package com.fembase.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class ShortUrls {
	 
    public ShortUrls() {
        // TODO Auto-generated constructor stub
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ShortUrls su = new ShortUrls();
        System.out.println(su.generate("h"));
        
        System.out.println(next());
    }
 
    public static String generate(String keyword) {
        final String[] CHARS_DIC = new String[] {"0","1","2","3","4","5","6","7","8","9"};
 
        // final String keyword = "http://sports.sina.com.cn/nba";
        byte[] encryptedTextBytes = null;
 
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            md5Digest.reset();
            md5Digest.update(keyword.getBytes("UTF-8"));
            encryptedTextBytes = md5Digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            String hex1 = Integer.toHexString(0xff & encryptedTextBytes[i * 2]);
            String hex2 = Integer
                    .toHexString(0xff & encryptedTextBytes[i * 2 + 1]);
 
            hex1 = hex1.length() == 1 ? "0" + hex1 : hex1;
            hex2 = hex2.length() == 1 ? "0" + hex2 : hex2;
            int index = (int) Long.parseLong(hex1 + hex2, 16)
                    % CHARS_DIC.length;
            result.append(CHARS_DIC[index]);
        }
 
        return result.toString();
    }
    
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 99999;

	public static synchronized long next() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d",
				date, seq++);
		return Long.parseLong(str);
	} 
    
}
