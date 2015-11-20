package com.fembase.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fembase.common.mapper.JsonMapper;

public class OutPutUtil {

	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected static String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static void outPutStreamJson(HttpServletResponse response, Object object) {
		
		try {
			response.reset();
			response.setContentType("text/html; charset=utf-8");
			//response.getWriter().write(JacksonUtil.toJson(object));
			response.getWriter().write(JsonMapper.toJsonString(object));
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
	
	
}
