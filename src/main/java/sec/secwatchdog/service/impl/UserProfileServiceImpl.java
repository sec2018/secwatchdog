package sec.secwatchdog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import sec.secwatchdog.mapper.AppexhibitrealtimeDao;
import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.ExhibitrealtimeDao;
import sec.secwatchdog.mapper.FeedbackDao;
import sec.secwatchdog.mapper.FeederbackDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Feedback;
import sec.secwatchdog.model.Feederback;
import sec.secwatchdog.model.Lastapparatusrealtime;
import sec.secwatchdog.model.Lastappexhibitrealtime;
import sec.secwatchdog.model.Lastexhibitrealtime;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.UserProfileService;
import sec.secwatchdog.util.AESUtil;
@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private ManagersDao managersDao;
	@Autowired
	private SheepdogsDao sheepdogsDao;
	@Autowired
	private DistrictsDao districtsDao;
	@Autowired
	private AppexhibitrealtimeDao appexhibitrealtimeDao;
	@Autowired
	private ExhibitrealtimeDao exhibitrealtimeDao;
	@Autowired
	private FeedbackDao feedbackDao;
	@Autowired
	private FeederbackDao feederbackDao;

	@Override
	public Map<String, Object> getUserProfile(String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		//被点击的管理员
		Managers manager = managersDao.getManagerByName(userName);
		List<Sheepdogs> sdlist = null;
		Districts districtsist = null;
		//佩戴项圈牧犬数量
		int neckdognumtotal = 0;
		String provinceCode,provinceCode0to2,cityCode,cityCode0to4,countyCode,countyCode0to6,villageCode,villageCode0to9,hamletCode;
		switch(manager.getPrivilegelevel()) {
		
			case 1:
			/*	map.put("username", manager.getUsername());
				map.put("managername", manager.getManagername());
				map.put("area", manager.getProvince());
				map.put("job", manager.getWorkplace());
				map.put("officecall", manager.getOfficecall());
				map.put("telphonecall", manager.getManagertel());
				map.put("useraddress", manager.getAddress());
				map.put("logintime", manager.getLogintime());
				
				map.put("dogtotalnum", manager.getUsername());
				//获得所有的狗
				List<Sheepdogs> sdlist = sheepdogsDao.getIndexInfor();
				//佩戴项圈牧犬数量
				int neckdognumtotal = 0;
				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckdognumtotal++;
					}			 
				}
				map.put("neckletedtotal", neckdognumtotal);
	            if (manager.getManagerstatus() == 0)
	            {
	            	map.put("adminstatus", "未激活");
	            }
	            else if (manager.getManagerstatus() == 1)
	            {
	            	map.put("adminstatus", "已激活");
	            }*/
				break;
			case 2:
				map.put("username", manager.getUsername());
				map.put("managername", manager.getManagername());
				map.put("privilegelevel", manager.getPrivilegelevel());
				map.put("area", manager.getProvince());
				map.put("job", manager.getWorkplace());
				map.put("officecall", manager.getOfficecall());
				map.put("telphonecall", manager.getManagertel());
				map.put("useraddress", manager.getAddress());
				map.put("logintime", manager.getLogintime());
				
				//获得该地区地区编码前两位(省)
				districtsist = districtsDao.getDistrictsByDistrictName(manager.getProvince());
				provinceCode = districtsist.getDistrictcode();
				provinceCode0to2 = provinceCode.substring(0,2);	
		        //获得该省所有的狗
				sdlist = sheepdogsDao.getIndexInforByDistrictcode(provinceCode0to2);
				map.put("dogtotalnum", sdlist.size());

				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckdognumtotal++;
					}			 
				}
				map.put("neckletedtotal", neckdognumtotal);
	            if (manager.getManagerstatus() == 0)
	            {
	            	map.put("adminstatus", "未激活");
	            }
	            else if (manager.getManagerstatus() == 1)
	            {
	            	map.put("adminstatus", "已激活");
	            }
				break;
			case 3:
				map.put("username", manager.getUsername());
				map.put("managername", manager.getManagername());
				map.put("privilegelevel", manager.getPrivilegelevel());
				map.put("area", manager.getProvince()+manager.getCity());
				map.put("job", manager.getWorkplace());
				map.put("officecall", manager.getOfficecall());
				map.put("telphonecall", manager.getManagertel());
				map.put("useraddress", manager.getAddress());
				map.put("logintime", manager.getLogintime());
				
				//获得该地区地区编码前两位(省)
				districtsist = districtsDao.getDistrictsByDistrictName(manager.getProvince());
				provinceCode = districtsist.getDistrictcode();
				provinceCode0to2 = provinceCode.substring(0,2);	
				//获得该地区地区编码前四位(市)
           		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCity(), provinceCode0to2).getDistrictcode();
           		cityCode0to4 = cityCode.substring(0,4);	
           		sdlist = sheepdogsDao.getIndexInforByDistrictcode(cityCode0to4);
           		map.put("dogtotalnum", sdlist.size());

				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckdognumtotal++;
					}			 
				}
				map.put("neckletedtotal", neckdognumtotal);
	            if (manager.getManagerstatus() == 0)
	            {
	            	map.put("adminstatus", "未激活");
	            }
	            else if (manager.getManagerstatus() == 1)
	            {
	            	map.put("adminstatus", "已激活");
	            }
				break;
				
			case 4:
				map.put("username", manager.getUsername());
				map.put("managername", manager.getManagername());
				map.put("privilegelevel", manager.getPrivilegelevel());
				map.put("area", manager.getProvince()+manager.getCity()+manager.getCounty());
				map.put("job", manager.getWorkplace());
				map.put("officecall", manager.getOfficecall());
				map.put("telphonecall", manager.getManagertel());
				map.put("useraddress", manager.getAddress());
				map.put("logintime", manager.getLogintime());
				
				//获得该地区地区编码前两位(省)
				districtsist = districtsDao.getDistrictsByDistrictName(manager.getProvince());
				provinceCode = districtsist.getDistrictcode();
				provinceCode0to2 = provinceCode.substring(0,2);	
				//获得该地区地区编码前四位(市)
           		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCity(), provinceCode0to2).getDistrictcode();
           		cityCode0to4 = cityCode.substring(0,4);	
        		
        		//获得该地区地区编码前六位(县)
           		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCounty(), cityCode0to4).getDistrictcode();
        		countyCode0to6 = countyCode.substring(0,6);	 
        		sdlist = sheepdogsDao.getIndexInforByDistrictcode(countyCode0to6);
        		map.put("dogtotalnum", sdlist.size());
				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckdognumtotal++;
					}			 
				}
				map.put("neckletedtotal", neckdognumtotal);
	            if (manager.getManagerstatus() == 0)
	            {
	            	map.put("adminstatus", "未激活");
	            }
	            else if (manager.getManagerstatus() == 1)
	            {
	            	map.put("adminstatus", "已激活");
	            }
				break;
			case 5:
				map.put("username", manager.getUsername());
				map.put("managername", manager.getManagername());
				map.put("privilegelevel", manager.getPrivilegelevel());
				map.put("area", manager.getProvince()+manager.getCity()+manager.getCounty()+manager.getVillage());
				map.put("job", manager.getWorkplace());
				map.put("officecall", manager.getOfficecall());
				map.put("telphonecall", manager.getManagertel());
				map.put("useraddress", manager.getAddress());
				map.put("logintime", manager.getLogintime());
				
				//获得该地区地区编码前两位(省)
				districtsist = districtsDao.getDistrictsByDistrictName(manager.getProvince());
				provinceCode = districtsist.getDistrictcode();
				provinceCode0to2 = provinceCode.substring(0,2);	
				//获得该地区地区编码前四位(市)
           		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCity(), provinceCode0to2).getDistrictcode();
           		cityCode0to4 = cityCode.substring(0,4);	
        		
        		//获得该地区地区编码前六位(县)
           		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCounty(), cityCode0to4).getDistrictcode();
        		countyCode0to6 = countyCode.substring(0,6);	 
        		//获得该地区地区编码前九位(乡)
        		villageCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getVillage(), countyCode0to6).getDistrictcode();
        		villageCode0to9 = villageCode.substring(0,9);	
        		sdlist = sheepdogsDao.getIndexInforByDistrictcode(villageCode0to9);
        		map.put("dogtotalnum", sdlist.size());
				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckdognumtotal++;
					}			 
				}
				map.put("neckletedtotal", neckdognumtotal);
	            if (manager.getManagerstatus() == 0)
	            {
	            	map.put("adminstatus", "未激活");
	            }
	            else if (manager.getManagerstatus() == 1)
	            {
	            	map.put("adminstatus", "已激活");
	            }
				break;
			case 6:
				map.put("username", manager.getUsername());
				map.put("managername", manager.getManagername());
				map.put("privilegelevel", manager.getPrivilegelevel());
				map.put("area", manager.getProvince()+manager.getCity()+manager.getCounty()+manager.getVillage()+manager.getHamlet());
				map.put("job", manager.getWorkplace());
				map.put("officecall", manager.getOfficecall());
				map.put("telphonecall", manager.getManagertel());
				map.put("useraddress", manager.getAddress());
				map.put("logintime", manager.getLogintime());
				
				//获得该地区地区编码前两位(省)
				districtsist = districtsDao.getDistrictsByDistrictName(manager.getProvince());
				provinceCode = districtsist.getDistrictcode();
				provinceCode0to2 = provinceCode.substring(0,2);	
				//获得该地区地区编码前四位(市)
           		cityCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCity(), provinceCode0to2).getDistrictcode();
           		cityCode0to4 = cityCode.substring(0,4);	
        		
        		//获得该地区地区编码前六位(县)
           		countyCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getCounty(), cityCode0to4).getDistrictcode();
        		countyCode0to6 = countyCode.substring(0,6);	             		
        		//获得该地区地区编码前九位(乡)
        		villageCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getVillage(), countyCode0to6).getDistrictcode();
        		villageCode0to9 = villageCode.substring(0,9);	
        		
        		hamletCode = districtsDao.getCityAndBelowDistrictsByDistrictName(manager.getHamlet(), villageCode0to9).getDistrictcode();
        		sdlist = sheepdogsDao.getIndexInforByDistrictcode(hamletCode);
        		map.put("dogtotalnum", sdlist.size());
				for(Sheepdogs each:sdlist){
					//"-1"表示未佩戴项圈
					if(!each.getNeckletid().equals("-1")) {
						neckdognumtotal++;
					}			 
				}
				map.put("neckletedtotal", neckdognumtotal);
	            if (manager.getManagerstatus() == 0)
	            {
	            	map.put("adminstatus", "未激活");
	            }
	            else if (manager.getManagerstatus() == 1)
	            {
	            	map.put("adminstatus", "已激活");
	            }
				break;	
			}	
			return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String rebackPwd(String userName, String rebackUserName) throws Exception{
		String result = "重置失败!";
		 List<Managers> userAll = managersDao.getUserAndRebackUser(userName,rebackUserName);
		 if (userAll.size() != 2)
        {
            return result;
        }
        else
        {
       	 Managers user = userAll.get(0).getUsername().equals(userName)?userAll.get(0):userAll.get(1);
       	 Managers rebackUser=userAll.get(0).getUsername().equals(rebackUserName)?userAll.get(0):userAll.get(1);

       	 if (user.getPrivilegelevel() + 1 != rebackUser.getPrivilegelevel())
            {
                return "00";//您只能重置下一级管理员密码
            }
       	 String password = "admin";
     	 AESUtil aes = new AESUtil();
	     managersDao.rebackManager(rebackUserName,aes.encryptData(password));
         result = "11";//重置成功		 
        }
	return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String activeUser(String username, String activeUsername) throws Exception {
		String result = "激活失败";
		 List<Managers> userAll = managersDao.getUserAndActiveUser(username,activeUsername);
		 if (userAll.size() != 2)
        {
            return result;
        }
        else
        {
       	 Managers user = userAll.get(0).getUsername().equals(username)?userAll.get(0):userAll.get(1);
       	 Managers activeUser=userAll.get(0).getUsername().equals(activeUsername)?userAll.get(0):userAll.get(1);
       	 
       	 if (user.getPrivilegelevel() > activeUser.getPrivilegelevel())
            {
                return "00";//您不能激活同级或上级管理员账号
            }
       	 
			 managersDao.activeManager(activeUser.getUsername());
            result = "11";//激活成功
			 
        }
	return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String freezeUser(String username, String activeUsername) throws Exception {
		String result = "冻结失败";
		 List<Managers> userAll = managersDao.getUserAndActiveUser(username,activeUsername);
		 if (userAll.size() != 2)
       {
           return result;
       }
       else
       {
      	 Managers user = userAll.get(0).getUsername().equals(username)?userAll.get(0):userAll.get(1);
      	 Managers activeUser=userAll.get(0).getUsername().equals(activeUsername)?userAll.get(0):userAll.get(1);
      	 
      	 if (user.getPrivilegelevel() > activeUser.getPrivilegelevel())
           {
               return "00";//您不能冻结同级或上级管理员账号
           }
      	 
			 managersDao.freezeManager(activeUser.getUsername());
           result = "11";//冻结成功
			 
       }
	return result;
	}

	@Override
	public Map<String, Object> getFarmDogList(String userName, int startPage, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String,Object>();

		Page page = PageHelper.startPage(startPage, pageSize);
		//该管理员管理的项圈狗
		List<Sheepdogs> dogs = sheepdogsDao.getFarmDogList(userName);
		int i=0;
		if(dogs.size() == 0) {
		/*	maptemp.put("dogid", "暂无条目");
			maptemp.put("dogname", "暂无条目");
			maptemp.put("neckletid", "----");
			maptemp.put("firstmedtime", "");
			maptemp.put("lastmed", "");
			maptemp.put("timemed", 0);
			maptemp.put("nextmed", "");*/
		//	maptemp.put("exhibitcycle", "----");
			//map.put(""+i, maptemp);
		}else {
			for(Sheepdogs item :dogs) {
				Map<String, Object> maptemp = new HashMap<String, Object>();
				maptemp.put("dogid", item.getDogid());
				maptemp.put("dogname", item.getDogname());
				//String neckletid = item.getNeckletid();
				if(item.getApparatusid().equals("-1")) {
					  Lastexhibitrealtime dogInfo = exhibitrealtimeDao.getDogInfo(item.getNeckletid());
					  if(dogInfo == null) {
					    	maptemp.put("neckletid", "----");
					    	maptemp.put("firstmedtime", "");
					    	maptemp.put("lastmed", "");
					    	maptemp.put("timemed", 0);
					    	maptemp.put("nextmed", "");
					    	data.put(""+i, maptemp);
		                    i++;
					    }else {
					    	maptemp.put("neckletid", item.getNeckletid());
					    	Feedback feedback = feedbackDao.getFeedback(item.getNeckletid());
					    	maptemp.put("firstmedtime", feedback.getFirstmedtime().toString());
					    	maptemp.put("lastmed", dogInfo.getRealtime());
		                     
		                    int tableremain = dogInfo.getTableremain();
		                    maptemp.put("timemed", feedback.getMedtotal()- tableremain);
		                    maptemp.put("nextmed",dogInfo.getNextexhibittime());

		                  //  maptemp.put("exhibitcycle", Integer.parseInt(feedback.getExhibitcycle()) / 1440);
		                    data.put(""+i, maptemp);
		                    i++;
					    	
					    }
				}else {
	
					Lastappexhibitrealtime dogInfo = appexhibitrealtimeDao.getDogInfo(item.getApparatusid());
					  if(dogInfo == null) {
					    	maptemp.put("neckletid", "----");
					    	maptemp.put("firstmedtime", "");
					    	maptemp.put("lastmed", "");
					    	maptemp.put("timemed", 0);
					    	maptemp.put("nextmed", "");
					    	data.put(""+i, maptemp);
		                    i++;
					    }else {
					    	maptemp.put("neckletid", item.getApparatusid());
					  
					    	Feederback feederback = feederbackDao.getFeederback(item.getApparatusid());
					    	
					    	maptemp.put("firstmedtime", feederback.getFirstmedtime().toString());
					    	maptemp.put("lastmed", dogInfo.getRealtime());
		              

		                    int tableremain = dogInfo.getTableremain();
		                    maptemp.put("timemed", feederback.getMedtotal()- tableremain);
		                    maptemp.put("nextmed",dogInfo.getNextexhibittime());

		                  //  maptemp.put("exhibitcycle", Integer.parseInt(feedback.getExhibitcycle()) / 1440);
		                    data.put(""+i, maptemp);
					    	i++;
					    }
			    	}	  
			   
			}
			
		}
		 //每页狗信息
        map.put("data", data);
        //狗总数
		map.put("dogTotal",page.getTotal());
		return map;
	}

 

}
