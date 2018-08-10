package sec.secwatchdog.service.impl;

 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import sec.secwatchdog.mapper.ApparatusrealtimeDao;
import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.DogownersDao;
import sec.secwatchdog.mapper.FeedbackDao;
import sec.secwatchdog.mapper.FeederDao;
import sec.secwatchdog.mapper.FeederbackDao;
import sec.secwatchdog.mapper.LastapparatusrealtimeDao;
import sec.secwatchdog.mapper.LastappexhibitrealtimeDao;
import sec.secwatchdog.mapper.LastexhibitrealtimeDao;
import sec.secwatchdog.mapper.LastneckletrealtimeDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.NeckletDao;
import sec.secwatchdog.mapper.NeckletrealtimeDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Apparatusrealtime;
import sec.secwatchdog.model.Appexhibitrealtime;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Dogowners;
import sec.secwatchdog.model.Feedback;
import sec.secwatchdog.model.Feeder;
import sec.secwatchdog.model.Feederback;
import sec.secwatchdog.model.Lastapparatusrealtime;
import sec.secwatchdog.model.Lastappexhibitrealtime;
import sec.secwatchdog.model.Lastexhibitrealtime;
import sec.secwatchdog.model.Lastneckletrealtime;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.Necklet;
import sec.secwatchdog.model.Neckletrealtime;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.ManageService;
import sec.secwatchdog.util.ChangeTimeFormat;
import sec.secwatchdog.util.NameConversionUtil;
@Service("managerService")
public class ManagerServiceImpl implements ManageService {

	@Autowired
	private ManagersDao managersDao;
	@Autowired
	private NameConversionUtil nameConversionUtil;
	@Autowired
	private DistrictsDao districtsDao;
	@Autowired
	private SheepdogsDao sheepdogsDao;
	@Autowired
	private DogownersDao dogownersDao;
	@Autowired
	private NeckletDao neckletDao;
	@Autowired
	private FeederDao feederDao;
	@Autowired
	private NeckletrealtimeDao neckletrealtimeDao;
	@Autowired
	private LastneckletrealtimeDao lastneckletrealtimeDao;
	@Autowired
	private LastexhibitrealtimeDao lastexhibitrealtimeDao;
	@Autowired
	private FeedbackDao feedbackDao;
	@Autowired
	private ApparatusrealtimeDao apparatusrealtimeDao;
	@Autowired
	private LastapparatusrealtimeDao lastapparatusrealtimeDao;
	@Autowired
	private LastappexhibitrealtimeDao lastappexhibitrealtimeDao;
	@Autowired
	private FeederbackDao feederbackDao;

	@Override
	public Map<String, Object> getNextLevelAdminInfo(String managerName, int startItem, int pageSize) {
		Managers thismanager = managersDao.getManagerByName(managerName);
		//如果该管理员不存在，则返回
		if (thismanager==null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> data = new HashMap<String,Object>();
		List<Managers> magagers = new ArrayList<Managers>();
		int i = 0;
		Page page = PageHelper.startPage(startItem, pageSize);
		switch(thismanager.getPrivilegelevel()) {
			case 1://全国级别管理员
				magagers = managersDao.getManagersByPrivilegelevel(2);//所用省级管理员
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getProvince());
					maptemp.put("job", magager.getWorkplace());
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);
			        //获得该省所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(provinceCode0to2);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 2://省级管理员
				//该省级管理员所在省份的所有市级管理员
				magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(String.valueOf(thismanager.getDistrictcode()).substring(0, 2), 3);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getCity());
					maptemp.put("upmanagerarea", magager.getProvince());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
			        //获得该市所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(cityCode0to4);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 3:
				magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(String.valueOf(thismanager.getDistrictcode()).substring(0, 4), 4);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getCounty());
					maptemp.put("upmanagerarea", magager.getCity());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
					
					//获得该地区地区编码前六位(县)
					Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCounty(), cityCode0to4);
					String countyCode = county.getDistrictcode();
					String countyCode0to6 = countyCode.substring(0,6);	 
			        //获得该县所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(countyCode0to6);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 4:
				magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(String.valueOf(thismanager.getDistrictcode()).substring(0, 6), 5);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getVillage());
					maptemp.put("upmanagerarea", magager.getCounty());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
					
					//获得该地区地区编码前六位(县)
					Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCounty(), cityCode0to4);
					String countyCode = county.getDistrictcode();
					String countyCode0to6 = countyCode.substring(0,6);	 
					//获得该地区地区编码前九位(乡)
					Districts village= districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getVillage(), countyCode0to6);
					String villageCode = village.getDistrictcode();
					String villageCode0to9 = villageCode.substring(0,9);	
			        //获得该乡所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(villageCode0to9);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 5:
				magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(String.valueOf(thismanager.getDistrictcode()).substring(0, 9), 6);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getHamlet());
					maptemp.put("upmanagerarea", magager.getVillage());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
					
					//获得该地区地区编码前六位(县)
					Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCounty(), cityCode0to4);
					String countyCode = county.getDistrictcode();
					String countyCode0to6 = countyCode.substring(0,6);	 
					//获得该地区地区编码前九位(乡)
					Districts village= districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getVillage(), countyCode0to6);
					String villageCode = village.getDistrictcode();
					String villageCode0to9 = villageCode.substring(0,9);	
					//获得该地区地区编码(村)
					Districts hamlet= districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getHamlet(), villageCode0to9);
					String hamletCode = hamlet.getDistrictcode();
			        //获得该村所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(hamletCode);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
				
		}
		
        //每页管理员信息
        map.put("data", data);
        //管理员总数
        map.put("totalNum", page.getTotal());

		return map;
	}
