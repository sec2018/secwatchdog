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
import sec.secwatchdog.service.ManageService;
import sec.secwatchdog.service.NewUserService;

@Controller
@RequestMapping("/newUser")
public class NewUserController {
	
	@Autowired
	private ManageService manageService;
	@Autowired
	private NewUserService newUserService;
	@RequestMapping("/newUserPage")
	public String GoToNewUserPage(@RequestParam(value="managername") String managername, HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}	
		Managers user= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/newuser");//转到页面index/newuser.jsp
		JSONObject jsStr = null;
		Map<String,Object> data = new HashMap<String,Object>();
		switch(user.getPrivilegelevel()) {
		case 1:			
			data.put("data1",user);//data1保存登录用户信息	 
			Map<String, Object> manager = manageService.getManagerInfo(managername);//当前管理员信息
			data.put("data3", manager);
			Map<String, Object> provinceInfo = newUserService.getProvinces();
			data.put("data4", provinceInfo);
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		case 6:
			
			break;
			
		}
			
 
		jsStr = JSONObject.fromObject(data);//数据转为json格式
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
	@RequestMapping("/cteateNewUserApi")
	@ResponseBody
	public String indexApi(@RequestBody JSONObject senddata, HttpServletRequest request ) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";			
		}

		Managers user= (Managers) session.getAttribute("currentUser");
		String jsStr = null;
		
		String clickType = senddata.getString("clicktype");
		int privilegelevel = senddata.getInt("privilegelevel");
		String managername = senddata.getString("managername");
		String address = senddata.getString("address");
		String area = senddata.getString("area");
		String identity = senddata.getString("identity");
		String officecall = senddata.getString("officecall");
		String tel = senddata.getString("tel");
		String username = senddata.getString("username");
		String password = senddata.getString("password");
		String addtype = senddata.getString("addtype");
		String result = "";
		try {
			 result = newUserService.addUser(addtype, privilegelevel, username, managername, address, identity, area, officecall, tel, password);
		} catch (Exception e) {
			//e.printStackTrace();
			result = "添加用户失败！";
		}
	 
		jsStr = result;
		return jsStr.toString();
	}
}
