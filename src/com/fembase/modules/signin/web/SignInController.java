package com.fembase.modules.signin.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fembase.common.utils.DateUtil;
import com.fembase.common.utils.OutPutUtil;
import com.fembase.common.utils.ReturnResult;
import com.fembase.common.web.BaseController;
import com.fembase.modules.signin.entity.SignIn;
import com.fembase.modules.signin.service.CheckUserService;
import com.fembase.modules.signin.service.ModifyPointService;
import com.fembase.modules.signin.service.SignInService;


@Controller
@RequestMapping(value = "${adminPath}/sigin/")
public class SignInController extends BaseController{
	protected Log log = LogFactory.getLog(getClass());
	@Autowired
	private CheckUserService checkUserService;
	@Autowired
	private SignInService signInService;
	@Autowired
	private ModifyPointService modifyservice;
	private int startPoint=5;
	private int maxpoint=10;
	private int signMax=5;
	@RequestMapping(value = "sign")
	public void signIn(HttpServletRequest request,HttpServletResponse response){
		String uid=request.getParameter("uid");
		String totken=request.getParameter("token");
		int total=0;
		if(null==uid||"".equals(uid)||null==totken||"".equals(totken)){
		    log.error("参数为空[uid="+uid+",token="+totken+"]");
			ReturnResult result=new ReturnResult();
			result.setState(-1,"参数为空[uid="+uid+",token="+totken+"]");
			OutPutUtil.outPutStreamJson(response, result);
			return ;
		}
		//TODO  去校验用户信息，
	/*	TokenInfo info=checkUserService.checkUser(uid, totken);
		if(info==null){
			ReturnResult result=new ReturnResult();
			result.setState(-1,"用户检验失败");
			OutPutUtil.outPutStreamJson(response, result);
			return ;
		}*/
        //验证成功，下面进行签到操作
		//去数据表中读取玩家的签到消息
		SignIn signinfo=signInService.get(uid);
		Boolean result=false;
		String resultStr="签到成功";
		int day=1;
		boolean isSign=true;
		int getpoint=startPoint;
		if(signinfo==null){
			//用户第一次签到，创建信息
			log.info("用户第一次签到  [uid="+uid+",token="+totken+"]");
			SignIn tempInfo=new SignIn();
			tempInfo.setUid(uid);
			tempInfo.setLastSignin(new Date());
			tempInfo.setContinuous("Y");
			tempInfo.setSigninCount(1);
			tempInfo.setCanSignin("Y");
			result=signInService.save(tempInfo);
			if(result){
				total=modifyservice.addPoint(uid,String.valueOf(getpoint),"1");
				ReturnResult result1=new ReturnResult();
				result1.setState(0,"success");
				Map<String, Object> map=new HashMap<String,Object>(1);
				map.put("point", getpoint);
				map.put("day", day);
				map.put("sign", isSign);
				map.put("total", total);
				result1.setData(map);		
				OutPutUtil.outPutStreamJson(response, result1);
				return ;
			}
		}else{
			//不是新用户
			//允许签到
			if("Y".equals(signinfo.getCanSignin())){				
			 //判读用户是否已经签过到
				Date begin=DateUtil.getBeginAtDate(new Date());
				Date endDate=DateUtil.getEndAtDate(new Date());
				String beginDateStr=DateUtil.getDateCurrentTime(new Date(), "00:00:00");
				String preDateStr=DateUtil.getDay(beginDateStr, -1);
				Date preDateBegin=DateUtil.toDateFromStr(preDateStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				//则表明已经签过到了，不允许再签到
				if(signinfo.getLastSignin().compareTo(begin)>0&&signinfo.getLastSignin().compareTo(endDate)<0){		
					resultStr="已经签到过了";
					ReturnResult result1=new ReturnResult();
					result1.setState(-1,resultStr);	
					result1.setData(0);//返回0积分
					OutPutUtil.outPutStreamJson(response, result1);
					return;
				}
				//前一天没有签到,则不是连续签到
				if(signinfo.getLastSignin().compareTo(preDateBegin)<0){
					signinfo.setContinuous("N");
					signinfo.setSigninCount(1);
					signinfo.setLastSignin(new Date());
					isSign=signInService.update(signinfo);
					resultStr="签到成功";
					
				}
				//正常的签到
				if(signinfo.getLastSignin().compareTo(preDateBegin)>0&&signinfo.getLastSignin().compareTo(begin)<0){
					signinfo.setContinuous("Y");					
					if(signinfo.getSigninCount()<signMax){
						getpoint=startPoint+signinfo.getSigninCount();
					}else{
						getpoint=maxpoint;
					}
					day=signinfo.getSigninCount()+1;
					signinfo.setSigninCount(signinfo.getSigninCount()+1);
					signinfo.setLastSignin(new Date());
					isSign=signInService.update(signinfo);
					resultStr="签到成功";		
				}
			
			}else{
				
				resultStr="该用户不允许签到";
				ReturnResult result1=new ReturnResult();
				result1.setState(-1,resultStr);	
				result1.setData(0);
				OutPutUtil.outPutStreamJson(response, result1);
				return;
			}			
		}		
		total=modifyservice.addPoint(uid,String.valueOf(getpoint),"1");
		ReturnResult result1=new ReturnResult();
		result1.setState(0,"success");
		Map<String, Object> map=new HashMap<String,Object>(1);
		map.put("point", getpoint);
		map.put("day", day);
		map.put("sign", isSign);
		map.put("total", total);
		result1.setData(map);	
		//result1.setData(getpoint);		
		OutPutUtil.outPutStreamJson(response, result1);
		
	}
	
	@RequestMapping(value = "signinfo")
	public void getSign(HttpServletRequest request,HttpServletResponse response){
		String uid=request.getParameter("uid");
		SignIn signinfo=null;
		int day=1;
		int total=0;
		boolean isSign=false;
		if(null==uid||"".equals(uid)){
			ReturnResult result=new ReturnResult();
			result.setState(-1,"参数为空");
			OutPutUtil.outPutStreamJson(response, result);
			return ;
		}
		try{
		 signinfo=signInService.get(uid);
		if(null==signinfo){
			//第一次打开请求
			ReturnResult result=new ReturnResult();
			result.setState(0,"success");
			Map<String, Object> map=new HashMap<String,Object>(1);
			map.put("day", 1);
			map.put("sign", false);
			result.setData(map);
			OutPutUtil.outPutStreamJson(response, result);
			return ;
		}
		}catch(Exception e){
			e.printStackTrace();ReturnResult result=new ReturnResult();
			result.setState(-1,"服务器繁忙");
			OutPutUtil.outPutStreamJson(response, result);
			return ;
		}
		//非第一次请求
		day=signinfo.getSigninCount();
		isSign=true;
		
		//看一下用户是否连续签到
		String beginDateStr=DateUtil.getDateCurrentTime(new Date(), "00:00:00");
		String preDateStr=DateUtil.getDay(beginDateStr, -1);
		Date preDateBegin=DateUtil.toDateFromStr(preDateStr+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
		//没有连续签到
		if(signinfo.getLastSignin().compareTo(preDateBegin)<0){			
			day=1;
			isSign=false;
		}
		
		//total=modifyservice.addPoint(uid,String.valueOf(0),"1");
		ReturnResult result=new ReturnResult();
		result.setState(0,"success");
		Map<String, Object> map=new HashMap<String,Object>(1);
		map.put("day",day);
		map.put("sign", isSign);
		map.put("total", total);
		result.setData(map);
		OutPutUtil.outPutStreamJson(response, result);
	}
	
	
	
}
