package com.fembase.modules.signin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fembase.common.utils.HttpUtils;
import com.fembase.modules.signin.service.ModifyPointService;

@Service
public class ModifyPointServiceImpl implements ModifyPointService {	
	@Value("#{APP_PROP['AddPoint.url']}")
	private String addurl;
	@Value("#{APP_PROP['SubPoint.url']}")
    private String suburl;
	@Override
	public int addPoint(String uid,String point,String type){
		// TODO Auto-generated method stub
		int result=0;
		Map<String, String> map=new HashMap<String, String>(1);
		if(null==uid||"".equals(uid)){
			map.put("uid",uid);
			map.put("point", point);
			map.put("type", type);
		}
		String count=HttpUtils.doPost(addurl, map);
		if(null!=count){
			result=Integer.parseInt(count);
		}
		return result;
	}

	@Override
	public int subPoint(String uid,String point) {
		// TODO Auto-generated method stub
		int result=0;
		Map<String, String> map=new HashMap<String, String>(1);
		if(null==uid||"".equals(uid)){
			map.put("uid",uid);
			map.put("point", point);
		}
		String count=HttpUtils.doPost(suburl, map);
		if(null!=count){
			result=Integer.parseInt(count);
		}
		return result;
	}

}
