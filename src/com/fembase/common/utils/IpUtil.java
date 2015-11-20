/**
 * @(#)Jodo, 2014年6月23日
 */
package com.fembase.common.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author zhangsuiwen  
 *
 */
public class IpUtil {
	private static Log log = LogFactory.getLog(IpUtil.class);
    private static final Pattern IPV4_PATTERN = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
    
    public static boolean isValidIpv4(String ip) {
        return IPV4_PATTERN.matcher(ip).matches();
    }
    
    private static final Pattern IPV6_PATTERN = Pattern.compile("^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b)\\.){3}(\\b((25[0-5])|(1\\d{2})|(2[0-4]\\d)|(\\d{1,2}))\\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$");
    
    public static boolean isValidIpv6(String ip) {
        return IPV6_PATTERN.matcher(ip).matches();
    }
    
    public static boolean isValidIp(String ip) {
        return isValidIpv4(ip) || isValidIpv6(ip);
    }
    
    public static String getIP(HttpServletRequest request){
		String ip=null;
		String X_forwarded_for = request.getHeader("X-Forwarded-For");
		if (null == X_forwarded_for) {
            ip = "127.0.0.1";
            X_forwarded_for = ip;
        }
        if (X_forwarded_for.contains(",")) {
            ip = X_forwarded_for.split(",")[0];
        } else {
            ip = X_forwarded_for;
        }
        if (!IpUtil.isValidIp(ip)) {
        	log.error("ip is not valid, ip=" + ip + ", ips=" + X_forwarded_for);
            ip = "127.0.0.1";
        }
        return ip;
	}
}
