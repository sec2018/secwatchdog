package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.ProvinceService;
import sec.secwatchdog.service.UserService;

@Controller
@RequestMapping("/province")
public class ProvinceController {

	@Resource
	private UserService userService;
	
	@Resource
	private ProvinceService provinceService;
	/***
	 * 
	 * @param province ���Ż���ʡ��
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/province")
	public String GoToProvincePage(@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model) {
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
			url.append("page_corps");//ת��ҳ��index/page_corps.jsp
			Map<String,Integer> armyIndexInfo = provinceService.GetArmyIndexLogo(province);//��ý�����ŵ�����������Ϣ
			data.put("data2",armyIndexInfo);
			Map<String,Object> armyProvinceMap = provinceService.GetArmyProvinceMap(province);//��ý�������¸�������ʦ����ϸ������Ϣ
			data.put("data3", armyProvinceMap);
			Map<String,Object> data4 = provinceService.GetDistrictcode(province);//��ý�����ŵ��������
			data.put("data4", data4);
		}else {//����ǽ�����ţ���ʡ
			url.append("page_province");//ת��ҳ��index/page_province.jsp
			Map<String,Integer> provinceIndexInfo = provinceService.GetIndexLogoInfo(province);//��ø�ʡ������������Ϣ
			data.put("data2",provinceIndexInfo);
			Map<String,Object> ProvinceMap = provinceService.GetProvinceMap(province);//��ø��¸��������е���ϸ������Ϣ
			data.put("data3", ProvinceMap);
			Map<String,Object> data4 = provinceService.GetDistrictcode(province);//��ø�ʡ���������
			data.put("data4", data4);
		}
		jsStr = JSONObject.fromObject(data);//����תΪjson��ʽ
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
}
