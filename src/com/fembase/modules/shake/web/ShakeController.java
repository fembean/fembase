package com.fembase.modules.shake.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fembase.common.utils.OutPutUtil;
import com.fembase.common.utils.ReturnResult;
import com.fembase.common.web.BaseController;
import com.fembase.modules.shake.entity.Award;
import com.fembase.modules.shake.entity.Shake;
import com.fembase.modules.shake.service.ShakeService;

/**
 * 摇一摇模块
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/shake")
public class ShakeController extends BaseController{

	@Autowired 
	private ShakeService shakeService;
	
	@RequestMapping(value = "play")
	public void list(Shake shake, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		shake.setUid("666666");
		shake.setCost(100);
		
		Award award = shakeService.play(shake);
		
		ReturnResult result1=new ReturnResult();
		result1.setState(0,"success");
		result1.setData(award);		
		OutPutUtil.outPutStreamJson(response, result1);
		
		/*return null;*/
	}
}
