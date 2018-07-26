package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.StringUtil;

import net.sf.json.JSONObject;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.PageBean;
import sec.secwatchdog.service.HamletService;
import sec.secwatchdog.service.UserService;
import sec.secwatchdog.util.PageUtil;

@Controller
@RequestMapping("/hamlet")
public class HamletController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private HamletService hamletService;
	@Resource
	private SheepdogsDao sheepdogsDao;
	
	@RequestMapping(value="/hamletapi", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String GoToHamlet(@RequestParam(value="page",required=false)String page,@RequestBody(required=false) JSONObject json,HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}		
		String province,city, county,village,hamlet;
		if(StringUtil.isEmpty(page)){
			page="1";
		}
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
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1", resultUser);
//		Map<String,Object> Getuser_page_farmDogList = new HashMap<String,Object>();
//		Getuser_page_farmDogList = hamletService.Getuser_page_farmDogList(pageBean,resultUser.getUsername());
//		data.put("data2", Getuser_page_farmDogList);
		Map<String,Object> gethamletmap = new HashMap<String,Object>();
	 
	    gethamletmap = hamletService.GetHamletMap(province,city,county,village,hamlet,request);
		data.put("data3", gethamletmap);
		Map<String,String> data4 = new HashMap<String,String>();
		data4.put("provincename", hamletService.GovToEchartsAreaName(province));
		data4.put("cityname", hamletService.GovToEchartsAreaName(city));
		data4.put("countyname", hamletService.GovToEchartsAreaName(county));
		data4.put("villagename", hamletService.GovToEchartsAreaName(village));
		data4.put("hamletname", hamletService.GovToEchartsAreaName(hamlet));
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
		JSONObject jsStr = JSONObject.fromObject(data);
		return jsStr.toString();
	}
	
	@RequestMapping(value="/hamletpage", produces="text/html;charset=UTF-8")
	public String GoToHamletPage(@RequestParam(value="page",required=false)String page,HttpServletRequest request) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null || session.getAttribute("hamletcode") == null){;
			return "redirect:/login.jsp";
		}
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		Managers resultUser= (Managers) session.getAttribute("currentUser");
		PageBean pageBean=new PageBean(Integer.parseInt(page),6);
		Map<String,Object> combineneckletandfeederdoglist = new HashMap<String,Object>();
		combineneckletandfeederdoglist = hamletService.CombineNeckletAndFeederDogList(pageBean,session.getAttribute("hamletcode").toString());
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data8", combineneckletandfeederdoglist);
		int total=hamletService.CombineNeckletAndFeederDogTotal(session.getAttribute("hamletcode").toString());
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/hamlet/hamletpage.do", total, Integer.parseInt(page), 6);
		data.put("pageCode", pageCode);
		JSONObject jsStr = JSONObject.fromObject(data);
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
