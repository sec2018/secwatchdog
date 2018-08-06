package sec.secwatchdog.service.impl;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.ManageService;
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
				System.err.println(thismanager.getVillage());
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
		Page page = PageHelper.startPage(startItem, pageSize);
		System.out.println(districtcodetemp.length());
		System.out.println(districtcodetemp);
        switch(districtcodetemp.length()) {
        	case 2:
        		districtInfo.put("districtlevel", district.getDistrictlevel());
        		districtInfo.put("province", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 2)+"0000000000"));
        		districtInfo.put("districtcode", districtcode);
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
				System.out.println(districtcode);
				districtInfo.put("districtlevel", district.getDistrictlevel());
        		districtInfo.put("province", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 2)+"0000000000"));
        		districtInfo.put("city", districtsDao.getDistrictNameByDistrictCode(districtcode.substring(0, 4)+"00000000"));
        		districtInfo.put("districtcode", districtcode);
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
		System.out.println(districtlevel);
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

}