//通过包括省级和省级以下，乡级和乡级以上地图页面进入管理页面
	@Override
	public Map<String, Object> getNextLevelAdminInfo(String managerName, String districtcode, int startItem,
			int pageSize) {
		Managers thismanager = managersDao.getManagerByName(managerName);
		if (thismanager==null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> data = new HashMap<String,Object>();
		Map<String, Object> districtInfo = new HashMap<String,Object>();
        Districts district = districtsDao.getDistrictsByDistrictcode(districtcode).get(0);
		String districtcodetemp = districtcode.replaceAll("0*$", "");
        switch (districtcodetemp.length())
        {
            case 1:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 3:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 5:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 7:
                districtcodetemp = districtcodetemp + "00";
                break;
            case 8:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 10:
                districtcodetemp = districtcodetemp + "00";
                break;
            case 11:
                districtcodetemp = districtcodetemp + "0";
                break;
        }
		List<Managers> magagers = new ArrayList<Managers>();
		int i = 0;
		Page page = null;
        switch(districtcodetemp.length()) {
        	case 2:
        		districtInfo.put("districtlevel", district.getDistrictlevel());
        		districtInfo.put("province", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 2)+"0000000000"));
        		districtInfo.put("districtcode", districtcode);
        		page = PageHelper.startPage(startItem, pageSize);
        		magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(districtcodetemp, 3);
            	for(Managers magager : magagers) {
    				Map<String, Object> maptemp = new HashMap<String,Object>();
    				maptemp.put("username", magager.getUsername());
    				maptemp.put("managername", magager.getManagername());
    				maptemp.put("logintime", magager.getLogintime());
    				maptemp.put("managearea", magager.getCity());
    				maptemp.put("upmanagerarea", magager.getProvince());
    				maptemp.put("job", magager.getWorkplace());
    			
    				String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
    				//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
    				//获得该地区地区编码前两位(省)
    				Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
    				String provinceCode = districtsist.getDistrictcode();
    				String provinceCode0to2 = provinceCode.substring(0,2);	
    				//获得该地区地区编码前四位(市)
    				Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
    				String cityCode = city.getDistrictcode();
    				String cityCode0to4 = cityCode.substring(0,4);	
    		        //获得该市所有的狗
    				List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(cityCode0to4);
    				//佩戴项圈牧犬数量和喂食器数量
    				int neckletedtotal = 0;
    				int feederedtotal = 0;
    				for(Sheepdogs each:sdlist){
    					//"-1"表示未佩戴项圈
    					if(!each.getNeckletid().equals("-1")) {
    						neckletedtotal++;
    					}
    					//"-1"表示无喂食器
    					if(!each.getApparatusid().equals("-1")) {
    						feederedtotal++;
    					}
    				}
    				maptemp.put("dogtotalnum", sdlist.size());
    				maptemp.put("neckletedtotal", neckletedtotal);
    				maptemp.put("feederedtotal", feederedtotal);
    				maptemp.put("officecall", magager.getOfficecall());
    				maptemp.put("telphonecall", magager.getManagertel());
    				data.put(""+i, maptemp);
    				
    				i++;
            	}
            	break;

			case 4:
				districtInfo.put("districtlevel", district.getDistrictlevel());
        		districtInfo.put("province", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 2)+"0000000000"));
        		districtInfo.put("city", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 4)+"00000000"));
        		districtInfo.put("districtcode", districtcode);
        		page = PageHelper.startPage(startItem, pageSize);
        		magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(districtcodetemp, 4);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getCounty());
					maptemp.put("upmanagerarea", magager.getCity());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
					
					//获得该地区地区编码前六位(县)
					Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCounty(), cityCode0to4);
					String countyCode = county.getDistrictcode();
					String countyCode0to6 = countyCode.substring(0,6);	 
			        //获得该县所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(countyCode0to6);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 6:
				districtInfo.put("districtlevel", district.getDistrictlevel());
        		districtInfo.put("province", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 2)+"0000000000"));
        		districtInfo.put("city", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 4)+"00000000"));
        		districtInfo.put("county", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 6)+"000000"));
        		districtInfo.put("districtcode", districtcode);
        		page = PageHelper.startPage(startItem, pageSize);
        		magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(districtcodetemp, 5);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getVillage());
					maptemp.put("upmanagerarea", magager.getCounty());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
					
					//获得该地区地区编码前六位(县)
					Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCounty(), cityCode0to4);
					String countyCode = county.getDistrictcode();
					String countyCode0to6 = countyCode.substring(0,6);	 
					//获得该地区地区编码前九位(乡)
					Districts village= districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getVillage(), countyCode0to6);
					String villageCode = village.getDistrictcode();
					String villageCode0to9 = villageCode.substring(0,9);	
			        //获得该乡所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(villageCode0to9);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 9:
				districtInfo.put("districtlevel", district.getDistrictlevel());
        		districtInfo.put("province", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 2)+"0000000000"));
        		districtInfo.put("city", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 4)+"00000000"));
        		districtInfo.put("county", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 6)+"000000"));
        		districtInfo.put("village", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 9)+"000"));
        		districtInfo.put("districtcode", districtcode);
        		page = PageHelper.startPage(startItem, pageSize);
        		magagers = managersDao.getManagersByDistrictcodeAndPrivilegelevel(districtcodetemp, 6);
        		for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getHamlet());
					maptemp.put("upmanagerarea", magager.getVillage());
					maptemp.put("job", magager.getWorkplace());
				
					String provinceName = nameConversionUtil.EchartsAreaNameToGov(magager.getProvince().toString());
					//String cityName = nameConversionUtil.EchartsAreaNameToGov(magager.getCity().toString());
					//获得该地区地区编码前两位(省)
					Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
					String provinceCode = districtsist.getDistrictcode();
					String provinceCode0to2 = provinceCode.substring(0,2);	
					//获得该地区地区编码前四位(市)
					Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCity(), provinceCode0to2);
					String cityCode = city.getDistrictcode();
					String cityCode0to4 = cityCode.substring(0,4);	
					
					//获得该地区地区编码前六位(县)
					Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getCounty(), cityCode0to4);
					String countyCode = county.getDistrictcode();
					String countyCode0to6 = countyCode.substring(0,6);	 
					//获得该地区地区编码前九位(乡)
					Districts village= districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getVillage(), countyCode0to6);
					String villageCode = village.getDistrictcode();
					String villageCode0to9 = villageCode.substring(0,9);	
					//获得该地区地区编码(村)
					Districts hamlet= districtsDao.getCityAndBelowDistrictsByDistrictName(magager.getHamlet(), villageCode0to9);
					String hamletCode = hamlet.getDistrictcode();
			        //获得该村所有的狗
					List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(hamletCode);
					//佩戴项圈牧犬数量和喂食器数量
					int neckletedtotal = 0;
					int feederedtotal = 0;
					for(Sheepdogs each:sdlist){
						//"-1"表示未佩戴项圈
						if(!each.getNeckletid().equals("-1")) {
							neckletedtotal++;
						}
						//"-1"表示无喂食器
						if(!each.getApparatusid().equals("-1")) {
							feederedtotal++;
						}
					}
					maptemp.put("dogtotalnum", sdlist.size());
					maptemp.put("neckletedtotal", neckletedtotal);
					maptemp.put("feederedtotal", feederedtotal);
					maptemp.put("officecall", magager.getOfficecall());
					maptemp.put("telphonecall", magager.getManagertel());
					data.put(""+i, maptemp);
					
					i++;
					
				}
				break;
        }
        //每页管理员信息
        map.put("data", data);
        //管理员总数
        map.put("totalNum", page.getTotal());

        map.put("districtInfo", districtInfo);
      	 
		return map;
	}
	@Override
	public Map<String, Object> getManagerInfo(String managerName) {
		Map<String, Object> map = new HashMap<String,Object>();
		Managers manager = managersDao.getManagerByName(managerName);
        if (manager!=null)
        {
			switch(manager.getPrivilegelevel()) {
				case 1:
					map.put("area", "全国");
					  break;
	            case 2:
	            	map.put("area", manager.getProvince());
	                break;
	            case 3:
	            	map.put("area", manager.getProvince()+manager.getCity());            
	                break;
	            case 4:
	            	map.put("area",  manager.getProvince()+manager.getCity()+manager.getCounty());
	                break;
	            case 5:
	            	map.put("area",  manager.getProvince()+manager.getCity()+manager.getCounty()+manager.getVillage());
	                break;
	            case 6:
	            	map.put("area",  manager.getProvince()+manager.getCity()+manager.getCounty()+manager.getVillage()+manager.getHamlet());
	            	break;
	            case 7:
	            	map.put("area",  "游客模式");
	            	break;
	     
			}
			map.put("username", manager.getUsername());
			map.put("managername", manager.getManagername());
			map.put("privilegelevel", manager.getPrivilegelevel());
			map.put("province", manager.getProvince());
			map.put("city", manager.getCity());
			map.put("county", manager.getCounty());
			map.put("village", manager.getVillage());
			map.put("hamlet", manager.getHamlet());
			map.put("job", manager.getWorkplace());
			map.put("manageridentity", manager.getManageridentity());
			map.put("officecall", manager.getOfficecall());
			map.put("telphone", manager.getManagertel());
			map.put("address", manager.getAddress());
			map.put("email", manager.getEmail());
			map.put("password", manager.getPassword());
			map.put("chargehamlet", manager.getChargehamlet());
			map.put("districtcode", manager.getDistrictcode());
			map.put("adminstatus", manager.getManagerstatus() == 1? "已激活" : "未激活");
		}
		else
        {
			map.put("username", "");
			map.put("managername", "");
			map.put("privilegelevel", -1);
			map.put("province", "");
			map.put("city", "");
			map.put("county", "");
			map.put("village","");
			map.put("hamlet", "");
			map.put("job", "");
			map.put("manageridentity", "");
			map.put("officecall", "");
			map.put("telphone", "");
			map.put("address", "");
			map.put("email", "");
			map.put("password", "");
			map.put("chargehamlet", "");
			map.put("districtcode", "");           
          
        }
		return map;
	}

	@Override
	public Map<String, Object>  getSearchByAdminInfo(String userName, String managerName,int startItem,int pageSize) {//managername可能是姓名、区域或手机号码
		
		Managers manager = managersDao.getManagerByName(userName);
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> data = new HashMap<String,Object>();
		List<Managers> magagers = new ArrayList<Managers>();
		int i=0;
		Page page = PageHelper.startPage(startItem, pageSize);
		switch(manager.getPrivilegelevel()) {
			case 1:
			    magagers = managersDao.getAllManagersByManagerName(manager.getPrivilegelevel(),managerName);
			    break;
			 case 2:
				 magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(manager.getPrivilegelevel(),String.valueOf(manager.getDistrictcode()).substring(0, 2),managerName);			   
                 break;
             case 3:
            	 magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(manager.getPrivilegelevel(),String.valueOf(manager.getDistrictcode()).substring(0, 4),managerName);			  
                  break;
             case 4:
            	 magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(manager.getPrivilegelevel(),String.valueOf(manager.getDistrictcode()).substring(0, 6),managerName);			  
                 break;
             case 5:
            	 magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(manager.getPrivilegelevel(),String.valueOf(manager.getDistrictcode()).substring(0, 9),managerName);			  
                break;
		}
 
		   for (Managers item:magagers)
           { 
			   List<Sheepdogs> sdlist = null;
			   String manageareatemp = null;
			   String provinceName,provinceCode,provinceCode0to2,cityName,cityCode,cityCode0to4,countyName,countyCode,countyCode0to6,
			   villageName,villageCode,villageCode0to9,hamlet,hamletCode;
               switch (item.privilegelevel)
               { 
                   case 2:
                	   manageareatemp = item.getProvince();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	       				//获得该地区地区编码前两位(省)
                	   provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	       			   provinceCode0to2 = provinceCode.substring(0,2);	
	       		        //获得该省所有的狗
	       			   sdlist = sheepdogsDao.getIndexInforByDistrictcode(provinceCode0to2);
                       break;
                   case 3:
                	   manageareatemp = item.getCity();
                	    provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());       				
                	    cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());       				
	                	 //获得该地区地区编码前两位(省)
                	    provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	               		sdlist = sheepdogsDao.getIndexInforByDistrictcode(cityCode0to4);
                        break;
                   case 4:
                	   manageareatemp = item.getCounty();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	               		cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());
	               		countyName = nameConversionUtil.EchartsAreaNameToGov(item.getCounty());
	               	 //获得该地区地区编码前两位(省)
                	    provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	            		
	            		//获得该地区地区编码前六位(县)
	               		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4).getDistrictcode();
	            		countyCode0to6 = countyCode.substring(0,6);	 
	            		sdlist = sheepdogsDao.getIndexInforByDistrictcode(countyCode0to6);
	            		break;
                   case 5:
                	   manageareatemp = item.getVillage();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	               		cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());
	               		countyName = nameConversionUtil.EchartsAreaNameToGov(item.getCounty());
	               		villageName = nameConversionUtil.EchartsAreaNameToGov(item.getVillage());
	               		//获得该地区地区编码前两位(省)
               	        provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	            		
	            		//获得该地区地区编码前六位(县)
	               		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4).getDistrictcode();
	            		countyCode0to6 = countyCode.substring(0,6);	 
	            		//获得该地区地区编码前九位(乡)
	            		villageCode = districtsDao.getCityAndBelowDistrictsByDistrictName(villageName, countyCode0to6).getDistrictcode();
	            		villageCode0to9 = villageCode.substring(0,9);	
	            		sdlist = sheepdogsDao.getIndexInforByDistrictcode(villageCode0to9);
                       break;
                   case 6:
                	   manageareatemp = item.getHamlet();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	               		cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());
	               		countyName = nameConversionUtil.EchartsAreaNameToGov(item.getCounty());
	               		villageName = nameConversionUtil.EchartsAreaNameToGov(item.getVillage());
	               		//hamlet = nameConversionUtil.EchartsAreaNameToGov(item.getHamlet());
	               	
	               	   //获得该地区地区编码前两位(省)
               	       provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	            		
	            		//获得该地区地区编码前六位(县)
	               		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4).getDistrictcode();
	            		countyCode0to6 = countyCode.substring(0,6);	             		
	            		//获得该地区地区编码前九位(乡)
	            		villageCode = districtsDao.getCityAndBelowDistrictsByDistrictName(villageName, countyCode0to6).getDistrictcode();
	            		villageCode0to9 = villageCode.substring(0,9);	
	            		hamletCode = districtsDao.getCityAndBelowDistrictsByDistrictName(item.getHamlet(), villageCode0to9).getDistrictcode();
	            		sdlist = sheepdogsDao.getIndexInforByDistrictcode(hamletCode);
                       break;
               }

               Map<String, Object> maptemp = new HashMap<String,Object>();
               
               maptemp.put("username",item.username);
               maptemp.put("managername", item.managername+"11");
               maptemp.put("logintime", item.logintime);
               
               if (item.workplace.isEmpty())
               {
            	   maptemp.put("job", "----");
               }
               else
               {
            	   maptemp.put("job", item.getWorkplace());
               }
               
               if (item.officecall.isEmpty())
               {
            	   maptemp.put("officecall", "----");
               }
               else
               {
            	   maptemp.put("officecall",item.getOfficecall());
               }
               
               if (item.managertel.isEmpty())
               {
            	   maptemp.put("telphonecall", "----");
               }
               else
               {
            	   maptemp.put("telphonecall" , item.getManagertel());
               }
             //  maptemp.put("privilegelevel",item.getPrivilegelevel());
			   maptemp.put("managearea", manageareatemp);		
			
				//佩戴项圈牧犬数量和喂食器数量
				int neckletedtotal = 0;
				int feederedtotal = 0;
				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckletedtotal++;
					}
					//"-1"表示无喂食器
					if(!each.getApparatusid().equals("-1")) {
						feederedtotal++;
					}
				}
				maptemp.put("dogtotalnum", sdlist.size());
				maptemp.put("neckletedtotal", neckletedtotal);
				maptemp.put("feederedtotal", feederedtotal);
				data.put(""+i, maptemp);				
				 i++;	 
              
           }
		//每页管理员信息
		map.put("data", data);
	
		//管理员总数
		map.put("totalNum", page.getTotal());

		return map;
	}

	@Override
	public Map<String, Object> getSearchByAdminInfo(String districtlevel, String managerName, String districtCode,
			int startItem, int pageSize) {

		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> data = new HashMap<String,Object>();
		List<Managers> magagers = new ArrayList<Managers>();
		int i=0;
		String districtcodetemp = districtCode.replaceAll("0*$", "");
        switch (districtcodetemp.length())
        {
            case 1:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 3:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 5:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 7:
                districtcodetemp = districtcodetemp + "00";
                break;
            case 8:
                districtcodetemp = districtcodetemp + "0";
                break;
            case 10:
                districtcodetemp = districtcodetemp + "00";
                break;
            case 11:
                districtcodetemp = districtcodetemp + "0";
                break;
        }
		Page page = PageHelper.startPage(startItem, pageSize);

		switch(Integer.parseInt(districtlevel)) {
			case 0:
			    magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(2,districtcodetemp,managerName);
			    break;
			 case 1:
				  magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(3,districtcodetemp,managerName);
				  break;
             case 2:
            	  magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(4,districtcodetemp,managerName);
                  break;
             case 3:
            	  magagers = managersDao.getAllManagersByManagerNameAndDistrictcode(5,districtcodetemp,managerName);
                 break;
       
		}
 
		   for (Managers item:magagers)
           { 
			   List<Sheepdogs> sdlist = null;
			   String manageareatemp = null;
			   String provinceName,provinceCode,provinceCode0to2,cityName,cityCode,cityCode0to4,countyName,countyCode,countyCode0to6,
			   villageName,villageCode,villageCode0to9,hamlet,hamletCode;
               switch (item.privilegelevel)
               { 
                   case 2:
                	   manageareatemp = item.getProvince();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	       				//获得该地区地区编码前两位(省)
                	   provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	       			   provinceCode0to2 = provinceCode.substring(0,2);	
	       		        //获得该省所有的狗
	       			   sdlist = sheepdogsDao.getIndexInforByDistrictcode(provinceCode0to2);
                       break;
                   case 3:
                	   manageareatemp = item.getCity();
                	    provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());       				
                	    cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());       				
	                	 //获得该地区地区编码前两位(省)
                	    provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	               		sdlist = sheepdogsDao.getIndexInforByDistrictcode(cityCode0to4);
                        break;
                   case 4:
                	   manageareatemp = item.getCounty();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	               		cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());
	               		countyName = nameConversionUtil.EchartsAreaNameToGov(item.getCounty());
	               	 //获得该地区地区编码前两位(省)
                	    provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	            		
	            		//获得该地区地区编码前六位(县)
	               		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4).getDistrictcode();
	            		countyCode0to6 = countyCode.substring(0,6);	 
	            		sdlist = sheepdogsDao.getIndexInforByDistrictcode(countyCode0to6);
	            		break;
                   case 5:
                	   manageareatemp = item.getVillage();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	               		cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());
	               		countyName = nameConversionUtil.EchartsAreaNameToGov(item.getCounty());
	               		villageName = nameConversionUtil.EchartsAreaNameToGov(item.getVillage());
	               		//获得该地区地区编码前两位(省)
               	        provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	            		
	            		//获得该地区地区编码前六位(县)
	               		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4).getDistrictcode();
	            		countyCode0to6 = countyCode.substring(0,6);	 
	            		//获得该地区地区编码前九位(乡)
	            		villageCode = districtsDao.getCityAndBelowDistrictsByDistrictName(villageName, countyCode0to6).getDistrictcode();
	            		villageCode0to9 = villageCode.substring(0,9);	
	            		sdlist = sheepdogsDao.getIndexInforByDistrictcode(villageCode0to9);
                       break;
                   case 6:
                	   manageareatemp = item.getHamlet();
                	   provinceName = nameConversionUtil.EchartsAreaNameToGov(item.getProvince().toString());
	               		cityName = nameConversionUtil.EchartsAreaNameToGov(item.getCity().toString());
	               		countyName = nameConversionUtil.EchartsAreaNameToGov(item.getCounty());
	               		villageName = nameConversionUtil.EchartsAreaNameToGov(item.getVillage());
	               		//hamlet = nameConversionUtil.EchartsAreaNameToGov(item.getHamlet());
	               	
	               	   //获得该地区地区编码前两位(省)
               	       provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
	               		provinceCode0to2 = provinceCode.substring(0,2);	
	               		//获得该地区地区编码前四位(市)
	               		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	               		cityCode0to4 = cityCode.substring(0,4);	
	            		
	            		//获得该地区地区编码前六位(县)
	               		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4).getDistrictcode();
	            		countyCode0to6 = countyCode.substring(0,6);	             		
	            		//获得该地区地区编码前九位(乡)
	            		villageCode = districtsDao.getCityAndBelowDistrictsByDistrictName(villageName, countyCode0to6).getDistrictcode();
	            		villageCode0to9 = villageCode.substring(0,9);	
	            		hamletCode = districtsDao.getCityAndBelowDistrictsByDistrictName(item.getHamlet(), villageCode0to9).getDistrictcode();
	            		sdlist = sheepdogsDao.getIndexInforByDistrictcode(hamletCode);
                       break;
               }

               Map<String, Object> maptemp = new HashMap<String,Object>();
               
               maptemp.put("username",item.username);
               maptemp.put("managername", item.managername+"11");
               maptemp.put("logintime", item.logintime);
               
               if (item.workplace.isEmpty())
               {
            	   maptemp.put("job", "----");
               }
               else
               {
            	   maptemp.put("job", item.getWorkplace());
               }
               
               if (item.officecall.isEmpty())
               {
            	   maptemp.put("officecall", "----");
               }
               else
               {
            	   maptemp.put("officecall",item.getOfficecall());
               }
               
               if (item.managertel.isEmpty())
               {
            	   maptemp.put("telphonecall", "----");
               }
               else
               {
            	   maptemp.put("telphonecall" , item.getManagertel());
               }
             //  maptemp.put("privilegelevel",item.getPrivilegelevel());
			   maptemp.put("managearea", manageareatemp);		
			
				//佩戴项圈牧犬数量和喂食器数量
				int neckletedtotal = 0;
				int feederedtotal = 0;
				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckletedtotal++;
					}
					//"-1"表示无喂食器
					if(!each.getApparatusid().equals("-1")) {
						feederedtotal++;
					}
				}
				maptemp.put("dogtotalnum", sdlist.size());
				maptemp.put("neckletedtotal", neckletedtotal);
				maptemp.put("feederedtotal", feederedtotal);
				data.put(""+i, maptemp);				
				 i++;	 
              
           }
		//每页管理员信息
		map.put("data", data);
	
		//管理员总数
		map.put("totalNum", page.getTotal());
		return map;
	}
	@Override
	public Map<String, Object> getVillageManagersList(String districtcode) {
		Map<String, Object> map = new HashMap<String, Object>();
		String villagecode0to9 = districtcode.substring(0, 9);
		List<Dogowners> dogownersList = dogownersDao.getDogownersList(villagecode0to9);
		int i=0;
		 for (Dogowners item:dogownersList)
         {
			 Map<String, Object> maptemp = new HashMap<String, Object>();
			 maptemp.put("ownerid", item.getOwnerid());
			 maptemp.put("ownername", item.getOwnername());
			 map.put(""+i, maptemp);
			 i++;
         }
		return map;
	}
	@Override
	public Map<String, Object> getNecksList(String username) {
		 Map<String, Object> map = new HashMap<String, Object>();

			List<Necklet> neckletsList = neckletDao.getNeckletsList(username);
			int i=0;
			 for (Necklet item:neckletsList)
	         {
				 Map<String, Object> maptemp = new HashMap<String, Object>();
				 maptemp.put("necid", item.getId());
				 maptemp.put("neckletid", item.getNeckletid());
				 map.put(""+i, maptemp);
				 i++;
	         }
			return map;
	}
	@Override
	public Map<String, Object> getFeedersList(String username) {
		 Map<String, Object> map = new HashMap<String, Object>();
			 
			List<Feeder> feedersList = feederDao.getFeedersList(username);
			
			int i=0;
			 for (Feeder item:feedersList)
	         {
				 Map<String, Object> maptemp = new HashMap<String, Object>();
				 maptemp.put("id", item.getId());
				 maptemp.put("feederid", item.getApparatusid());
				 map.put(""+i, maptemp);
				 i++;
	         }
			return map;
	}
	/**
	 * 添加主人
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String addOwer(String ownername, String owneridentity, String ownersex, String ownerhamletcode, int ownerage,
			String ownerjob, String homeaddress, String telphone) throws Exception {
		String result = "添加失败";
		// 如果主人存在，则无法再次创建
		if (dogownersDao.getOwnerByName(ownername)!=null) {
			result = "添加失败，主人已经存在！";
		}	
	 
		Dogowners owner = new Dogowners();
		owner.setOwnername(ownername);
		owner.setOwnertelphone(telphone);
		owner.setOwneraddress(homeaddress);
		owner.setOwnerstatus(1);
		owner.setOwnerretiretime("");
		owner.setOwneridentity(owneridentity);
		owner.setOwnersex(ownersex);
		owner.setOwnerage(ownerage);
		owner.setOwnerjob(ownerjob);
		owner.setDistrictcode(ownerhamletcode);		
		dogownersDao.addOwner(owner);
		result = "主人添加成功！";
		return result;
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String addNecklet(String neckletid, int medtotal, String category, String username) throws Exception {
		String result = "添加失败";
		// 如果项圈存在，则无法再次创建
		if (neckletDao.getNeckletByNeckletid(neckletid)!=null) {
			result = "添加失败，项圈已经存在！";
		}	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		Necklet necklet = new Necklet();
		necklet.setNeckletid(neckletid);
		necklet.setRetiretime("");

		necklet.setLogintime(ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString()));
        necklet.setCategory(category);
        necklet.setDogid(-1);
        necklet.setUsername(username);
        necklet.setMedtotal(medtotal);
        neckletDao.addNecklet(necklet);
        //除了项圈表，以下四个关联表也要添加数据
		Neckletrealtime neckletrealtime = new Neckletrealtime();
		neckletrealtime.setRealtime(nowtime);
    	neckletrealtime.setNeckletid(neckletid);
    	neckletrealtime.setNeckletpower("1.0");
    	neckletrealtime.setNeckletlongitude("121.445274");
    	neckletrealtime.setNeckletvdoing("31.130922");
    	neckletrealtime.setNecklethealthy(1);
    	neckletrealtime.setNeckletbug("0");
    	neckletrealtimeDao.addNeckletrealtime(neckletrealtime);
    	
    	Lastneckletrealtime lastneckletrealtime = new Lastneckletrealtime();
    	lastneckletrealtime.setRealtime(nowtime);
    	lastneckletrealtime.setNeckletid(neckletid);
    	lastneckletrealtime.setNeckletpower("1.0");
    	lastneckletrealtime.setNeckletlongitude("121.32");
    	lastneckletrealtime.setNeckletvdoing("40.21");
    	lastneckletrealtime.setNecklethealthy(1);
    	lastneckletrealtime.setNeckletbug("0");
    	lastneckletrealtime.setDistrictcode(-1);
    	lastneckletrealtimeDao.addLastneckletrealtime(lastneckletrealtime);
    	
    	Lastexhibitrealtime lastexhibitrealtime = new Lastexhibitrealtime();
    	lastexhibitrealtime.setRealtime(nowtime);
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.SECOND, 2592000);
    	Date nextexhibittime = cal.getTime();
    	lastexhibitrealtime.setNextexhibittime(ChangeTimeFormat.changeTimeFormat(df.format(nextexhibittime).toString()));
    	lastexhibitrealtime.setNeckletid(neckletid);
    	lastexhibitrealtime.setDistrictcode(-1);
    	lastexhibitrealtime.setExhibitlongitude("121.32");
    	lastexhibitrealtime.setExhibitvdoing("40.21");
    	lastexhibitrealtime.setTableremain(12);
    	lastexhibitrealtimeDao.addLastexhibitrealtime(lastexhibitrealtime);
    	
    	Feedback feedback = new Feedback();
    	feedback.setExhibitcycle("43200");
    	feedback.setNeckletid(neckletid);
    	feedback.setFeedcycle("21600");
    	feedback.setUpdatetime(nowtime);
    	feedback.setMedtotal(12);
    	feedback.setFirstmedtime(nowtime);
    	cal.add(Calendar.YEAR, 1);
    	Date endmedtime = cal.getTime();
    	feedback.setEndmedtime(ChangeTimeFormat.changeTimeFormat(df.format(endmedtime).toString()));
    	feedbackDao.addFeedback(feedback);
		 
        result = "项圈添加成功！";
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String addFeeder(String apparatusid, int medtotal, String category, String username) throws Exception {
		String result = "添加失败";
		// 如果喂食器存在，则无法再次创建
		if (feederDao.getFeederByFeederid(apparatusid)!=null) {
			result = "添加失败，喂食器已经存在！";
		}	
		
		Feeder feeder = new Feeder();
		feeder.setApparatusid(apparatusid);
		feeder.setRetiretime("");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		feeder.setLogintime(nowtime);
		feeder.setCategory(category);
		feeder.setDogid(-1);
		feeder.setUsername(username);
		feeder.setMedtotal(medtotal);
		feeder.setApparatus("良好");
		feeder.setApparatushealthy(0);
		feeder.setDistrictcode(-1);
		feederDao.addFeeder(feeder);
		//除了喂食器表，以下四个关联表也要添加数据
		Apparatusrealtime apparatusrealtime = new Apparatusrealtime();
		apparatusrealtime.setApparatusbug("NO");
		apparatusrealtime.setApparatushealthy(0);
		apparatusrealtime.setApparatusid(apparatusid);
		apparatusrealtime.setApparatuslongitude("121.32");
		apparatusrealtime.setApparatusvdoing("40.21");
		apparatusrealtime.setFeederrealtime(nowtime);
		apparatusrealtime.setApparatuspower("1.0");
		apparatusrealtimeDao.addApparatusrealtime(apparatusrealtime);
		
		Lastapparatusrealtime lastapparatusrealtime = new Lastapparatusrealtime();
		lastapparatusrealtime.setApparatusbug("NO");
		lastapparatusrealtime.setApparatushealthy(0);
		lastapparatusrealtime.setApparatusid(apparatusid);
		lastapparatusrealtime.setApparatuslongitude("121.32");
		lastapparatusrealtime.setApparatusvdoing("40.21");
		lastapparatusrealtime.setFeederrealtime(nowtime);
		lastapparatusrealtime.setApparatuspower("1.0");
		lastapparatusrealtime.setDistrictcode(-1);
		lastapparatusrealtimeDao.addLastapparatusrealtime(lastapparatusrealtime);
		
		Lastappexhibitrealtime lastappexhibitrealtime = new Lastappexhibitrealtime();
		lastappexhibitrealtime.setRealtime(nowtime);
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.SECOND, 2592000);
    	Date nextexhibittime = cal.getTime();
    	lastappexhibitrealtime.setNextexhibittime(ChangeTimeFormat.changeTimeFormat(df.format(nextexhibittime).toString()));
    	lastappexhibitrealtime.setApparatusid(apparatusid);
    	lastappexhibitrealtime.setDistrictcode(-1);
    	lastappexhibitrealtime.setExhibitlongitude("121.32");
    	lastappexhibitrealtime.setExhibitvdoing("40.21");
    	lastappexhibitrealtime.setTableremain(12);
    	lastappexhibitrealtime.setApparatuspower("1.0");
    	lastappexhibitrealtimeDao.addLastappexhibitrealtime(lastappexhibitrealtime);
     
		Feederback feederback = new Feederback();
		feederback.setExhibitcycle("43200");
		feederback.setApparatusid(apparatusid);
		feederback.setFeedercycle("21600");;
		feederback.setUpdatetime(nowtime);
		feederback.setMedtotal(12);
		feederback.setFirstmedtime(nowtime);
    	cal.add(Calendar.YEAR, 1);
    	Date endmedtime = cal.getTime();
    	feederback.setEndmedtime(ChangeTimeFormat.changeTimeFormat(df.format(endmedtime).toString()));
    	feederbackDao.addFeederback(feederback);		
	
		result = "喂食器添加成功！";    
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String bindFeeder(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid,
			String dogweight, String dogcolor, String dogage, String dogfeederid) throws Exception {
		String result = "绑定失败";
		
		Sheepdogs sheepdog = new Sheepdogs();
		sheepdog.setDogname(dogname);
		sheepdog.setNeckletid("-1");
		sheepdog.setApparatusid(dogfeederid.equals("")?"-1":dogfeederid);
		sheepdog.setBelonghamlet(dogbelonghamlet);
		sheepdog.setUsername(username);
		sheepdog.setManagername(managersDao.getManagerByName(username).getManagername());
		sheepdog.setDogownerid(Integer.parseInt(dogownerid));
		sheepdog.setDogweight(dogweight);
		sheepdog.setDogcolor(dogcolor);
		sheepdog.setDogage(dogage);
		sheepdog.setDoginfo("健康");
		sheepdog.setDogstatus(1);
		sheepdog.setDogretirtime("");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		sheepdog.setLogintime(nowtime);
		sheepdog.setDogsex(dogsex);
		sheepdog.setDistrictcode(ownerhamletcode);
		
		sheepdogsDao.addSheepDog(sheepdog);
		
		if(dogfeederid != "") {
			Feeder feeder = feederDao.getFeederByFeederid(dogfeederid);
			if(feeder != null) {
				
				feeder.setDogid(sheepdogsDao.getLastId());
				feederDao.updateFeeder(feeder);
			}
				    
		    Lastappexhibitrealtime lastappexhibitrealtime = lastappexhibitrealtimeDao.getLastappexhibitrealtime(dogfeederid);
		    if(lastappexhibitrealtime != null) {
		    	lastappexhibitrealtime.setDistrictcode(Long.valueOf(ownerhamletcode));
		    	lastappexhibitrealtimeDao.updateLastappexhibitrealtime(lastappexhibitrealtime);
		    }
		    
			Lastapparatusrealtime lastapparatusrealtime = lastapparatusrealtimeDao.getLastapparatusrealtime(dogfeederid);
		    if(lastapparatusrealtime != null) {
		    	lastapparatusrealtime.setDistrictcode(Long.valueOf(ownerhamletcode));
		    	lastapparatusrealtimeDao.updateLastapparatusrealtime(lastapparatusrealtime);
		    }
		}
			
		result = "success";  
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String bindNecklet(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid,
			String dogweight, String dogcolor, String dogage, String dogneckletid) throws Exception {
		String result = "绑定失败";
		
		Sheepdogs sheepdog = new Sheepdogs();
		sheepdog.setDogname(dogname);
		sheepdog.setNeckletid(dogneckletid.equals("")?"-1":dogneckletid);
		sheepdog.setApparatusid("-1");
		sheepdog.setBelonghamlet(dogbelonghamlet);
		sheepdog.setUsername(username);
		sheepdog.setManagername(managersDao.getManagerByName(username).getManagername());
		sheepdog.setDogownerid(Integer.parseInt(dogownerid));
		sheepdog.setDogweight(dogweight);
		sheepdog.setDogcolor(dogcolor);
		sheepdog.setDogage(dogage);
		sheepdog.setDoginfo("健康");
		sheepdog.setDogstatus(1);
		sheepdog.setDogretirtime("");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		sheepdog.setLogintime(nowtime);
		sheepdog.setDogsex(dogsex);
		sheepdog.setDistrictcode(ownerhamletcode);
		
		sheepdogsDao.addSheepDog(sheepdog);
		
		if(dogneckletid != "") {
			Necklet necklet = neckletDao.getNeckletByNeckletid(dogneckletid);
			if(necklet != null) {
				
				necklet.setDogid(sheepdogsDao.getLastId());
				neckletDao.updateNecklet(necklet);
			}
			
			Lastexhibitrealtime lastexhibitrealtime = lastexhibitrealtimeDao.getLastexhibitrealtime(dogneckletid);
		    if(lastexhibitrealtime != null) {
		    	lastexhibitrealtime.setDistrictcode(Long.valueOf(ownerhamletcode));
		    	lastexhibitrealtimeDao.updateLastexhibitrealtime(lastexhibitrealtime);
		    }
		    
		    Lastneckletrealtime lastneckletrealtime = lastneckletrealtimeDao.getLastneckletrealtime(dogneckletid);
		    if(lastneckletrealtime != null) {
		    	lastneckletrealtime.setDistrictcode(Long.valueOf(ownerhamletcode));
		    	lastneckletrealtimeDao.updateLastneckletrealtime(lastneckletrealtime);
		    }
		}
			
		result = "success";  
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String addDog(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid,
			String dogweight, String dogcolor, String dogage) throws Exception {
		String result = "添加失败";
		
		Sheepdogs sheepdog = new Sheepdogs();
		sheepdog.setDogname(dogname);
		sheepdog.setNeckletid("-1");
		sheepdog.setApparatusid("-1");
		sheepdog.setBelonghamlet(dogbelonghamlet);
		sheepdog.setUsername(username);
		sheepdog.setManagername(managersDao.getManagerByName(username).getManagername());
		sheepdog.setDogownerid(Integer.parseInt(dogownerid));
		sheepdog.setDogweight(dogweight);
		sheepdog.setDogcolor(dogcolor);
		sheepdog.setDogage(dogage);
		sheepdog.setDoginfo("健康");
		sheepdog.setDogstatus(1);
		sheepdog.setDogretirtime("");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		sheepdog.setLogintime(nowtime);
		sheepdog.setDogsex(dogsex);
		sheepdog.setDistrictcode(ownerhamletcode);
		
		sheepdogsDao.addSheepDog(sheepdog);
			
		result = "牧犬添加成功！";  
		return result;
	}
	@Override
	public Map<String, Object> getDogInfo(int dogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Sheepdogs sheepdogs = sheepdogsDao.getSheepdogbyDogId(dogId);
		if(sheepdogs == null)
			return null;
		map.put("dogid", dogId);
		map.put("dogname", sheepdogs.getDogname());
		map.put("dogsex", sheepdogs.getDogsex());
		map.put("belonghamlet", sheepdogs.getBelonghamlet());

		map.put("ownername", dogownersDao.getOwnerById(sheepdogs.getDogownerid()).getOwnername());
		map.put("dogweight", sheepdogs.getDogweight());
		map.put("dogcolor", sheepdogs.getDogcolor());
		map.put("dogage", sheepdogs.getDogage()==null?"3":sheepdogs.getDogage());
		if(sheepdogs.getNeckletid() == null || sheepdogs.getNeckletid().equals("-1")) {
			map.put("neckletid", "");
			map.put("necid", -1);
		}else {
			map.put("neckletid", sheepdogs.getNeckletid());
			map.put("necid", neckletDao.getNeckletByNeckletid(sheepdogs.getNeckletid()).getId());		
		}
		if(sheepdogs.getApparatusid() == null || sheepdogs.getApparatusid().equals("-1")) {
			map.put("feederid", "");
			map.put("fid", -1);
		}else {
			map.put("feederid", sheepdogs.getApparatusid());

			map.put("fid", feederDao.getFeederByFeederid(sheepdogs.getApparatusid()).getId());		
		}
		
		map.put("adminname", sheepdogs.getManagername());
		map.put("adminphone", managersDao.getManagerByName(sheepdogs.getUsername()).getManagertel());
		return map;
	}
	@Override
	public Map<String, Object> getDogOwnerInfo(int dogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		long dogOwnerId = sheepdogsDao.getSheepdogbyDogId(dogId).getDogownerid();
		Dogowners dogowners = dogownersDao.getOwnerById(dogOwnerId);
		if(dogowners !=null) {
			map.put("ownerid", dogowners.getOwnerid());
			map.put("ownername", dogowners.getOwnername());
			map.put("owneridentity", dogowners.getOwneridentity());
			map.put("ownersex", dogowners.getOwnersex());
			map.put("ownerage", dogowners.getOwnerage());
			map.put("ownerjob", dogowners.getOwnerjob());
			map.put("homeaddress", dogowners.getOwneraddress());
			map.put("telphone", dogowners.getOwnertelphone());
	 
			
		}
		return map;
	}
	@Override
	public Map<String, Object> getDogNeckletInfo(int dogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String dogNeckletId = sheepdogsDao.getSheepdogbyDogId(dogId).getNeckletid();
		if(dogNeckletId == null|| dogNeckletId.equals("-1")) {
			 map.put("neckletid", "----");
			 map.put("powerleft", "----");
			 map.put("medtotal", 0);
			 map.put("medleft", -1);
			 map.put("endmedtime", "");
			 map.put("areacycle", "----");
			 map.put("exhibitcycle", "----");
			 map.put("firstmedtime", "----");
			 map.put("lastmedtime", "----");
			 map.put("lastremindmedtime", "----");
       
		}else {
			String powerleft = lastneckletrealtimeDao.getLastneckletrealtime(dogNeckletId).getNeckletpower();
			Feedback neckletinfo2 = feedbackDao.getFeedback(dogNeckletId);
			Lastexhibitrealtime med = lastexhibitrealtimeDao.getLastexhibitrealtime(dogNeckletId);

			int medleft = med.getTableremain();
			
			if(neckletinfo2 != null) {
				 map.put("neckletid", dogNeckletId);
				 map.put("powerleft", powerleft);
				 map.put("medtotal", neckletinfo2.getMedtotal());
				 map.put("medleft", medleft);
				 map.put("endmedtime", neckletinfo2.getEndmedtime());
				 map.put("areacycle",Double.parseDouble(neckletinfo2.getFeedcycle())/1440);
				 map.put("exhibitcycle",Double.parseDouble(neckletinfo2.getExhibitcycle())/1440);
				 map.put("firstmedtime", neckletinfo2.getFirstmedtime());
				 map.put("lastmedtime", med.getRealtime());
				 map.put("lastremindmedtime", med.getRealtime());
			}
		}
		return map;
	}
	@Override
	public Map<String, Object> getDogFeederInfo(int dogId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String dogFeederId = sheepdogsDao.getSheepdogbyDogId(dogId).getApparatusid();
		if(dogFeederId == null|| dogFeederId.equals("-1") ){
			 map.put("feederid", "----");
			 map.put("powerleft", "----");
			 map.put("medtotal", 0);
			 map.put("medleft", -1);
			 map.put("endmedtime", "");
			 map.put("areacycle", "----");
			 map.put("exhibitcycle", "----");
			 map.put("firstmedtime", "----");
			 map.put("lastmedtime", "----");
			 map.put("lastremindmedtime", "----");
       
		}else {
			String powerleft = lastapparatusrealtimeDao.getLastapparatusrealtime(dogFeederId).getApparatuspower();
			Feederback feederinfo2 = feederbackDao.getFeederback(dogFeederId);
			Lastappexhibitrealtime med = lastappexhibitrealtimeDao.getLastappexhibitrealtime(dogFeederId);
			int medleft = med.getTableremain();
			
			if(feederinfo2 != null) {
				 map.put("feederid", dogFeederId);
				 map.put("powerleft", Double.parseDouble(powerleft)/10);
				 map.put("medtotal", feederinfo2.getMedtotal());
				 map.put("medleft", medleft);
				 map.put("endmedtime", feederinfo2.getEndmedtime());
				 
				 map.put("areacycle",Double.parseDouble(feederinfo2.getFeedercycle())/1440);
				 map.put("exhibitcycle",Double.parseDouble(feederinfo2.getExhibitcycle())/1440);
				 map.put("firstmedtime", feederinfo2.getFirstmedtime());
				 map.put("lastmedtime", med.getRealtime());
				 map.put("lastremindmedtime", med.getRealtime());
			}
		}
		return map;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String modifyNecklet(String neckletid, String power, String medtotal, String medleft, String areacycle,
			String exhibitcycle, String firstmedtime) throws Exception {
		String result = "项圈信息修改失败";
		Feedback feedback = feedbackDao.getFeedback(neckletid);
		Lastexhibitrealtime lastexhibitrealtime = lastexhibitrealtimeDao.getLastexhibitrealtime(neckletid);
		feedback.setMedtotal(Integer.valueOf(medtotal));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		feedback.setUpdatetime(nowtime);
		feedback.setFeedcycle(String.valueOf(Math.round(Double.valueOf(areacycle)*1440)));
		feedback.setExhibitcycle(String.valueOf((int)Math.round(Double.valueOf(exhibitcycle)*1440)));
		feedback.setFirstmedtime(ChangeTimeFormat.changeTimeFormat(firstmedtime));
		
		Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1970, 0, 01, 8,0);//月份0表示一月
        Date startTime = calendar.getTime();
        long firsttimestamp = (df.parse(firstmedtime).getTime() - startTime.getTime())/1000;    
        calendar.add(Calendar.SECOND, (int)firsttimestamp + Integer.parseInt(medtotal) * (int)(Math.round(Double.valueOf(exhibitcycle)*1440*60)));
        feedback.setEndmedtime(ChangeTimeFormat.changeTimeFormat(df.format(calendar.getTime()).toString()));		
        feedbackDao.updateFeedback(feedback);
        
        lastexhibitrealtime.setTableremain(Integer.valueOf(medleft));
        lastexhibitrealtime.setRealtime(feedback.getUpdatetime());
        lastexhibitrealtime.setNextexhibittime(feedback.getFirstmedtime());
        lastexhibitrealtime.setNeckletid(neckletid);
        lastexhibitrealtimeDao.updateLastexhibitrealtime2(lastexhibitrealtime);
        
        result = "项圈信息修改成功";
        return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String modifyFeeder(String feederid, String power, String medtotal, String medleft, String areacycle,
			String exhibitcycle, String firstmedtime) throws Exception {
		String result = "喂食器信息修改失败";
		Feederback feederback = feederbackDao.getFeederback(feederid);
		Lastappexhibitrealtime lastappexhibitrealtime = lastappexhibitrealtimeDao.getLastappexhibitrealtime(feederid);
		feederback.setMedtotal(Integer.valueOf(medtotal));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString());
		feederback.setUpdatetime(nowtime);
		feederback.setFeedercycle(String.valueOf(Math.round(Double.valueOf(areacycle)*1440)));
		feederback.setExhibitcycle(String.valueOf((int)Math.round(Double.valueOf(exhibitcycle)*1440)));
		feederback.setFirstmedtime(ChangeTimeFormat.changeTimeFormat(firstmedtime));
		
		Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1970, 0, 01, 8,0);//月份0表示一月
        Date startTime = calendar.getTime();
        long firsttimestamp = (df.parse(firstmedtime).getTime() - startTime.getTime())/1000;    
        calendar.add(Calendar.SECOND, (int)firsttimestamp + Integer.parseInt(medtotal) * (int)(Math.round(Double.valueOf(exhibitcycle)*1440*60)));
        feederback.setEndmedtime(ChangeTimeFormat.changeTimeFormat(df.format(calendar.getTime()).toString()));	
        feederbackDao.updateFeederback(feederback);
        
        lastappexhibitrealtime.setTableremain(Integer.valueOf(medleft));
        lastappexhibitrealtime.setApparatuspower(power);
        lastappexhibitrealtime.setApparatusid(feederid);
        lastappexhibitrealtimeDao.updateLastappexhibitrealtime2(lastappexhibitrealtime);
        result = "喂食器信息修改成功";
        return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String modifyOwner(String ownerid, String ownername, String owneridentity, String ownersex, String ownerage,
			String ownerjob, String homeaddress, String telphone) throws Exception {
		String result = "主人信息修改失败";
		Dogowners dogowners = dogownersDao.getOwnerById(Long.valueOf(ownerid));
		dogowners.setOwnerid(Long.valueOf(ownerid));
		dogowners.setOwnername(ownername);
		dogowners.setOwneridentity(owneridentity);
		dogowners.setOwnersex(ownersex);
		dogowners.setOwnerage(Integer.valueOf(ownerage));
		dogowners.setOwnerjob(ownerjob);
		dogowners.setOwneraddress(homeaddress);
		dogowners.setOwnertelphone(telphone);
		dogownersDao.updateOwner(dogowners);
		
		result = "主人信息修改成功";
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String modifyDog(String username, String dogid, String dogname, String dogsex, String dogbelonghamlet,
			String districtcode, String dogownerid, String dogweight, String dogcolor, String dogage,
			String dogneckletid, String dogfeederid)  throws Exception {
		String result = "牧犬信息修改失败";
		Sheepdogs sheepdogs = sheepdogsDao.getSheepdogbyDogId(Integer.valueOf(dogid));
		//sheepdogs.setDogid(Integer.valueOf(dogid));
		sheepdogs.setDogname(dogname);
		sheepdogs.setDogsex(dogsex);
		sheepdogs.setBelonghamlet(dogbelonghamlet);
		sheepdogs.setDogownerid(Integer.valueOf(dogownerid));
        sheepdogs.setDogweight(dogweight);
        sheepdogs.setDogcolor(dogcolor);
        sheepdogs.setDogage(dogage);
        sheepdogs.setNeckletid(dogneckletid==""?"-1":dogneckletid);
        sheepdogs.setDistrictcode(districtcode);
        sheepdogs.setApparatusid(dogfeederid == "" ? "-1" : dogfeederid);
        sheepdogsDao.updateSheepDog(sheepdogs);
        
        if(dogneckletid != "") {
        	//旧项圈去掉dogid
        	Necklet originnecklet = neckletDao.getNeckletByDogid(dogid);
        	if(originnecklet != null && originnecklet.getNeckletid() != dogneckletid) {
        		originnecklet.setDogid(-1);
        		neckletDao.updateNecklet(originnecklet);

        	//新项圈添加dogid
        	Necklet thisnecklet = neckletDao.getNeckletByNeckletid(dogneckletid);
        	thisnecklet.setDogid(Integer.valueOf(dogid));
        	neckletDao.updateNecklet(thisnecklet);
        	
        	Lastexhibitrealtime lastexhibitrealtime = lastexhibitrealtimeDao.getLastexhibitrealtime(dogneckletid);
        	lastexhibitrealtime.setDistrictcode(Long.valueOf(sheepdogs.getDistrictcode()));
        	lastexhibitrealtimeDao.updateLastexhibitrealtime(lastexhibitrealtime);
        	
        	Lastneckletrealtime lastneckletrealtime = lastneckletrealtimeDao.getLastneckletrealtime(dogneckletid);
        	lastneckletrealtime.setDistrictcode(Long.valueOf(sheepdogs.getDistrictcode()));
        	lastneckletrealtimeDao.updateLastneckletrealtime(lastneckletrealtime);
        	}
        }
        
        if(dogfeederid != "" ) {
        	//旧喂食器去掉dogid
        	Feeder originfeeder = feederDao.getFeederByDogrid(dogid);
        
        	if(originfeeder != null && originfeeder.getApparatusid() != dogfeederid) {
        		originfeeder.setDogid(-1);
        		feederDao.updateFeeder(originfeeder);

        	//新喂食器添加dogid
        	Feeder thisnecklet = feederDao.getFeederByFeederid(dogfeederid);
        	thisnecklet.setDogid(Integer.valueOf(dogid));
        	feederDao.updateFeeder(thisnecklet);
        	
        	Lastappexhibitrealtime lastappexhibitrealtime  = lastappexhibitrealtimeDao.getLastappexhibitrealtime(dogfeederid);
        	lastappexhibitrealtime.setDistrictcode(Long.valueOf(sheepdogs.getDistrictcode()));
        	lastappexhibitrealtimeDao.updateLastappexhibitrealtime(lastappexhibitrealtime);
        	
        	Lastapparatusrealtime lastneckletrealtime = lastapparatusrealtimeDao.getLastapparatusrealtime(dogfeederid);
        	lastneckletrealtime.setDistrictcode(Long.valueOf(sheepdogs.getDistrictcode()));
        	lastapparatusrealtimeDao.updateLastapparatusrealtime(lastneckletrealtime);
        	}
        }
        if(dogfeederid == "" && dogneckletid == "") {
        	Necklet originnecklet = neckletDao.getNeckletByDogid(dogid);
    		originnecklet.setDogid(-1);
    		neckletDao.updateNecklet(originnecklet);
        
        	Feeder originfeeder = feederDao.getFeederByFeederid(dogid);
    		originfeeder.setDogid(-1);
    		feederDao.updateFeeder(originfeeder);
        	
        }
		
		result = "牧犬信息修改成功";
		
		return result;
	}	

}
