package com.fembase.modules.signin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fembase.common.utils.ConfigManager;
import com.fembase.common.utils.HttpUtils;
import com.fembase.common.utils.JacksonUtil;
import com.fembase.common.utils.MemcachedManager;
import com.fembase.modules.signin.entity.TokenInfo;
import com.fembase.modules.signin.service.CheckUserService;

@Service
public class CheckUserServiceImpl implements CheckUserService{
	@Value("#{APP_PROP['checkusr.url']}")
	private String url;
	
	protected Log log = LogFactory.getLog(getClass());
	@Override
	public TokenInfo checkUser(String uid, String sessiontoken) {
		// TODO Auto-generated method stub
		String localsecret=ConfigManager.instance.getValue("SECRET_KEY");
		TokenInfo tokeninfo=null;
		String result=null;
		if(null==uid||"".equals(uid)||sessiontoken.isEmpty()){
			log.error("参数错误  参数为空");
			return null;
		}
		//去memcached获取 token 信息
		tokeninfo = MemcachedManager.instance.getOpenUserInfo(uid);
		
		if(tokeninfo==null){			
			System.out.println("本地memcache没有获取到数据，需要去服务器请求");
			//本地memcache没有获取到数据，需要去服务器请求	
		    //TODO 去服务器那用户信息
			Map<String,String> map=new HashMap<String,String>();
			map.put("uid", uid);
			map.put("sessionToken", sessiontoken);
			result=HttpUtils.doPost(url, map);
			System.out.println("返回的result="+result);
			if(result!=null){			
				tokeninfo=JacksonUtil.toJava(result, TokenInfo.class);
			}
		}
		//
		if(tokeninfo==null){
			log.error("用户检验信息失败  ");		
		}	
		return tokeninfo;
	}

}
