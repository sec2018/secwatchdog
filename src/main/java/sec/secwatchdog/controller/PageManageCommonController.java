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
/***
 * 
 * @param districtcode
 * @param managername 从上一管理页进入所需参数
 * @param request
 * @param model
 * @return
 */
	@RequestMapping("/index")
	public String index(@RequestParam(value="districtcode", required = false) String districtcode,@RequestParam(value="managername", required = false) String managername, HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";
		}

		int startItem = 0;
		int pageSize = 8;
		Managers user= (Managers) session.getAttribute("currentUser");
		
		StringBuilder url = new StringBuilder("index/page_managecommon");//转到页面index/page_managecommon.jsp
		JSONObject jsStr = null;
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",user);//data1保存登录用户信息
		
		//从全国地图、村级地图进入或者通过上一级管理员页面进入管理员页面
		if(districtcode == null || districtcode.equals("0")) {
			//data4用来表示是否在页面上显示"添加管理员"的功能
			if(districtcode == null) {//通过上一级管理员页面进入管理员页面
				data.put("data4", false);
			}
			else {//从全国地图、村级地图进入
				data.put("data4", true);//控制'添加管理员'按钮是否显示
				managername = user.getUsername();
			}

			Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, startItem, pageSize);//当前管理员的下一级管理员信息
			data.put("data2",nextLevelManagersInfo.get("data"));
			data.put("total", nextLevelManagersInfo.get("totalNum"));
			data.put("data5", true);//表示从全国地图、村级地图进入或者通过上一级管理员页面进入管理员页面


		}
		//通过包括省级和省级以下，乡级和乡级以上地图页面进入管理页面
		else {
			data.put("data4", true);
			managername = user.getUsername();
			Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, districtcode, startItem, pageSize);//当前管理员的下一级管理员信息
			data.put("data2",nextLevelManagersInfo.get("data"));
			data.put("total", nextLevelManagersInfo.get("totalNum"));
			data.put("data5", false);//通过包括省级和省级以下，乡级和乡级以上地图页面进入管理页面
			data.put("districtInfo", nextLevelManagersInfo.get("districtInfo"));//地区编码

			
		}
		
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
		String districtcode = json.getString("districtcode");
		
		int startItem = json.getInt("startItem");
		int pageSize = json.getInt("pageSize");
		 
        Managers user= (Managers) session.getAttribute("currentUser");
		JSONObject jsStr = null;
		Map<String,Object> data = new HashMap<String,Object>();
		
		//从全国地图、村级地图进入或者通过上一级管理员页面进入管理员页面后，进行下一页操作
		if(districtcode.equals("0")) {
			Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, startItem, pageSize);//下一页管理员信息
			data.put("data2",nextLevelManagersInfo.get("data"));
			data.put("total", nextLevelManagersInfo.get("totalNum"));
		}
		//通过包括省级和省级以下，乡级和乡级以上地图页面进入管理页面后，进行下一页操作
		else {
			managername = user.getUsername();
			Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, districtcode, startItem, pageSize);//下一页管理员信息
			data.put("data2",nextLevelManagersInfo.get("data"));
			data.put("total", nextLevelManagersInfo.get("totalNum"));
		
		}
 
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
		String districtlevel = json.getString("districtlevel");
        int startItem = json.getInt("startItem");
		int pageSize = json.getInt("pageSize");
        Map<String, Object>  data = new HashMap<String, Object>();
        Map<String, Object>  data1 = null;
      
        if (!username.isEmpty())
        {
            if (clicktype.equals("onlynextonlinename"))
            {
              
                if (districtcode.equals("0"))
                {
                    //管理页面跳转或全国、村地图页面进入
                	//searchTotal = manageService.getSearchByAdminInfoTotal(username, managername,startItem,pageSize);
                	data1 = manageService.getSearchByAdminInfo(username, managername,startItem,pageSize);
                }
                else
                {
                    //其他地图页面跳转到管理员页面
//                    String privilegelevel = json.getString("privilegelevel");
                    data1 =  manageService.getSearchByAdminInfo(districtlevel, managername, districtcode,startItem,pageSize);
               }
            }
            else
            {
                
            }
        }
        data.put("data1", data1.get("data"));
        data.put("searchTotal", data1.get("totalNum"));
        JSONObject jsStr = JSONObject.fromObject(data);
		return jsStr.toString();
	}
}
