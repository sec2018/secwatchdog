package sec.secwatchdog.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.UserService;
import sec.secwatchdog.service.VillageService;
@Controller
@RequestMapping("/village")
public class VillageController {
 
	@Resource
	private VillageService villageService;
 /***
  * 
  * @param village 乡名
  * @param county  县名
  * @param city   市名
  * @param province  市名
  * @param request
  * @param model
  * @return
 * @throws Exception 
  */
	@RequestMapping("/village")
	public String GoToCountyPage(@RequestParam(value="village") String village, @RequestParam(value="county") String county,@RequestParam(value="city") String city,@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model) throws Exception {
		province = URLDecoder.decode(province,"UTF-8");
		city = URLDecoder.decode(city,"UTF-8");
		county = URLDecoder.decode(county,"UTF-8");
		village = URLDecoder.decode(village,"UTF-8");
		
		HttpSession session=request.getSession();
		//session过期
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",manager);//data1保存用户信息

		url.append("page_village");//转到页面index/page_village.jsp
		Map<String,Integer> villageIndexInfo = villageService.GetIndexLogoInfo(province, city,county,village);//获得该乡的总体数据信息
		data.put("data2",villageIndexInfo);
		Map<String,Object> villageMap = villageService.GetVillageMap(province,city,county,village);//获得该乡下各村的数据信息
		data.put("data3", villageMap);
		Map<String,Object> data4 = villageService.GetDistrictcode(province,city,county,village);//获得该乡的区域编码
		data.put("data4", data4);
	
		
		jsStr = JSONObject.fromObject(data);
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	
	}
}
