package sec.secwatchdog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sec.secwatchdog.mapper.WarnEntity;
import sec.secwatchdog.model.DevicedEntity;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.WakeEntity;
import sec.secwatchdog.util.CalculateUtil;
import sec.secwatchdog.util.R;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	
	@RequestMapping("/anaindex")
	public String GoToAnalysisPage(HttpServletRequest request,ModelMap model) throws Exception {
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/analysis_logo");
		return url.toString();
	}
	
	
	@RequestMapping("/anawakelv")
	@ResponseBody
	public R AnalysisWakeLevel(HttpServletRequest request,@RequestParam(value="flag",required=false)String flag,@RequestParam(value="districode",required=false)String districode){
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){
			return R.error(400, "无效的登录");
		}
		List<WakeEntity> wakeList = new ArrayList<WakeEntity>();
		if(districode == null) {
			wakeList.clear();
			WakeEntity wakeEntity = null;
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname("全国");
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setNfwaketimes(2000);
					wakeEntity.setNwakelv(CalculateUtil.Get2double((2000/(2400*1.00))));
					wakeEntity.setAppnum(100);
					wakeEntity.setAswaketimes(1200);
					wakeEntity.setAfwaketimes(700);
					wakeEntity.setAwakelv(CalculateUtil.Get2double((700/(1200*1.00))));
					wakeEntity.setNanum(100+200);
					wakeEntity.setNaswaketimes(2400+1200);
					wakeEntity.setNafwaketimes(2000+700);
					wakeEntity.setNawakelv(CalculateUtil.Get2double(((2000+700)/((2400+1200)*1.00))));
					wakeList.add(wakeEntity);
				}
				else {
					wakeEntity.setGovname("省"+i);
					wakeEntity.setNeckletnum(20);
					wakeEntity.setNswaketimes(240);
					wakeEntity.setNfwaketimes(200);
					wakeEntity.setNwakelv(CalculateUtil.Get2double((200/(240*1.00))));
					wakeEntity.setAppnum(10);
					wakeEntity.setAswaketimes(120);
					wakeEntity.setAfwaketimes(70);
					wakeEntity.setAwakelv(CalculateUtil.Get2double((70/(120*1.00))));
					wakeEntity.setNanum(10+20);
					wakeEntity.setNaswaketimes(240+120);
					wakeEntity.setNafwaketimes(200+70);
					wakeEntity.setNawakelv(CalculateUtil.Get2double(((200+70)/((240+120)*1.00))));
					wakeList.add(wakeEntity);
				}
			}
		}else {
			wakeList.clear();
			WakeEntity wakeEntity = null;
			if(flag.equals("upper")) {
				//判断districode的上一级
				if(districode.contains("省")) {
					districode = "全国";
				}else if(districode.contains("市")) {
					districode = "省";
				}else if(districode.contains("县")) {
					districode = "市";
				}else if(districode.contains("乡")) {
					districode = "县";
				}
			}
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname(districode);
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setNfwaketimes(2000);
					wakeEntity.setNwakelv(CalculateUtil.Get2double((2000/(2400*1.00))));
					wakeEntity.setAppnum(100);
					wakeEntity.setAswaketimes(1200);
					wakeEntity.setAfwaketimes(700);
					wakeEntity.setAwakelv(CalculateUtil.Get2double((700/(1200*1.00))));
					wakeEntity.setNanum(100+200);
					wakeEntity.setNaswaketimes(2400+1200);
					wakeEntity.setNafwaketimes(2000+700);
					wakeEntity.setNawakelv(CalculateUtil.Get2double(((2000+700)/((2400+1200)*1.00))));
					wakeList.add(wakeEntity);
				}
				else {
					//case 省
					if(districode.contains("全国")) {
						wakeEntity.setGovname("省"+i);
						wakeEntity.setNeckletnum(20);
						wakeEntity.setNswaketimes(240);
						wakeEntity.setNfwaketimes(200);
						wakeEntity.setNwakelv(CalculateUtil.Get2double((200/(240*1.00))));
						wakeEntity.setAppnum(10);
						wakeEntity.setAswaketimes(120);
						wakeEntity.setAfwaketimes(70);
						wakeEntity.setAwakelv(CalculateUtil.Get2double((70/(120*1.00))));
						wakeEntity.setNanum(10+20);
						wakeEntity.setNaswaketimes(240+120);
						wakeEntity.setNafwaketimes(200+70);
						wakeEntity.setNawakelv(CalculateUtil.Get2double(((200+70)/((240+120)*1.00))));
						wakeList.add(wakeEntity);
					}else if(districode.contains("省")) {
						wakeEntity.setGovname("市"+i);
						wakeEntity.setNeckletnum(20);
						wakeEntity.setNswaketimes(240);
						wakeEntity.setNfwaketimes(200);
						wakeEntity.setNwakelv(CalculateUtil.Get2double((200/(240*1.00))));
						wakeEntity.setAppnum(10);
						wakeEntity.setAswaketimes(120);
						wakeEntity.setAfwaketimes(70);
						wakeEntity.setAwakelv(CalculateUtil.Get2double((70/(120*1.00))));
						wakeEntity.setNanum(10+20);
						wakeEntity.setNaswaketimes(240+120);
						wakeEntity.setNafwaketimes(200+70);
						wakeEntity.setNawakelv(CalculateUtil.Get2double(((200+70)/((240+120)*1.00))));
						wakeList.add(wakeEntity);
					}else if(districode.contains("市")) {
						wakeEntity.setGovname("县"+i);
						wakeEntity.setNeckletnum(20);
						wakeEntity.setNswaketimes(240);
						wakeEntity.setNfwaketimes(200);
						wakeEntity.setNwakelv(CalculateUtil.Get2double((200/(240*1.00))));
						wakeEntity.setAppnum(10);
						wakeEntity.setAswaketimes(120);
						wakeEntity.setAfwaketimes(70);
						wakeEntity.setAwakelv(CalculateUtil.Get2double((70/(120*1.00))));
						wakeEntity.setNanum(10+20);
						wakeEntity.setNaswaketimes(240+120);
						wakeEntity.setNafwaketimes(200+70);
						wakeEntity.setNawakelv(CalculateUtil.Get2double(((200+70)/((240+120)*1.00))));
						wakeList.add(wakeEntity);
					}else if(districode.contains("县")) {
						wakeEntity.setGovname("乡"+i);
						wakeEntity.setNeckletnum(20);
						wakeEntity.setNswaketimes(240);
						wakeEntity.setNfwaketimes(200);
						wakeEntity.setNwakelv(CalculateUtil.Get2double((200/(240*1.00))));
						wakeEntity.setAppnum(10);
						wakeEntity.setAswaketimes(120);
						wakeEntity.setAfwaketimes(70);
						wakeEntity.setAwakelv(CalculateUtil.Get2double((70/(120*1.00))));
						wakeEntity.setNanum(10+20);
						wakeEntity.setNaswaketimes(240+120);
						wakeEntity.setNafwaketimes(200+70);
						wakeEntity.setNawakelv(CalculateUtil.Get2double(((200+70)/((240+120)*1.00))));
						wakeList.add(wakeEntity);
					}else if(districode.contains("乡")) {
						wakeEntity.setGovname("村"+i);
						wakeEntity.setNeckletnum(20);
						wakeEntity.setNswaketimes(240);
						wakeEntity.setNfwaketimes(200);
						wakeEntity.setNwakelv(CalculateUtil.Get2double((200/(240*1.00))));
						wakeEntity.setAppnum(10);
						wakeEntity.setAswaketimes(120);
						wakeEntity.setAfwaketimes(70);
						wakeEntity.setAwakelv(CalculateUtil.Get2double((70/(120*1.00))));
						wakeEntity.setNanum(10+20);
						wakeEntity.setNaswaketimes(240+120);
						wakeEntity.setNafwaketimes(200+70);
						wakeEntity.setNawakelv(CalculateUtil.Get2double(((200+70)/((240+120)*1.00))));
						wakeList.add(wakeEntity);
					}
				}
			}
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",wakeList);
        return R.ok(map);
		
	}
	
	
	
	@RequestMapping("/anadevicedlv")
	@ResponseBody
	public R AnalysisDevicedLevel(HttpServletRequest request,@RequestParam(value="flag",required=false)String flag,@RequestParam(value="districode",required=false)String districode){
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){
			return R.error(400, "无效的登录");
		}
		List<DevicedEntity> devicedList = new ArrayList<DevicedEntity>();
		if(districode == null) {
			devicedList.clear();
			DevicedEntity devicedEntity = null;
			for(int i=0;i<10;i++) {
				devicedEntity = new DevicedEntity();
				if(i==0) {
					devicedEntity.setGovname("全国");
					devicedEntity.setDognum(20000);
					devicedEntity.setNeckletdognum(15000);
					devicedEntity.setNpilltimes(240000);
					devicedEntity.setAppdognum(3000);
					devicedEntity.setApilltimes(36000);
					devicedEntity.setManpilldog(800);
					devicedEntity.setManpilltimes(9600);
					devicedEntity.setAllpilldognum(18800);
					devicedEntity.setAllpilltimes(240000+36000+18800);
					devicedEntity.setDevicedlv(CalculateUtil.Get2double(((15000+3000)/(20000*1.00))));
					devicedList.add(devicedEntity);
				}
				else {
					devicedEntity.setGovname("省"+i);
					devicedEntity.setDognum(2000);
					devicedEntity.setNeckletdognum(1500);
					devicedEntity.setNpilltimes(24000);
					devicedEntity.setAppdognum(300);
					devicedEntity.setApilltimes(3600);
					devicedEntity.setManpilldog(80);
					devicedEntity.setManpilltimes(960);
					devicedEntity.setAllpilldognum(1880);
					devicedEntity.setAllpilltimes(24000+3600+1880);
					devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
					devicedList.add(devicedEntity);
				}
			}
		}else {
			devicedList.clear();
			DevicedEntity devicedEntity = null;
			if(flag.equals("upper")) {
				//判断districode的上一级
				if(districode.contains("省")) {
					districode = "全国";
				}else if(districode.contains("市")) {
					districode = "省";
				}else if(districode.contains("县")) {
					districode = "市";
				}else if(districode.contains("乡")) {
					districode = "县";
				}
			}
			for(int i=0;i<10;i++) {
				devicedEntity = new DevicedEntity();
				if(i==0) {
					devicedEntity.setGovname(districode);
					devicedEntity.setDognum(2000);
					devicedEntity.setNeckletdognum(1500);
					devicedEntity.setNpilltimes(24000);
					devicedEntity.setAppdognum(300);
					devicedEntity.setApilltimes(3600);
					devicedEntity.setManpilldog(80);
					devicedEntity.setManpilltimes(960);
					devicedEntity.setAllpilldognum(1880);
					devicedEntity.setAllpilltimes(24000+3600+1880);
					devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
					devicedList.add(devicedEntity);
				}
				else {
					//case 省
					if(districode.contains("全国")) {
						devicedEntity.setGovname("省"+i);
						devicedEntity.setDognum(2000);
						devicedEntity.setNeckletdognum(1500);
						devicedEntity.setNpilltimes(24000);
						devicedEntity.setAppdognum(300);
						devicedEntity.setApilltimes(3600);
						devicedEntity.setManpilldog(80);
						devicedEntity.setManpilltimes(960);
						devicedEntity.setAllpilldognum(1880);
						devicedEntity.setAllpilltimes(24000+3600+1880);
						devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
						devicedList.add(devicedEntity);
					}else if(districode.contains("省")) {
						devicedEntity.setGovname("市"+i);
						devicedEntity.setDognum(2000);
						devicedEntity.setNeckletdognum(1500);
						devicedEntity.setNpilltimes(24000);
						devicedEntity.setAppdognum(300);
						devicedEntity.setApilltimes(3600);
						devicedEntity.setManpilldog(80);
						devicedEntity.setManpilltimes(960);
						devicedEntity.setAllpilldognum(1880);
						devicedEntity.setAllpilltimes(24000+3600+1880);
						devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
						devicedList.add(devicedEntity);
					}else if(districode.contains("市")) {
						devicedEntity.setGovname("县"+i);
						devicedEntity.setDognum(2000);
						devicedEntity.setNeckletdognum(1500);
						devicedEntity.setNpilltimes(24000);
						devicedEntity.setAppdognum(300);
						devicedEntity.setApilltimes(3600);
						devicedEntity.setManpilldog(80);
						devicedEntity.setManpilltimes(960);
						devicedEntity.setAllpilldognum(1880);
						devicedEntity.setAllpilltimes(24000+3600+1880);
						devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
						devicedList.add(devicedEntity);
					}else if(districode.contains("县")) {
						devicedEntity.setGovname("乡"+i);
						devicedEntity.setDognum(2000);
						devicedEntity.setNeckletdognum(1500);
						devicedEntity.setNpilltimes(24000);
						devicedEntity.setAppdognum(300);
						devicedEntity.setApilltimes(3600);
						devicedEntity.setManpilldog(80);
						devicedEntity.setManpilltimes(960);
						devicedEntity.setAllpilldognum(1880);
						devicedEntity.setAllpilltimes(24000+3600+1880);
						devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
						devicedList.add(devicedEntity);
					}else if(districode.contains("乡")) {
						devicedEntity.setGovname("村"+i);
						devicedEntity.setDognum(2000);
						devicedEntity.setNeckletdognum(1500);
						devicedEntity.setNpilltimes(24000);
						devicedEntity.setAppdognum(300);
						devicedEntity.setApilltimes(3600);
						devicedEntity.setManpilldog(80);
						devicedEntity.setManpilltimes(960);
						devicedEntity.setAllpilldognum(1880);
						devicedEntity.setAllpilltimes(24000+3600+1880);
						devicedEntity.setDevicedlv(CalculateUtil.Get2double(((1500+300)/(2000*1.00))));
						devicedList.add(devicedEntity);
					}
				}
			}
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",devicedList);
        return R.ok(map);
		
	}
	
	
	@RequestMapping("/anawarnlv")
	@ResponseBody
	public R AnalysisWarnLevel(HttpServletRequest request,@RequestParam(value="flag",required=false)String flag,@RequestParam(value="districode",required=false)String districode){
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){
			return R.error(400, "无效的登录");
		}
		List<WarnEntity> warnList = new ArrayList<WarnEntity>();
		if(districode == null) {
			warnList.clear();
			WarnEntity warnEntity = null;
			for(int i=0;i<10;i++) {
				warnEntity = new WarnEntity();
				if(i==0) {
					warnEntity.setGovname("全国");
					warnEntity.setNeckletnum(20000);
					warnEntity.setNbugtimes(200);
					warnEntity.setNwarntimes(200000);
					warnEntity.setNwarnlv(0.90);
					warnEntity.setAppnum(5000);
					warnEntity.setAbugtimes(100);
					warnEntity.setAwarntimes(55000);
					warnEntity.setAwarnlv(0.85);
					warnEntity.setDevicenum(25000);
					warnEntity.setDbugtimes(300);
					warnEntity.setDwarntimes(255000);
					warnEntity.setDlv(0.89);
					warnList.add(warnEntity);
				}
				else {
					warnEntity.setGovname("省"+i);
					warnEntity.setNeckletnum(2000);
					warnEntity.setNbugtimes(20);
					warnEntity.setNwarntimes(20000);
					warnEntity.setNwarnlv(0.90);
					warnEntity.setAppnum(500);
					warnEntity.setAbugtimes(10);
					warnEntity.setAwarntimes(5500);
					warnEntity.setAwarnlv(0.85);
					warnEntity.setDevicenum(2500);
					warnEntity.setDbugtimes(30);
					warnEntity.setDwarntimes(25500);
					warnEntity.setDlv(0.89);
					warnList.add(warnEntity);
				}
			}
		}else {
			warnList.clear();
			WarnEntity warnEntity = null;
			if(flag.equals("upper")) {
				//判断districode的上一级
				if(districode.contains("省")) {
					districode = "全国";
				}else if(districode.contains("市")) {
					districode = "省";
				}else if(districode.contains("县")) {
					districode = "市";
				}else if(districode.contains("乡")) {
					districode = "县";
				}
			}
			for(int i=0;i<10;i++) {
				warnEntity = new WarnEntity();
				if(i==0) {
					warnEntity.setGovname(districode);
					warnEntity.setNeckletnum(2000);
					warnEntity.setNbugtimes(20);
					warnEntity.setNwarntimes(20000);
					warnEntity.setNwarnlv(0.90);
					warnEntity.setAppnum(500);
					warnEntity.setAbugtimes(10);
					warnEntity.setAwarntimes(5500);
					warnEntity.setAwarnlv(0.85);
					warnEntity.setDevicenum(2500);
					warnEntity.setDbugtimes(30);
					warnEntity.setDwarntimes(25500);
					warnEntity.setDlv(0.89);
					warnList.add(warnEntity);
				}
				else {
					//case 省
					if(districode.contains("全国")) {
						warnEntity.setGovname("省"+i);
						warnEntity.setNeckletnum(2000);
						warnEntity.setNbugtimes(20);
						warnEntity.setNwarntimes(20000);
						warnEntity.setNwarnlv(0.90);
						warnEntity.setAppnum(500);
						warnEntity.setAbugtimes(10);
						warnEntity.setAwarntimes(5500);
						warnEntity.setAwarnlv(0.85);
						warnEntity.setDevicenum(2500);
						warnEntity.setDbugtimes(30);
						warnEntity.setDwarntimes(25500);
						warnEntity.setDlv(0.89);
						warnList.add(warnEntity);
					}else if(districode.contains("省")) {
						warnEntity.setGovname("市"+i);
						warnEntity.setNeckletnum(2000);
						warnEntity.setNbugtimes(20);
						warnEntity.setNwarntimes(20000);
						warnEntity.setNwarnlv(0.90);
						warnEntity.setAppnum(500);
						warnEntity.setAbugtimes(10);
						warnEntity.setAwarntimes(5500);
						warnEntity.setAwarnlv(0.85);
						warnEntity.setDevicenum(2500);
						warnEntity.setDbugtimes(30);
						warnEntity.setDwarntimes(25500);
						warnEntity.setDlv(0.89);
						warnList.add(warnEntity);
					}else if(districode.contains("市")) {
						warnEntity.setGovname("县"+i);
						warnEntity.setNeckletnum(2000);
						warnEntity.setNbugtimes(20);
						warnEntity.setNwarntimes(20000);
						warnEntity.setNwarnlv(0.90);
						warnEntity.setAppnum(500);
						warnEntity.setAbugtimes(10);
						warnEntity.setAwarntimes(5500);
						warnEntity.setAwarnlv(0.85);
						warnEntity.setDevicenum(2500);
						warnEntity.setDbugtimes(30);
						warnEntity.setDwarntimes(25500);
						warnEntity.setDlv(0.89);
						warnList.add(warnEntity);
					}else if(districode.contains("县")) {
						warnEntity.setGovname("乡"+i);
						warnEntity.setNeckletnum(2000);
						warnEntity.setNbugtimes(20);
						warnEntity.setNwarntimes(20000);
						warnEntity.setNwarnlv(0.90);
						warnEntity.setAppnum(500);
						warnEntity.setAbugtimes(10);
						warnEntity.setAwarntimes(5500);
						warnEntity.setAwarnlv(0.85);
						warnEntity.setDevicenum(2500);
						warnEntity.setDbugtimes(30);
						warnEntity.setDwarntimes(25500);
						warnEntity.setDlv(0.89);
						warnList.add(warnEntity);
					}else if(districode.contains("乡")) {
						warnEntity.setGovname("村"+i);
						warnEntity.setNeckletnum(2000);
						warnEntity.setNbugtimes(20);
						warnEntity.setNwarntimes(20000);
						warnEntity.setNwarnlv(0.90);
						warnEntity.setAppnum(500);
						warnEntity.setAbugtimes(10);
						warnEntity.setAwarntimes(5500);
						warnEntity.setAwarnlv(0.85);
						warnEntity.setDevicenum(2500);
						warnEntity.setDbugtimes(30);
						warnEntity.setDwarntimes(25500);
						warnEntity.setDlv(0.89);
						warnList.add(warnEntity);
					}
				}
			}
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",warnList);
        return R.ok(map);
		
	}
}
