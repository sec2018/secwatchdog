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
	public R AnalysisWakeLevel(HttpServletRequest request,@RequestParam(value="province",required=false)String province,
			@RequestParam(value="city",required=false)String city,@RequestParam(value="county",required=false)String county,
			@RequestParam(value="village",required=false)String village,@RequestParam(value="hamlet",required=false)String hamlet){
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){
			return R.error(400, "无效的登录");
		}
		List<WakeEntity> wakeList = new ArrayList<WakeEntity>();
		if(province == null) {
			wakeList.clear();
			WakeEntity wakeEntity = null;
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname("全国");
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setAfwaketimes(2000);
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
					wakeEntity.setAfwaketimes(200);
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
		}else if(province != null && city == null){
			wakeList.clear();
			WakeEntity wakeEntity = null;
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname(province);
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setAfwaketimes(2000);
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
					wakeEntity.setGovname("市"+i);
					wakeEntity.setNeckletnum(20);
					wakeEntity.setNswaketimes(240);
					wakeEntity.setAfwaketimes(200);
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
		}else if(province != null && city != null && county == null){
			wakeList.clear();
			WakeEntity wakeEntity = null;
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname(city);
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setAfwaketimes(2000);
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
					wakeEntity.setGovname("县"+i);
					wakeEntity.setNeckletnum(20);
					wakeEntity.setNswaketimes(240);
					wakeEntity.setAfwaketimes(200);
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
		}else if(province != null && city != null && county != null && village == null){
			wakeList.clear();
			WakeEntity wakeEntity = null;
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname(county);
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setAfwaketimes(2000);
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
					wakeEntity.setGovname("乡"+i);
					wakeEntity.setNeckletnum(20);
					wakeEntity.setNswaketimes(240);
					wakeEntity.setAfwaketimes(200);
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
		}else if(province != null && city != null && county != null && village != null){
			wakeList.clear();
			WakeEntity wakeEntity = null;
			for(int i=0;i<10;i++) {
				wakeEntity = new WakeEntity();
				if(i==0) {
					wakeEntity.setGovname(village);
					wakeEntity.setNeckletnum(200);
					wakeEntity.setNswaketimes(2400);
					wakeEntity.setAfwaketimes(2000);
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
					wakeEntity.setGovname("村"+i);
					wakeEntity.setNeckletnum(20);
					wakeEntity.setNswaketimes(240);
					wakeEntity.setAfwaketimes(200);
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
		
		
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",wakeList);
        return R.ok(map);
		
	}
}
