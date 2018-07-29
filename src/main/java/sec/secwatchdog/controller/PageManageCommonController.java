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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.ManageService;

@Controller
@RequestMapping("/pageManageCommon")
public class PageManageCommonController {
	
	@Autowired
	private ManageService manageService;
	@RequestMapping("/index")
	public String index(@RequestParam(value="districtcode") String districode,@RequestParam(value="managername") String managername, HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";
		}
		int startItem = 0;
		int pageSize = 8;
		Managers user= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",user);//data1保存登录用户信息
		url.append("page_managecommon1");//转到页面index/page_managecommon1.jsp
		int totalNum = manageService.getNextLevelAdminInfoTotalNum(managername);
		data.put("total", totalNum);
		Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, startItem, pageSize);//当前管理员的下一级管理员信息
		data.put("data2",nextLevelManagersInfo);
		Map<String, Object> manager = manageService.getManagerInfo(managername);//当前管理员信息
		data.put("data3", manager);
		
		jsStr = JSONObject.fromObject(data);//数据转为json格式
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
	
	@RequestMapping("/indexApi")
	@ResponseBody
	public String indexApi(@RequestBody JSONObject json, HttpServletRequest request ) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";
		}
		String managername = json.getString("managername");
		
		int startItem = json.getInt("startItem");
		int pageSize = json.getInt("pageSize");
		 
		Managers user= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",user);//data1保存登录用户信息
		url.append("page_managecommon1");//转到页面index/page_managecommon1.jsp
		int totalNum = manageService.getNextLevelAdminInfoTotalNum(managername);
		data.put("total", totalNum);
		Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, startItem, pageSize);//当前管理员的下一级管理员信息
		data.put("data2",nextLevelManagersInfo);
		Map<String, Object> manager = manageService.getManagerInfo(managername);//当前管理员信息
		data.put("data3", manager);
		
		jsStr = JSONObject.fromObject(data);//数据转为json格式
		  
		return jsStr.toString();
	}
	
	@RequestMapping(value="/searchManagerApi", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String SearchManagerApi(@RequestBody(required=false) JSONObject json,HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		String username =json.getString("username");
        String managername = json.getString("managername");
        String clicktype = json.getString("clicktype");
        String districtcode = json.getString("districtcode");
        int startItem = json.getInt("startItem");
		int pageSize = json.getInt("pageSize");
        Map<String, Object>  data = new HashMap<String, Object>();
        Map<String, Object>  data1 = null;
        int searchTotal = 0;
        if (!username.isEmpty())
        {
            if (clicktype.equals("onlynextonlinename"))
            {
              
                if (!districtcode.isEmpty() || districtcode == "0")
                {
                    //管理页面跳转
                	searchTotal = manageService.getSearchByAdminInfoTotal(username, managername,startItem,pageSize);
                	data1 = manageService.getSearchByAdminInfo(username, managername,startItem,pageSize);
                }
                else
                {
                    //地图跳转
                    String privilegelevel = json.getString("privilegelevel");
                    data =  manageService.getSearchByAdminInfo(privilegelevel, managername, districtcode);
                }
            }
            else
            {
                
            }
        }
        data.put("data1", data1);
        data.put("searchTotal", searchTotal);
        JSONObject jsStr = JSONObject.fromObject(data);
		return jsStr.toString();
	}
}
