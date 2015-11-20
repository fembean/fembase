package com.fembase.modules.sts.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fembase.common.log.YoumiLog;
import com.fembase.common.utils.IpUtil;
import com.fembase.common.utils.StringUtils;
import com.fembase.common.web.BaseController;
import com.fembase.modules.sts.utils.AdxmiSign;
import com.fembase.modules.user.entity.User;
import com.fembase.modules.user.service.UserService;

/**
 * 有米服务器回调接口
 * @author dong
 * @version 2015-07-20
 */
@Controller
public class YoumiController extends BaseController {

	@Autowired
	private UserService userService;
	
	private List<String> youmiIpList;
	
	private static final String YOUMI_TOKEN = "378fc87242ec83cf";

	@RequestMapping(value = "${adminPath}/youmi/{pkg}")
	public void form(User user, Model model ,@PathVariable("pkg") String pkg,HttpServletRequest request,
			HttpServletResponse resp) throws IOException {
		
		String url= StringUtils.getUrl(request);
		
		String ip=IpUtil.getIP(request);
		YoumiLog.log("pkg="+pkg+";url="+url+"ip="+ip);
		
		if(youmiIpList==null){
			youmiIpList=new ArrayList<String>();
			youmiIpList.add("58.63.244.57");
			youmiIpList.add("58.63.244.58");
			youmiIpList.add("54.223.128.154");
			youmiIpList.add("54.223.174.146");
			youmiIpList.add("58.63.244.232");
			youmiIpList.add("58.63.244.233");
			youmiIpList.add("52.68.70.234");
			youmiIpList.add("52.68.12.214");
		}
		
		if(!youmiIpList.contains(ip)){
			sendResult(resp, "0");
			return;
		}
		
		try {
			if(!AdxmiSign.checkUrlSignature(url, YOUMI_TOKEN)){
				YoumiLog.log("checkUrlSignature return false");
				return;
			}
			String uid=request.getParameter("user");
			String point=request.getParameter("points");
			String storeid=request.getParameter("storeid");
			String order=request.getParameter("order");
			if(uid.trim().equals("")||!StringUtils.isNumber(point)){
				YoumiLog.log("paramters are wrong");
				sendResult(resp, "0");
				return;
			}
			
			//TokenInfo tokenifo = MemcachedManager.instance.getOpenUserInfo(uid);
			/*if(tokenifo == null){
				sendResult(resp, "404");
				return;
			}*/
			/*String mc_orderid = MemcachedManager.instance.getYoumiOrderId(order);
			if(mc_orderid != null && mc_orderid.equals(order)){
				log.info("orderid="+order+" exist");
				sendResult(resp, "0");
				return;
			}*/
			
			Integer amount= Integer.valueOf(point);
			
			YoumiLog.log("uid="+uid+"&point="+point+"&orderid="+order+"&"+"&pkg="+pkg+"&ip="+ip);
			
			String detail = "有米任務_"+order;
			
			//JpointManagerRemote.instance.increaseJpoint(tokenifo.getJodoUid(), amount, false, new JpointOpReason(JpointOpCode.INC_JPOINT_FROM_THIRD_MEDIA, detail));
			
			//MemcachedManager.instance.setYoumiOrderId(order);
			
			sendResult(resp, "1");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			sendResult(resp, "404");
			//ex.printStackTrace();
		}
	}

	private void sendResult(HttpServletResponse resp, String resultCode) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(resultCode);
        resp.getWriter().close();
    }

}