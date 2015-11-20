/**
 */
package com.fembase.modules.shake.web;

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
import com.fembase.modules.shake.entity.UserShakeLog;
import com.fembase.modules.shake.service.UserShakeLogService;

/**
 * 用户摇奖记录Controller
 * @author dong
 * @version 2015-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/shake/userShakeLog")
public class UserShakeLogController extends BaseController {

	@Autowired
	private UserShakeLogService userShakeLogService;
	
	@ModelAttribute
	public UserShakeLog get(@RequestParam(required=false) String id) {
		UserShakeLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userShakeLogService.get(id);
		}
		if (entity == null){
			entity = new UserShakeLog();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(UserShakeLog userShakeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserShakeLog> page = userShakeLogService.findPage(new Page<UserShakeLog>(request, response), userShakeLog); 
		model.addAttribute("page", page);
		return "modules/shake/userShakeLogList";
	}

	@RequestMapping(value = "form")
	public String form(UserShakeLog userShakeLog, Model model) {
		model.addAttribute("userShakeLog", userShakeLog);
		return "modules/shake/userShakeLogForm";
	}

	@RequestMapping(value = "save")
	public String save(UserShakeLog userShakeLog, Model model, RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, userShakeLog)){
			return form(userShakeLog, model);
		}*/
		userShakeLogService.save(userShakeLog);
		addMessage(redirectAttributes, "保存用户摇奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/shake/userShakeLog/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(UserShakeLog userShakeLog, RedirectAttributes redirectAttributes) {
		userShakeLogService.delete(userShakeLog);
		addMessage(redirectAttributes, "删除用户摇奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/shake/userShakeLog/?repage";
	}

}