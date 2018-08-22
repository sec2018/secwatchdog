package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.HamletService;
import sec.secwatchdog.service.ManageService;
import sec.secwatchdog.service.UserProfileService;

@Controller
@RequestMapping("/userProfile")
public class UserProfileController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private ManageService manageService;
	@Autowired
	private HamletService hamletService;
	/**
	 * @throws Exception *
	 * 
	 * @param username 当前点击的管理员用户名
	 * @param request
	 * @param model
	 * @return
	 * @throws   
	 */
    @RequestMapping("userProfilePage")
	public String GoToUserProfilePage(@RequestParam(value="viewuser") String username, HttpServletRequest request,ModelMap model ) throws Exception    {
	HttpSession session=request.getSession();
	//session失效，退出登录页面
	if(session.getAttribute("currentUser")==null){;
		return "redirect:/login.jsp";
	}
	Managers manager= (Managers) session.getAttribute("currentUser");
	StringBuilder url = new StringBuilder("index/user_profile");//转到页面index/user_profile.jsp
	JSONObject jsStr = null;
	Map<String,Object> data = new HashMap<String,Object>();
	
		//获取被点击的管理员信息
    data  = userProfileService.getUserProfile(username);

	jsStr = JSONObject.fromObject(data);
	model.addAttribute("model",jsStr.toString());	 
	return url.toString();
}
    /***
     * @param username
     * @param request
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping("userProfileFarmPage")
	public String GoToUserProfileFarmPage(@RequestParam(value="viewuser") String username, HttpServletRequest request,ModelMap model ) throws Exception {
	HttpSession session=request.getSession();
	//session失效，退出登录页面
	if(session.getAttribute("currentUser")==null){;
		return "redirect:/login.jsp";
	}
	int startItem = 0;
	int pageSize = 8;
	Managers manager= (Managers) session.getAttribute("currentUser");
	StringBuilder url = new StringBuilder("index/user_profile_farm");//转到页面index/user_profile.jsp
	JSONObject jsStr = null;
	Map<String,Object> data = new HashMap<String,Object>();
	
	//获取被点击的管理员信息
	Map<String,Object> data1  = manageService.getManagerInfo(username);
	data.put("data1", data1);
	Map<String, Object> dogList = userProfileService.getFarmDogList(username, startItem,  pageSize);
	data.put("data2", dogList.get("data"));
	data.put("total", dogList.get("dogTotal"));
	data.put("data3", manager);
	Map<String,Object> data4 = hamletService.GetLevel6AdminDogNum(username);
	data.put("data4", data4);
	/*Map<String, Object> data4 = userProfileService.getFarmFeederDogList(username);
	data.put("data4", data4);*/
	
	jsStr = JSONObject.fromObject(data);
	model.addAttribute("model",jsStr.toString());	 
	return url.toString();
}
 /***
  *    
  * @param json
  * @param request
  * @return
 * @throws Exception 
  */
    @RequestMapping("userProfileFarmPageApi")
    @ResponseBody
	public String GoToUserProfileFarmPageApi(@RequestBody JSONObject json, HttpServletRequest request) throws Exception {
	HttpSession session=request.getSession();
	//session失效，退出登录页面
	if(session.getAttribute("currentUser")==null){;
		return null;
	}
	JSONObject jsStr = null;
	String username = json.getString("username"); 
	int startItem = json.getInt("startItem");
	int pageSize = json.getInt("pageSize");
	System.out.println(username);
	System.out.println(startItem);
	System.out.println(pageSize);
	Managers manager= (Managers) session.getAttribute("currentUser");

	Map<String,Object> data = new HashMap<String,Object>();
	

	Map<String, Object> dogList = userProfileService.getFarmDogList(username, startItem,  pageSize);
	data.put("data2", dogList.get("data"));
	data.put("total", dogList.get("dogTotal"));
//	data.put("data3", manager);
/*
		Map<String, Object> data4 = userProfileService.getFarmFeederDogList(username);
		data.put("data4", data4);*/

	jsStr = JSONObject.fromObject(data); 
	System.out.println(jsStr);
	return jsStr.toString();
}
    /***
     * 
     * @param json 包括重置密码，激活账户，冻结账户的必要参数
     * @param request
     * @return
     */
    @RequestMapping("userProfileApi")
    @ResponseBody
    public String UserProfileApi(@RequestBody JSONObject json, HttpServletRequest request ) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return null;
		}
		String clicktype = json.getString("clicktype");	 
		Managers user= (Managers) session.getAttribute("currentUser");
		String username = user.getUsername();
		String result = "";
	   //重置密码
		 if (clicktype.equals("rebackpwd"))
		{
			String rebackusername = json.getString("rebackusername");
			try {
				result = userProfileService.rebackPwd(username, rebackusername);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "重置失败!";
			}
			if (result.equals("11")) {
				result = "重置成功!";

			} else if (result.equals("00")) {
				result = "您只能重置下一级管理员密码!";
			} else {
				result = "重置失败!";
			}
		}
		//激活管理员
         else if (clicktype.equals("activeadmin"))
         {
             String activeusername = json.getString("activeusername");
             try {
 				result = userProfileService.activeUser(username, activeusername);
 			} catch (Exception e) {
				logger.error("【系统错误】",e);
 				result = "激活失败!";
 			}
 			if (result.equals("11")) {
 				result = "激活成功!";

 			} else if (result.equals("00")) {
 				result = "您只能激活下一级管理员!";
 			} else {
 				result = "激活失败!";
 			}
         }
		 //冻结管理员
         else if (clicktype.equals("freezeadmin"))
         {
        	 String freezeusername = json.getString("activeusername");

        	 try {
 		   	result = userProfileService.freezeUser(username, freezeusername);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
 				result = "冻结失败!";
 			}
 			if (result.equals("11")) {
 				result = "冻结成功!";

 			} else if (result.equals("00")) {
 				result = "您只能冻结下一级管理员!";
 			} else {
 				result = "冻结失败!";
 			}
         }
 	  
		return result.toString();
	}
}
