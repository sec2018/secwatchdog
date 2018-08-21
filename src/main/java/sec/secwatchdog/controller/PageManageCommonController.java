package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sec.secwatchdog.service.UserProfileService;

@Controller
@RequestMapping("/pageManageCommon")
public class PageManageCommonController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ManageService manageService;
	@Autowired
	private UserProfileService userProfileService;
/***
 * 
 * @param districtcode
 * @param managername 从上一管理页进入所需参数
 * @param request
 * @param model
 * @return
 * @throws Exception 
 */
	@RequestMapping("/index")
	public String index(@RequestParam(value="districtcode", required = false) String districtcode,@RequestParam(value="managername", required = false) String managername, HttpServletRequest request,ModelMap model) throws Exception {
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
			Map<String, Object> manager = manageService.getManagerInfo(managername);//当前管理员信息
			data.put("data3", manager);
			data.put("total", nextLevelManagersInfo.get("totalNum"));
			data.put("data5", true);//表示从全国地图、村级地图进入或者通过上一级管理员页面进入管理员页面
			data.put("districtlevel",(Integer.parseInt(manager.get("privilegelevel").toString())-2)+"");

		}
		//通过包括省级和省级以下，乡级和乡级以上地图页面进入管理页面
		else {
		 
			data.put("data4", true);
			managername = user.getUsername();
			Map<String,Object> nextLevelManagersInfo = manageService.getNextLevelAdminInfo(managername, districtcode, startItem, pageSize);//当前管理员的下一级管理员信息
			data.put("data2",nextLevelManagersInfo.get("data"));
			Map<String, Object> manager = manageService.getManagerInfo(managername);//当前管理员信息
			data.put("data3", manager);
			data.put("total", nextLevelManagersInfo.get("totalNum"));
			data.put("data5", false);//通过包括省级和省级以下，乡级和乡级以上地图页面进入管理页面
			Map<String, Object> districtinfo = (Map<String, Object>) nextLevelManagersInfo.get("districtInfo");
			data.put("districtInfo", districtinfo);//地区编码
			data.put("districtlevel",districtinfo.get("districtlevel"));		 
			
		}
		
		jsStr = JSONObject.fromObject(data);//数据转为json格式
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
	
	/***
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/hamletManager")
	public String hamletManager(HttpServletRequest request,ModelMap model) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";
		}

		int startItem = 0;
		int pageSize = 8;
		Managers user= (Managers) session.getAttribute("currentUser");
		
		StringBuilder url = new StringBuilder("index/page_managecommon6");//转到页面index/page_managecommon6.jsp
		JSONObject jsStr = null;
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("data1",user);//data1保存登录用户信息

		Map<String, Object> dogList = userProfileService.getFarmDogList(user.getUsername(), startItem,  pageSize);
		data.put("data2", dogList.get("data"));
		data.put("total", dogList.get("dogTotal"));
		Map<String, Object> data4 = manageService.getVillageManagersList(String.valueOf(user.getDistrictcode()));
		data.put("data4", data4);
		Map<String, Object> data7 = manageService.getNecksList(user.getUsername());
		data.put("data7", data7);
		Map<String, Object> data9 = manageService.getFeedersList(user.getUsername());
		data.put("data9", data9);
		
		
		jsStr = JSONObject.fromObject(data);//数据转为json格式
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
	
	@RequestMapping("/indexApi")
	@ResponseBody
	public String indexApi(@RequestBody JSONObject json, HttpServletRequest request ) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return null;
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
	
	@RequestMapping("/hamletManagerApi")
	@ResponseBody
	public String hamletManagerApi(@RequestBody JSONObject json, HttpServletRequest request ) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return null;
		}
		 
		  
		JSONObject jsStr = null;
		Map<String,Object> data = new HashMap<String,Object>();
		
	
		int startItem = json.getInt("startItem");
		int pageSize = json.getInt("pageSize");
		Managers user= (Managers) session.getAttribute("currentUser");

		data.put("data1",user);//data1保存登录用户信息
		Map<String, Object> dogList = userProfileService.getFarmDogList(user.getUsername(), startItem,  pageSize);
		data.put("data2", dogList.get("data"));
		data.put("total", dogList.get("dogTotal"));

		jsStr = JSONObject.fromObject(data);//数据转为json格式
		  
		return jsStr.toString();
	}
	
	@RequestMapping(value="/searchManagerApi", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String SearchManagerApi(@RequestBody(required=false) JSONObject json,HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return null;
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
		            data1 = manageService.getSearchByAdminInfo(username, managername,startItem,pageSize);
		        }
		        else
		        {
		            //其他地图页面跳转到管理员页面
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
	
	@RequestMapping("/pagedog")
	public String PageDog(@RequestParam(value="dogid") int dogId,HttpServletRequest request,ModelMap model) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return "redirect:/login.jsp";
		}

		Managers user= (Managers) session.getAttribute("currentUser");
		
		StringBuilder url = new StringBuilder("index/page_dog");//转到页面index/page_dog.jsp
		JSONObject jsStr = null;
		Map<String,Object> data = new HashMap<String,Object>();
		
		data.put("data6",user);//data6保存登录用户信息
		Map<String,Object> data1 = manageService.getDogInfo(dogId);
		data.put("data1", data1);
		Map<String,Object> data2 = manageService.getDogNeckletInfo(dogId);
		data.put("data2", data2);
		Map<String,Object> data9 = manageService.getDogFeederInfo(dogId);
		data.put("data9", data9);
		Map<String,Object> data3 = manageService.getDogOwnerInfo(dogId);
		data.put("data3", data3);
		Map<String,Object> data5 = null;
		if(user.getPrivilegelevel()==6) {
			data5 = manageService.getVillageManagersList(String.valueOf(user.getDistrictcode()));
		}
		data.put("data5", data5);
		Map<String, Object> data7 = manageService.getNecksList(user.getUsername());
		data.put("data7", data7);
		Map<String, Object> data8 = manageService.getFeedersList(user.getUsername());
		data.put("data8", data8);

		jsStr = JSONObject.fromObject(data);//数据转为json格式
		model.addAttribute("model",jsStr.toString());	 
		return url.toString();
	}
	
    @RequestMapping("pagedogapi")
    @ResponseBody
    public String PagedogApi(@RequestBody JSONObject json, HttpServletRequest request ) {
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){
			return null;
		}
		String clicktype = json.getString("clicktype");	 
		Managers user= (Managers) session.getAttribute("currentUser");
		String result = "";
	
		 if (clicktype.equals("owneradd"))
		{
	        String ownername = json.getString("ownername");		       
	        String owneridentity = json.getString("owneridentity");
	        String ownersex = json.getString("ownersex");
	        String ownerhamlet = json.getString("ownerhamlet");
	        String ownerhamletcode = json.getString("ownerhamletcode");
	        int ownerage = json.getInt("ownerage");
	        String ownerjob = json.getString("ownerjob");
	        String homeaddress = json.getString("homeaddress");
	        String telphone = json.getString("telphone");
		      
			try {
				 result = manageService.addOwer(ownername, owneridentity, ownersex, ownerhamletcode, ownerage, ownerjob, homeaddress, telphone);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "添加主人失败!";
			}
			 
		}

         else if (clicktype.equals("neckletadd"))
         {
        	 String neckletid = json.getString("neckletid");
        	 int medtotal = json.getInt("medtotal");
        	 String category = json.getString("category");
        	 String username = json.getString("username");
        	 try {
				 result = manageService.addNecklet(neckletid, medtotal, category, username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "添加项圈失败!";
			}
         }

         else if (clicktype.equals("feederadd"))
         {  
      
        	 String apparatusid = json.getString("feederid");
        	 int medtotal = json.getInt("medtotal");
        	 String category = json.getString("category");
        	 String username = json.getString("username");
        	 try {
				 result = manageService.addFeeder(apparatusid, medtotal, category, username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "添加喂食器失败!";
			}
        	 
         }else if(clicktype.equals("bindfeeder")) {
                      
             String username = json.getString("username");
             String dogname = json.getString("dogname");
             String dogsex = json.getString("dogsex");
             String dogbelonghamlet = json.getString("dogbelonghamlet");
             String ownerhamletcode = json.getString("ownerhamletcode");
             String dogownerid = json.getString("dogownerid");
             String dogweight = json.getString("dogweight");
             String dogcolor = json.getString("dogcolor");
             String dogage = json.getString("dogage");
             String dogfeederid = json.getString("dogfeederid");
             try {
				 result = manageService.bindFeeder(username, dogname, dogsex, dogbelonghamlet, ownerhamletcode, dogownerid, dogweight, dogcolor, dogage, dogfeederid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "绑定喂食器失败!";
			}
         }else if(clicktype.equals("bindnecklet")) {
       
        	 String username = json.getString("username");
             String dogname = json.getString("dogname");
             String dogsex = json.getString("dogsex");
             String dogbelonghamlet = json.getString("dogbelonghamlet");
             String ownerhamletcode = json.getString("ownerhamletcode");
             String dogownerid = json.getString("dogownerid");
             String dogweight = json.getString("dogweight");
             String dogcolor = json.getString("dogcolor");
             String dogage = json.getString("dogage");
             String dogneckletid = json.getString("dogneckletid");
             try {
				 result = manageService.bindNecklet(username, dogname, dogsex, dogbelonghamlet, ownerhamletcode, dogownerid, dogweight, dogcolor, dogage, dogneckletid);
			} catch (Exception e) {
				logger.error("【系统错误】",e);
				result = "绑定项圈失败!";
			}
         }else if(clicktype.equals("dogadd")) {
   
             String username = json.getString("username");
             String dogname = json.getString("dogname");
             String dogsex = json.getString("dogsex");
             String dogbelonghamlet = json.getString("dogbelonghamlet");
             String ownerhamletcode = json.getString("ownerhamletcode");
             String dogownerid = json.getString("dogownerid");
             String dogweight = json.getString("dogweight");
             String dogcolor = json.getString("dogcolor");
             String dogage = json.getString("dogage");
             try {
				 result = manageService.addDog(username, dogname, dogsex, dogbelonghamlet, ownerhamletcode, dogownerid, dogweight, dogcolor, dogage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "添加牧犬失败!";
			}
         }else if(clicktype.equals("neckletmodify")) {
   
             String neckletid = json.getString("neckletid");
             String power = json.getString("power");
             String medtotal = json.getString("medtotal");
             String medleft = json.getString("medleft");
             String areacycle = json.getString("areacycle");
             String exhibitcycle = json.getString("exhibitcycle");
             String firstmedtime = json.getString("firstmedtime");
         
             try {
				 result = manageService.modifyNecklet(neckletid, power, medtotal, medleft, areacycle, exhibitcycle, firstmedtime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "修好项圈信息失败!";
			}
         }else if(clicktype.equals("feedermodify")) {
   
        	 String feederid = json.getString("feederid");
             String power = json.getString("power");
             String medtotal = json.getString("medtotal");
             String medleft = json.getString("medleft");
             String areacycle = json.getString("areacycle");
             String exhibitcycle = json.getString("exhibitcycle");
             String firstmedtime = json.getString("firstmedtime");
             try {
				 result = manageService.modifyFeeder(feederid, power, medtotal, medleft, areacycle, exhibitcycle, firstmedtime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "修改喂食器信息失败!";
			}
         }else if(clicktype.equals("ownermodify")) {
   
             String ownerid = json.getString("ownerid");
             String ownername = json.getString("ownername");
             String owneridentity = json.getString("owneridentity");
             String ownersex = json.getString("ownersex");
             String ownerage = json.getString("ownerage");
             String ownerjob = json.getString("ownerjob");
             String homeaddress = json.getString("homeaddress");
             String telphone = json.getString("telphone");
  
             try {
				 result = manageService.modifyOwner(ownerid, ownername, owneridentity, ownersex, ownerage, ownerjob, homeaddress, telphone);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "修改主人信息失败!";
			}
         }else if(clicktype.equals("dogmodify")) {
   
             String username = json.getString("username");
             String dogid = json.getString("dogid");
             String dogname = json.getString("dogname");
             String dogsex = json.getString("dogsex");
             String dogbelonghamlet = json.getString("dogbelonghamlet");
             String districtcode = json.getString("districtcode");
             String dogownerid = json.getString("dogownerid");
             String dogweight = json.getString("dogweight");
             String dogcolor = json.getString("dogcolor");
             String dogage = json.getString("dogage");
             String dogneckletid = json.getString("dogneckletid");
             String dogfeederid = json.getString("dogfeederid");
            try {
				 result = manageService.modifyDog(username, dogid, dogname, dogsex, dogbelonghamlet, districtcode, dogownerid, dogweight, dogcolor, dogage, dogneckletid, dogfeederid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("【系统错误】",e);
				result = "修改牧犬信息失败!";
			}
         }

		return result.toString();
	}
}
