package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.PersonalService;

@Controller
@RequestMapping("/personal")
public class PersonalController {
	@Autowired
	private PersonalService personalService;
	@RequestMapping("/pagePersonal")
	public String GoToPersonalPage(HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/page_personal");//转到页面index/page_personal.jsp
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",manager);//data1保存用户信息
		
		//待激活用户
		Map<String,Object> unActiveUsers = personalService.getUnActiveUsers(manager.getUsername());
		data.put("data2",unActiveUsers);
	 
		jsStr = JSONObject.fromObject(data);//数据转为json格式
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
	
	@RequestMapping("/pagePersonalApi")
	@ResponseBody
	public String indexApi(@RequestBody JSONObject senddata, HttpServletRequest request ) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";
		}
 	
		String clickType = senddata.getString("clicktype");
		String username = senddata.getString("username");
 
		Managers user= (Managers) session.getAttribute("currentUser");
		String jsStr = null;
		//确认激活
		if(clickType.equals("activeadmins")) {
			String activeNames = senddata.getString("activenames");
			String[] namearr = activeNames.split(",");
			String result = "";
            for (int i = 0; i < namearr.length; i++)
            {
                String temp = "";
                System.out.println(username+"-"+namearr[i]);
                try {
					temp = personalService.activeAdmin(username, namearr[i]);
				} catch (Exception e) {
					result += namearr[i] + "激活失败!";
				}
                if (temp.equals("11"))
                {
                	result += namearr[i] + "激活成功!";
                   
                }else if(temp.equals("00")) {
                	result += namearr[i] + "您不能激活同级或上级管理员账号 !";
                } else {
                	result += namearr[i] + "激活失败!";
                }
            }

            jsStr =  result;
			
		}
		//确认修改
		else if(clickType.equals("modifyself")) {
			   String managername = senddata.getString("managername");
			//   String managerarea = senddata.getString("managerarea");
			   String managerjob = senddata.getString("managerjob");
			   String manageridentity = senddata.getString("manageridentity");
			   String officecall = senddata.getString("officecall");
			   String managertel = senddata.getString("managertel");
			   String manageraddress = senddata.getString("manageraddress");
			   String email = senddata.getString("email");
			   String password = senddata.getString("password");
			   String result = "";
			try {
				result = personalService.modifyUser(username, managername, managerjob, manageridentity, officecall, managertel, manageraddress, email, password);
			} catch (Exception e) {
				result = "修改失败！";
			}       
			
			jsStr =  result;
		}		  
		return jsStr.toString();
	}
}
