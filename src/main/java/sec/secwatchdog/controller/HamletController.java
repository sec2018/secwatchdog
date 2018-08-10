package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
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
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.PageBean;
import sec.secwatchdog.service.HamletService;
import sec.secwatchdog.service.UserService;
import sec.secwatchdog.util.NameConversionUtil;
import sec.secwatchdog.util.PageUtil;

@Controller
@RequestMapping("/hamlet")
public class HamletController {
 
	@Resource
	private HamletService hamletService;
	@Resource
	private SheepdogsDao sheepdogsDao;
	@Autowired
	private NameConversionUtil nameConversionUtil;
	
	@RequestMapping(value="/hamletapi", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String GoToHamlet(@RequestBody(required=false) JSONObject json,HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return null;
		}		
		String province,city, county,village,hamlet;
		/*if(StringUtil.isEmpty(page)){
			page="1";
		}*/
		
		String page = "1";
		Map<String, Object> data = new HashMap<>();
		try {
			PageBean pageBean=new PageBean(Integer.parseInt(page),6);
			Managers resultUser= (Managers) session.getAttribute("currentUser");
			if(json.getString("hamlet").equals("null")) {	
				province = resultUser.getProvince();
				city = resultUser.getCity();
				county = resultUser.getCounty();
				village = resultUser.getVillage();
				hamlet = resultUser.getHamlet();	
			}else {
				province = json.getString("province");
				city = json.getString("city");
				county = json.getString("county");
				village = json.getString("village");
				hamlet = json.getString("hamlet");
			}
			data = new HashMap<String,Object>();
			data.put("data1", resultUser);
//		Map<String,Object> Getuser_page_farmDogList = new HashMap<String,Object>();
//		Getuser_page_farmDogList = hamletService.Getuser_page_farmDogList(pageBean,resultUser.getUsername());
//		data.put("data2", Getuser_page_farmDogList);
			Map<String,Object> gethamletmap = new HashMap<String,Object>();
 
			gethamletmap = hamletService.GetHamletMap(province,city,county,village,hamlet,request);
			data.put("data3", gethamletmap);
			Map<String,String> data4 = new HashMap<String,String>();

			data4.put("provincename", nameConversionUtil.GovToEchartsAreaName(province));
			data4.put("cityname", nameConversionUtil.GovToEchartsAreaName(city));
			data4.put("countyname", nameConversionUtil.GovToEchartsAreaName(county));
			data4.put("villagename", nameConversionUtil.GovToEchartsAreaName(village));
			data4.put("hamletname", nameConversionUtil.GovToEchartsAreaName(hamlet));
			data.put("data4", data4);
//		Map<String,Object> getupuser_page_farmDogFeederList = new HashMap<String,Object>();
//		getupuser_page_farmDogFeederList = hamletService.Getupuser_page_farmDogFeederList(resultUser.getProvince(),resultUser.getCity(),resultUser.getCounty(),resultUser.getVillage(),resultUser.getHamlet());
//		data.put("data5", getupuser_page_farmDogFeederList);
			Map<String,Object> getHamletFeederMap = new HashMap<String,Object>();
			getHamletFeederMap = hamletService.GetHamletFeederMap(province,city,county,village,hamlet);
			data.put("data6", getHamletFeederMap);
			Map<String,Object> getLevel6AdminDogNum = new HashMap<String,Object>();
			getLevel6AdminDogNum = hamletService.GetLevel6AdminDogNum(resultUser.getUsername());
			data.put("data7", getLevel6AdminDogNum);
			
			Map<String,Object> combineneckletandfeederdoglist = new HashMap<String,Object>();

			combineneckletandfeederdoglist = hamletService.CombineNeckletAndFeederDogList(pageBean,session.getAttribute("hamletcode").toString());
			data.put("data8", combineneckletandfeederdoglist);
			
			int total=hamletService.CombineNeckletAndFeederDogTotal(session.getAttribute("hamletcode").toString());
			String pageCode=PageUtil.getPagation(request.getContextPath()+"/hamlet/hamletpage.do", total, Integer.parseInt(page), 6);
			data.put("pageCode", pageCode);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsStr = JSONObject.fromObject(data);
		return jsStr.toString();
	}
	
	@RequestMapping(value="/hamletpage", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String GoToHamletPage(@RequestBody String page,HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null || session.getAttribute("hamletcode") == null){;
			return null;
		}
		Map<String, Object> data = new HashMap<>();
		try {
			page = page.split("=")[1];
			PageBean pageBean=new PageBean(Integer.parseInt(page),6);
			Map<String,Object> combineneckletandfeederdoglist = new HashMap<String,Object>();
			combineneckletandfeederdoglist = hamletService.CombineNeckletAndFeederDogList(pageBean,session.getAttribute("hamletcode").toString());
			data = new HashMap<String,Object>();
			data.put("data8", combineneckletandfeederdoglist);
			int total=hamletService.CombineNeckletAndFeederDogTotal(session.getAttribute("hamletcode").toString());
			String pageCode=PageUtil.getPagation(request.getContextPath()+"/hamlet/hamletpage.do", total, Integer.parseInt(page), 6);
			data.put("pageCode", pageCode);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsStr = JSONObject.fromObject(data);
		return jsStr.toString();

	}
	
	@RequestMapping(value="/CombineNeckletAndFeederDog", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String getCombineNeckletAndFeederDogByNeckletId(@RequestBody String neckletId,HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null || session.getAttribute("hamletcode") == null){;
			return null;
		}
		
		neckletId = neckletId.split("=")[1];
	
		Map<String,Object> data = new HashMap<String,Object>();
		try {
			data = hamletService.getCombineNeckletAndFeederDogByNeckletId(neckletId,session.getAttribute("hamletcode").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsStr = JSONObject.fromObject(data);
		System.out.println(jsStr);
		return jsStr.toString();
	}
	
	@RequestMapping("/hamlet")
	public String GoTohamletPage(@RequestParam(value="hamlet") String hamlet, @RequestParam(value="village") String village,@RequestParam(value="county") String county,@RequestParam(value="city") String city,@RequestParam(value="province") String province,HttpServletRequest request,ModelMap model) {
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		model.addAttribute("provincename", province);
		model.addAttribute("cityname", city);
		model.addAttribute("countyname", county);
		model.addAttribute("villagename", village);
		model.addAttribute("hamletname", hamlet);
		StringBuilder url = new StringBuilder("index/page_hamlet");
		return url.toString();
	 
	}
}
