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

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.CityService;
import sec.secwatchdog.service.UserService;

@Controller
@RequestMapping("/city")
public class CityController {

	@Resource
	private CityService cityService;
/***
 * 
 * @param city 城市名
 * @param province 省名 
 * @param request
 * @param model
 * @return
 */
	@RequestMapping("/city")
	public String GoToCityPage(@RequestParam(value="city") String city,@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model)  throws Exception{
		province = URLDecoder.decode(province,"UTF-8");
		city = URLDecoder.decode(city,"UTF-8");
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",manager);//data1保存用户信息
		
			if(province.equals("建设兵团")) {//查看建设兵团的详情下某师
				url.append("page_division");//转到页面index/page_division.jsp
				Map<String,Integer> divisionIndexInfo = cityService.GetDivisionIndexLogo(province,city);//获得师德总体数据信息
				data.put("data2",divisionIndexInfo);
				Map<String,Object> armyCityMap = cityService.GetArmyCityMap(province,city);//获得该师下各个流行团的详细数据信息
				data.put("data3", armyCityMap);
				Map<String,Object> data4 = cityService.GetDistrictcode(province,city);//获得师的区域编码
				data.put("data4", data4);
			}else {
				url.append("page_city");//转到页面index/page_city.jsp

				Map<String,Integer> cityIndexInfo = cityService.GetIndexLogoInfo(province, city);//获得该市的总体数据信息
				data.put("data2",cityIndexInfo);
				Map<String,Object> cityMap = cityService.GetCityMap(province,city);//获得该市下各个流行县的详细数据信息
				data.put("data3", cityMap);
				Map<String,Object> data4 = cityService.GetDistrictcode(province,city);//获得该市的区域编码
				data.put("data4", data4);
			}
	
		jsStr = JSONObject.fromObject(data);
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	 
	}
}
