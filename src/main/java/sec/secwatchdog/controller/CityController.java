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
import sec.secwatchdog.service.CityService;
import sec.secwatchdog.service.UserService;

@Controller
@RequestMapping("/city")
public class CityController {

	@Resource
	private UserService userService;
	
	@Resource
	private CityService cityService;
/***
 * 
 * @param city ������
 * @param province ʡ��
 * @param request
 * @param model
 * @return
 */
	@RequestMapping("/city")
	public String GoToCityPage(@RequestParam(value="city") String city,@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model) {
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
		if(province.equals("�������")) {//�鿴������ŵ�����
			url.append("page_division");//ת��ҳ��index/page_division.jsp
			Map<String,Integer> divisionIndexInfo = cityService.GetDivisionIndexLogo(province,city);//���ʦ������������Ϣ
			data.put("data2",divisionIndexInfo);
			Map<String,Object> armyCityMap = cityService.GetArmyCityMap(province,city);//���ʦ�¸��������ŵ���ϸ������Ϣ
			data.put("data3", armyCityMap);
			Map<String,Object> data4 = cityService.GetDistrictcode(province,city);//���ʦ���������
			data.put("data4", data4);
		}else {//����ǽ�����ţ���ʡ
			url.append("page_city");//ת��ҳ��index/page_city.jsp

			Map<String,Integer> cityIndexInfo = cityService.GetIndexLogoInfo(province, city);//��ø��е�����������Ϣ
			data.put("data2",cityIndexInfo);
			Map<String,Object> cityMap = cityService.GetCityMap(province,city);//��ø����¸��������ص���ϸ������Ϣ
			data.put("data3", cityMap);
			Map<String,Object> data4 = cityService.GetDistrictcode(province,city);//��ø��е��������
			data.put("data4", data4);
		}
		jsStr = JSONObject.fromObject(data);//����תΪjson��ʽ
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	 
	}
}
