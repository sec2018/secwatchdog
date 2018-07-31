package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.UserProfileService;

@Controller
@RequestMapping("/userProfile")
public class userProfileController {
	@Autowired
	private UserProfileService userProfileService;
	
    @RequestMapping("userProfilePage")
	public String GoToUserProfilePage(@RequestParam(value="viewuser") String username, HttpServletRequest request,ModelMap model ) {
	HttpSession session=request.getSession();
	//session失效，退出登录页面
	if(session.getAttribute("currentUser")==null){;
		return "redirect:/login.jsp";
	}
	Managers manager= (Managers) session.getAttribute("currentUser");
	StringBuilder url = new StringBuilder("index/user_profile");
	JSONObject jsStr = null;
	Map<String,Object> data = new HashMap<String,Object>();
	
	data  = userProfileService.getUserProfile(username);
	jsStr = JSONObject.fromObject(data);
	model.addAttribute("model",jsStr.toString());	 
	return url.toString();
}
}
