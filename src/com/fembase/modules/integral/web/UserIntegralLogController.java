/**
 */
package com.fembase.modules.integral.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fembase.common.config.Global;
import com.fembase.common.persistence.Page;
import com.fembase.common.utils.StringUtils;
import com.fembase.common.web.BaseController;
import com.fembase.modules.integral.entity.UserIntegralLog;
import com.fembase.modules.integral.service.UserIntegralLogService;

/**
 * 用户积分记录Controller
 * @author dong
 * @version 2015-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/integral/userIntegralLog")
public class UserIntegralLogController extends BaseController {

	@Autowired
	private UserIntegralLogService userIntegralLogService;
	
	@ModelAttribute
	public UserIntegralLog get(@RequestParam(required=false) String id) {
		UserIntegralLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userIntegralLogService.get(id);
		}
		if (entity == null){
			entity = new UserIntegralLog();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(UserIntegralLog userIntegralLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserIntegralLog> page = userIntegralLogService.findPage(new Page<UserIntegralLog>(request, response), userIntegralLog); 
		model.addAttribute("page", page);
		return "modules/integral/userIntegralLogList";
	}

	@RequestMapping(value = "form")
	public String form(UserIntegralLog userIntegralLog, Model model) {
		model.addAttribute("userIntegralLog", userIntegralLog);
		return "modules/integral/userIntegralLogForm";
	}

	@RequestMapping(value = "save")
	public String save(UserIntegralLog userIntegralLog, Model model, RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, userIntegralLog)){
			return form(userIntegralLog, model);
		}*/
		userIntegralLogService.save(userIntegralLog);
		addMessage(redirectAttributes, "保存用户积分记录成功");
		return "redirect:"+Global.getAdminPath()+"/integral/userIntegralLog/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(UserIntegralLog userIntegralLog, RedirectAttributes redirectAttributes) {
		userIntegralLogService.delete(userIntegralLog);
		addMessage(redirectAttributes, "删除用户积分记录成功");
		return "redirect:"+Global.getAdminPath()+"/integral/userIntegralLog/?repage";
	}

}