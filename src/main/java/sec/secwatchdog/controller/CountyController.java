package sec.secwatchdog.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.CountyService;
import sec.secwatchdog.service.UserService;
@Controller
@RequestMapping("/county")
public class CountyController {
 
	@Resource
	private UserService userService;
	
	@Resource
	private CountyService countyService;
 /***
  * 
  * @param county 县名
  * @param city   市名
  * @param province  省名
  * @param request
  * @param model
  * @return
 * @throws Exception 
  */
	@RequestMapping("/county")
	public String GoToCountyPage(@RequestParam(value="county") String county,@RequestParam(value="city") String city,@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model) throws Exception {
		province = URLDecoder.decode(province,"UTF-8");
		city = URLDecoder.decode(city,"UTF-8");
		county = URLDecoder.decode(county,"UTF-8");
		
		HttpSession session=request.getSession();
		//session过期
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",manager);//data1用户信息

		url.append("page_county");//转到页面index/page_county.jsp
	 
		Map<String,Integer> countyIndexInfo = countyService.GetIndexLogoInfo(province, city,county);//该县的总体数据信息
		data.put("data2",countyIndexInfo);
		Map<String,Object> countyMap = countyService.GetCountyMap(province,city,county);//该县下各个流行乡的数据信息
		data.put("data3", countyMap);
		Map<String,Object> data4 = countyService.GetDistrictcode(province,city,county);//获得该县的区域编码
		data.put("data4", data4);
	 
		
		jsStr = JSONObject.fromObject(data);
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
}
