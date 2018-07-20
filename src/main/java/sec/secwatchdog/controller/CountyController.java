package sec.secwatchdog.controller;

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
 * @param city ������
 * @param province ʡ��
 * @param request
 * @param model
 * @return
 */
	@RequestMapping("/county")
	public String GoToCountyPage(@RequestParam(value="county") String county,@RequestParam(value="city") String city,@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		//sessionʧЧ���˵���½ҳ��
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
			
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",manager);//data1�����û���Ϣ

		url.append("page_county");//ת��ҳ��index/page_county.jsp
		Map<String,Integer> countyIndexInfo = countyService.GetIndexLogoInfo(province, city,county);//��ø��ص�����������Ϣ
		data.put("data2",countyIndexInfo);
		Map<String,Object> countyMap = countyService.GetCountyMap(province,city,county);//��ø����¸������������ϸ������Ϣ
		data.put("data3", countyMap);
		Map<String,Object> data4 = countyService.GetDistrictcode(province,city,county);//��ø��ص��������
		data.put("data4", data4);
		
		jsStr = JSONObject.fromObject(data);//����תΪjson��ʽ
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
}
