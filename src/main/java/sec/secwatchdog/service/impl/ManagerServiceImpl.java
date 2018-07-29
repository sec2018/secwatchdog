package sec.secwatchdog.service.impl;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int getNextLevelAdminInfoTotalNum(String managerName) {
		Managers manager = managersDao.getManagerByName(managerName);
		int totalNum = 0;
		switch(manager.getPrivilegelevel()) {
			case 1:
				totalNum = managersDao.getManagersByPrivilegelevel(2).size();			 
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
				
		}
		return totalNum;
	}
	@Override
	public Map<String, Object> getNextLevelAdminInfo(String managerName, int startItem, int pageSize) {
		Managers manager = managersDao.getManagerByName(managerName);
		Map<String, Object> map = new HashMap<String,Object>();
		List<Managers> magagers = new ArrayList<Managers>();
		int i = 0;
		PageHelper.startPage(startItem, pageSize);
		switch(manager.getPrivilegelevel()) {
			case 1:
				magagers = managersDao.getManagersByPrivilegelevel(2);
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
					maptemp.put("managearea", magager.getProvince());
					//maptemp.put("upmanageraera", "");
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
					//maptemp.put("privilegelevel", magager.getPrivilegelevel());
					map.put(""+i, maptemp);
					
					i++;
					
				}
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
				
		}
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
	                 
	                break;
	            case 3:
	                
	                break;
	            case 4:
	                 
	                break;
	            case 5:
	                 
	                break;
	            case 6:
	                
	                break;
	            case 7:
	                
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
	public int getSearchByAdminInfoTotal(String userName, String managerName, int startItem, int pageSize) {
		Managers manager = managersDao.getManagerByName(userName);
		int totalNum = 0;
		PageHelper.startPage(startItem, pageSize);
		switch(manager.getPrivilegelevel()) {
			case 1:
				totalNum = managersDao.getAllManagersByManagerName(manager.getPrivilegelevel(),managerName).size();
				break;
			 case 2:
                 break;
             case 3:
                  break;
             case 4:
                 break;
             case 5:
                break;
		}
		return totalNum;
	}
	@Override
	public Map<String, Object>  getSearchByAdminInfo(String userName, String managerName,int startItem,int pageSize) {//managername可能是姓名、区域或手机号码
		
		Managers manager = managersDao.getManagerByName(userName);
		Map<String, Object> map = new HashMap<String,Object>();
		List<Managers> magagers = new ArrayList<Managers>();
		int i=0;
		PageHelper.startPage(startItem, pageSize);
		switch(manager.getPrivilegelevel()) {
			case 1:
			    magagers = managersDao.getAllManagersByManagerName(manager.getPrivilegelevel(),managerName);
			    break;
			 case 2:
                 break;
             case 3:
                  break;
             case 4:
                 break;
             case 5:
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
             /*  if (!item.managername.contains(managerName) && !manageareatemp.contains(managerName) && !item.managertel.contains(managerName))
               {
                   continue;
               }*/
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
				map.put(""+i, maptemp);				
				 i++;

//               if (item.getPrivilegelevel()==2)
//               {
//                  
//               }
//               else if (item.getPrivilegelevel()==3)
//               {
//                  
//               }
//               else if (item.getPrivilegelevel()==4)
//               {
//                 
//               }
//               else if (item.getPrivilegelevel()==5)
//               {
//                  
//            	 
//               }
//               else if (item.getPrivilegelevel()==6)
//               { 
//               }
				 
              
           }
		return map;
	}


	
	
	@Override
	public Map<String, Object>  getSearchByAdminInfo(String privilegeLevel, String userName, String managerName) {
		// TODO Auto-generated method stub
		return null;
	}
 

	

}
