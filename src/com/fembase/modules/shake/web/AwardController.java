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
import com.fembase.modules.shake.entity.Award;
import com.fembase.modules.shake.service.AwardService;

/**
 * 奖品Controller
 * @author dong
 * @version 2015-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/shake/award")
public class AwardController extends BaseController {

	@Autowired
	private AwardService awardService;
	
	@ModelAttribute
	public Award get(@RequestParam(required=false) String id) {
		Award entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = awardService.get(id);
		}
		if (entity == null){
			entity = new Award();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Award award, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Award> page = awardService.findPage(new Page<Award>(request, response), award); 
		model.addAttribute("page", page);
		return "modules/shake/awardList";
	}

	@RequestMapping(value = "form")
	public String form(Award award, Model model) {
		model.addAttribute("award", award);
		return "modules/shake/awardForm";
	}

	@RequestMapping(value = "save")
	public String save(Award award, Model model, RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, award)){
			return form(award, model);
		}*/
		awardService.save(award);
		addMessage(redirectAttributes, "保存奖品成功");
		return "redirect:"+Global.getAdminPath()+"/shake/award/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Award award, RedirectAttributes redirectAttributes) {
		awardService.delete(award);
		addMessage(redirectAttributes, "删除奖品成功");
		return "redirect:"+Global.getAdminPath()+"/shake/award/?repage";
	}

}