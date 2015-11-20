package com.fembase.modules.shake.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fembase.common.utils.CacheUtils;
import com.fembase.modules.shake.dao.AwardDao;
import com.fembase.modules.shake.dao.UserShakeLogDao;
import com.fembase.modules.shake.entity.Award;
import com.fembase.modules.shake.entity.Shake;
import com.fembase.modules.shake.entity.UserShakeLog;
import com.fembase.modules.shake.utils.CommonUtil;

@Service
@Transactional(readOnly = true)
public class ShakeService {
	
	@Autowired
	private UserShakeLogDao userShakeLogDao;
	@Autowired
	private AwardDao awardDao;
	
	@Transactional(readOnly = false)
	public Award play(Shake shake) {
		int[] rateArray = null;
		String[] awardIdArray = null;
		Map<String, Award> awardMap = null;
		int con = 0;
		Object object = CacheUtils.get(CacheUtils.SIMPLE_CACHE,"rateArray");
		if(object == null){
			Award award = new Award();
			award.setState("1");
			List<Award> findList = awardDao.findList(award);
			awardMap = new HashMap<String, Award>();
			int length = findList.size();
			rateArray = new int[length];
			awardIdArray = new String[length];
			int i=0;
			for(Award a : findList){
				awardMap.put(a.getId(), a);
				int rate = a.getRate();
				con += rate;
				rateArray[i] = con;
				awardIdArray[i] = a.getId();
				i++;
			}
			CacheUtils.put(CacheUtils.SIMPLE_CACHE,"rateArray", rateArray);
			CacheUtils.put(CacheUtils.SIMPLE_CACHE,"awardIdArray", awardIdArray);
			CacheUtils.put(CacheUtils.SIMPLE_CACHE,"awardMap", awardMap);
			CacheUtils.put(CacheUtils.SIMPLE_CACHE,"con", con);
		}else{
			rateArray = (int[])object;
			awardIdArray = (String[])CacheUtils.get(CacheUtils.SIMPLE_CACHE,"awardIdArray");
			awardMap = (Map<String,Award>)CacheUtils.get(CacheUtils.SIMPLE_CACHE,"awardMap");
			con = (int)CacheUtils.get(CacheUtils.SIMPLE_CACHE,"con");
		}
		
		int nextInt = CommonUtil.nextInt(con);
		int targetIndex = 0;
		for(int rate : rateArray){
			if(nextInt < rate){
				break;
			}
			targetIndex++;
		}
		String targetId = awardIdArray[targetIndex];
		Award award = awardMap.get(targetId);
		UserShakeLog log = new UserShakeLog();
		log.setUid(shake.getUid());
		log.setAwardId(targetId);
		log.setCost(shake.getCost());
		log.setResult(award.getMul()*shake.getCost()-shake.getCost());
		log.setCreateTime(new Date());
		userShakeLogDao.insert(log);
		
		return award;
		
	}
	
	
}
