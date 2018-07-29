package sec.secwatchdog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.*;
import sec.secwatchdog.model.*;
import sec.secwatchdog.service.HamletService;
import sec.secwatchdog.util.NameConversionUtil;
@Service("hamletService")
public class HamletServiceImpl implements HamletService{

	@Autowired
	private NameConversionUtil nameConversionUtil;

	@Autowired
	private DistrictsDao districtsdao;

	@Autowired
	private SheepdogsDao sheepdogsdao;

	@Autowired
	private LastneckletrealtimeDao lastneckletrealtimedao;

	@Autowired
	private LastexhibitrealtimeDao lastexhibitrealtimedao;

	@Autowired
	private LastapparatusrealtimeDao lastapparatusrealtimedao;

	@Autowired
	private LastappexhibitrealtimeDao lastappexhibitrealtimedao;

	@Autowired
	private FeedbackDao feedbackdao;

	@Autowired
	private FeederbackDao feederbackdao;

	@Override
	public Map<String, Object> GetHamletMap(String province, String city, String county, String village,
			String hamlet,HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Map<String,Object> GetHamletMap = new HashMap<String,Object>();
		try {
			province = nameConversionUtil.EchartsAreaNameToGov(province);
			city = nameConversionUtil.EchartsAreaNameToGov(city);
			county = nameConversionUtil.EchartsAreaNameToGov(county);
			village = nameConversionUtil.EchartsAreaNameToGov(village);
			hamlet = nameConversionUtil.EchartsAreaNameToGov(hamlet);
			//获得该地区地区编码前两位(省)
			String provinceCode0to2 = districtsdao.getDistrictsByDistrictName(province).getDistrictcode().substring(0,2);
			//获得该地区地区编码前四位(市)
			String cityCode0to4 = districtsdao.getCityAndBelowDistrictsByDistrictName(city, provinceCode0to2).getDistrictcode().substring(0,4);
			//获得该地区地区编码前六位(县)
			String countyCode0to6 = districtsdao.getCityAndBelowDistrictsByDistrictName(county, cityCode0to4).getDistrictcode().substring(0,6);
			//获得该地区地区编码前九位(乡)
			String villageCode0to9 = districtsdao.getCityAndBelowDistrictsByDistrictName(village, countyCode0to6).getDistrictcode().substring(0,9);
			//获得该地区地区编码前12位(村)
			Districts thishamlet = districtsdao.getCityAndBelowDistrictsByDistrictName(hamlet, villageCode0to9);
			
			String hamletcode = thishamlet.getDistrictcode();
			session.setAttribute("hamletcode", hamletcode);
			List<Sheepdogs> doginfo = new ArrayList<Sheepdogs>();
			doginfo = sheepdogsdao.getHamletDogs(hamletcode);
			int i = 0;
			for(Sheepdogs each:doginfo) {
				Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("dogid", each.getDogid());
				maptemp.put("neckletid", each.getNeckletid());
				maptemp.put("dogname", each.getDogname());
				Lastneckletrealtime lr = lastneckletrealtimedao.getLastneckletrealtime(each.getNeckletid());
				if(lr!=null) {
					maptemp.put("lng", Double.parseDouble(lr.getNeckletlongitude()));
					maptemp.put("lat", Double.parseDouble(lr.getNeckletvdoing()));
				}else {
					maptemp.put("lng", Double.parseDouble(thishamlet.getLng()));
					maptemp.put("lat", Double.parseDouble(thishamlet.getLat()));
				}
				Lastexhibitrealtime lb = lastexhibitrealtimedao.getLastexhibitrealtime(each.getNeckletid());
				maptemp.put("nextmedtime", lb.getNextexhibittime());
				maptemp.put("manager", each.getManagername());
				maptemp.put("hamletname", hamlet);
				GetHamletMap.put(""+i, maptemp);
				i++;
			}
		}catch(Exception e) {
			return null;
		}
		return GetHamletMap;
	}

