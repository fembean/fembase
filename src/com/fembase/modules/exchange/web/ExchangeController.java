/**
 */
package com.fembase.modules.exchange.web;

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
import com.fembase.common.utils.IpUtil;
import com.fembase.common.utils.StringUtils;
import com.fembase.common.web.BaseController;
import com.fembase.modules.exchange.entity.Exchange;
import com.fembase.modules.exchange.service.ExchangeService;

/**
 * 兑换Controller
 * @author dong
 * @version 2015-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/exchange")
public class ExchangeController extends BaseController {

	@Autowired
	private ExchangeService exchangeService;
	
	@ModelAttribute
	public Exchange get(@RequestParam(required=false) String id) {
		Exchange entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = exchangeService.get(id);
		}
		if (entity == null){
			entity = new Exchange();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Exchange exchange, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(IpUtil.getIP(request)+"==========>");
		return "modules/exchange/exchangeList";
	}

	@RequestMapping(value = "form")
	public String form(Exchange exchange, Model model) {
		model.addAttribute("exchange", exchange);
		return "modules/exchange/exchangeForm";
	}

	@RequestMapping(value = "save")
	public String save(Exchange exchange, Model model, RedirectAttributes redirectAttributes) {
		/*if (!beanValidator(model, exchange)){
			return form(exchange, model);
		}*/
		exchangeService.save(exchange);
		addMessage(redirectAttributes, "保存兑换成功");
		return "redirect:"+Global.getAdminPath()+"/exchange/exchange/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Exchange exchange, RedirectAttributes redirectAttributes) {
		exchangeService.delete(exchange);
		addMessage(redirectAttributes, "删除兑换成功");
		return "redirect:"+Global.getAdminPath()+"/exchange/exchange/?repage";
	}

}