	@Override
	public String GovToEchartsAreaName(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> GetHamletFeederMap(String province, String city, String county, String village,
			String hamlet) {
		Map<String,Object> GetHamletFeederMap = new HashMap<String,Object>();
		Map<String,Object> GetHamletMap = new HashMap<String,Object>();
		try {
			province = nameConversionUtil.EchartsAreaNameToGov(province);
			city = nameConversionUtil.EchartsAreaNameToGov(city);
			county = nameConversionUtil.EchartsAreaNameToGov(county);
			village = nameConversionUtil.EchartsAreaNameToGov(village);
			hamlet = nameConversionUtil.EchartsAreaNameToGov(hamlet);
			//获得该地区地区编码前两位(省)
			String provinceCode0to2 = districtsdao.getDistrictsByDistrictName(province).getDistrictcode().substring(0, 2);
			//获得该地区地区编码前四位(市)
			String cityCode0to4 = districtsdao.getCityAndBelowDistrictsByDistrictName(city, provinceCode0to2).getDistrictcode().substring(0, 4);
			//获得该地区地区编码前六位(县)
			String countyCode0to6 = districtsdao.getCityAndBelowDistrictsByDistrictName(county, cityCode0to4).getDistrictcode().substring(0, 6);
			//获得该地区地区编码前九位(乡)
			String villageCode0to9 = districtsdao.getCityAndBelowDistrictsByDistrictName(village, countyCode0to6).getDistrictcode().substring(0, 9);
			//获得该地区地区编码前12位(村)
			Districts thishamlet = districtsdao.getCityAndBelowDistrictsByDistrictName(hamlet, villageCode0to9);
			String hamletcode = thishamlet.getDistrictcode();
			List<Sheepdogs> doginfo = new ArrayList<Sheepdogs>();
			doginfo = sheepdogsdao.getFarmDogFeederList(hamletcode);
			int i = 0;
			Map<String, Object> maptemp = new HashMap<String,Object>();
			for(Sheepdogs each:doginfo) {
				maptemp.put("dogid", each.getDogid());
				maptemp.put("feederid", each.getApparatusid());
				maptemp.put("dogname", each.getDogname());
				Lastapparatusrealtime lu = lastapparatusrealtimedao.getLastapparatusrealtime(each.getApparatusid());
				if(lu!=null) {
					maptemp.put("lng", Double.parseDouble(lu.getApparatuslongitude()));
					maptemp.put("lat", Double.parseDouble(lu.getApparatusvdoing()));
				}else {
					maptemp.put("lng", Double.parseDouble(thishamlet.getLng()));
					maptemp.put("lat", Double.parseDouble(thishamlet.getLat()));
				}
				Lastappexhibitrealtime lb = lastappexhibitrealtimedao.getLastappexhibitrealtime(each.getApparatusid());
				maptemp.put("nextmedtime", lb.getNextexhibittime());
				maptemp.put("manager", each.getManagername());
				maptemp.put("hamletname", hamlet);
				GetHamletFeederMap.put(""+i, maptemp);
				i++;
			}
		}catch (Exception e){
			return null;
		}
		return GetHamletFeederMap;
	}

	@Override
	public Map<String, Object> GetLevel6AdminDogNum(String username) {
		Map<String,Object> GetLevel6AdminDogNum = new HashMap<String,Object>();
		List<Sheepdogs> thisadmin =sheepdogsdao.getLevel6AdminDogNum(username);
		if(thisadmin.size() ==0) {
			GetLevel6AdminDogNum.put("dogtotalnum", 0);
			GetLevel6AdminDogNum.put("neckletedtotal", 0);
		}
		else {
			GetLevel6AdminDogNum.put("dogtotalnum", thisadmin.size());
			int i = 0;
			for(Sheepdogs each:thisadmin) {
				if(!each.getNeckletid().equals("-1")) {
					i++;
				}
			}
			GetLevel6AdminDogNum.put("neckletedtotal", i);
		}
		return GetLevel6AdminDogNum;
	}

	@Override
	public Map<String, Object> CombineNeckletAndFeederDogList(PageBean pageBean, String hamletCode) {
		Map<String,Object> combineneckletandfeederdoglist = new HashMap<String,Object>();
		Map<String, Object> paramsmap = new HashMap<String, Object>();
		paramsmap.put("pageBean1", pageBean);
		if(pageBean!=null){
			paramsmap.put("pagestart", pageBean.getStart());
			paramsmap.put("pagesize", pageBean.getPageSize());
		}
		paramsmap.put("hamletCode", hamletCode);
		List<Sheepdogs> doglist = new ArrayList<Sheepdogs>();
		doglist = sheepdogsdao.combineNeckletAndFeederDogList(paramsmap);
		int i = 0;
		for(Sheepdogs each:doglist) {
			Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("dogid", each.getDogid());
			maptemp.put("dogname", each.getDogname());
			String neckletid = each.getNeckletid();
			String apparatusid = each.getApparatusid();
			if(apparatusid.equals("-1") && !neckletid.equals("-1")) {
				Lastexhibitrealtime lb = lastexhibitrealtimedao.getLastexhibitrealtime(neckletid);
				if(lb != null) {
					maptemp.put("neckletid",  neckletid);
					Feedback feedback = feedbackdao.getFeedback(neckletid);
					maptemp.put("firstmedtime",  feedback.getFirstmedtime());
					maptemp.put("lastmed",  lb.getRealtime());
					maptemp.put("timemed",  feedback.getMedtotal() - lb.getTableremain());
					maptemp.put("nextmed",  lb.getNextexhibittime());
					maptemp.put("exhibitcycle", (Double.parseDouble(feedback.getExhibitcycle())/1440)+"");
				}else {
					maptemp.put("neckletid",  "----");
					maptemp.put("firstmedtime",  "");
					maptemp.put("lastmed",  "");
					maptemp.put("timemed",  0);
					maptemp.put("nextmed",  "");
					maptemp.put("exhibitcycle","10000");
				}
			}else if(!apparatusid.equals("-1") && neckletid.equals("-1")){
				Lastappexhibitrealtime le = lastappexhibitrealtimedao.getLastappexhibitrealtime(apparatusid);
				if(le!=null) {
					maptemp.put("feederid", apparatusid);
					Feederback fb = feederbackdao.getFeederback(apparatusid);
					maptemp.put("firstmedtime", fb.getFirstmedtime());
					maptemp.put("lastmed", le.getRealtime());
					maptemp.put("timemed", fb.getMedtotal()-le.getTableremain());
					maptemp.put("nextmed", le.getNextexhibittime());
					maptemp.put("exhibitcycle", "10000");
				}else {
					maptemp.put("feederid", "----");
					maptemp.put("firstmedtime", "");
					maptemp.put("lastmed", "");
					maptemp.put("timemed", 0);
					maptemp.put("nextmed", "");
					maptemp.put("exhibitcycle","10000");
				}
			}
			combineneckletandfeederdoglist.put(""+i, maptemp);
			i++;
		}
		return combineneckletandfeederdoglist;
	}

	@Override
	public Integer CombineNeckletAndFeederDogTotal(String hamletCode) {
		int total = sheepdogsdao.combiNeckletAndFeederDogTotal(hamletCode);
		return total;
	}

	@Override
	public Map<String, Object> getCombineNeckletAndFeederDogByNeckletId(String neckletId, String hamletCode) {
		Map<String,Object> combineneckletandfeederdog = new HashMap<String,Object>();
		
		List<Sheepdogs> doglist = new ArrayList<Sheepdogs>();
		doglist = sheepdogsdao.getCombineNeckletAndFeederDogByNeckletId(neckletId, hamletCode);
		int i = 0;
		for(Sheepdogs each:doglist) {
			Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("dogid", each.getDogid());
			maptemp.put("dogname", each.getDogname());
			String neckletid = each.getNeckletid();
			String apparatusid = each.getApparatusid();
			if(apparatusid.equals("-1") && !neckletid.equals("-1")) {
				Lastexhibitrealtime lb = lastexhibitrealtimedao.getLastexhibitrealtime(neckletid);
				if(lb != null) {
					maptemp.put("neckletid",  neckletid);
					Feedback feedback = feedbackdao.getFeedback(neckletid);
					maptemp.put("firstmedtime",  feedback.getFirstmedtime());
					maptemp.put("lastmed",  lb.getRealtime());
					maptemp.put("timemed",  feedback.getMedtotal() - lb.getTableremain());
					maptemp.put("nextmed",  lb.getNextexhibittime());
					maptemp.put("exhibitcycle", (Double.parseDouble(feedback.getExhibitcycle())/1440)+"");
				}else {
					maptemp.put("neckletid",  "----");
					maptemp.put("firstmedtime",  "");
					maptemp.put("lastmed",  "");
					maptemp.put("timemed",  0);
					maptemp.put("nextmed",  "");
					maptemp.put("exhibitcycle","10000");
				}
			}else if(!apparatusid.equals("-1") && neckletid.equals("-1")){
				Lastappexhibitrealtime le = lastappexhibitrealtimedao.getLastappexhibitrealtime(apparatusid);
				if(le!=null) {
					maptemp.put("feederid", apparatusid);
					Feederback fb = feederbackdao.getFeederback(apparatusid);
					maptemp.put("firstmedtime", fb.getFirstmedtime());
					maptemp.put("lastmed", le.getRealtime());
					maptemp.put("timemed", fb.getMedtotal()-le.getTableremain());
					maptemp.put("nextmed", le.getNextexhibittime());
					maptemp.put("exhibitcycle", "10000");
				}else {
					maptemp.put("feederid", "----");
					maptemp.put("firstmedtime", "");
					maptemp.put("lastmed", "");
					maptemp.put("timemed", 0);
					maptemp.put("nextmed", "");
					maptemp.put("exhibitcycle","10000");
				}
			}
			combineneckletandfeederdog.put(""+i, maptemp);
			i++;
		}
		return combineneckletandfeederdog;
	}
	
}
