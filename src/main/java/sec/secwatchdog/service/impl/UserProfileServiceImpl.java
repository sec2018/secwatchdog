package sec.secwatchdog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.UserProfileService;
@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private ManagersDao managersDao;
	@Autowired
	private SheepdogsDao sheepdogsDao;
	@Autowired
	private DistrictsDao districtsDao;

	@Override
	public Map<String, Object> getUserProfile(String userName) {
		System.out.println(userName);
		Map<String, Object> map = new HashMap<String, Object>();
		
		Managers manager = managersDao.getManagerByName(userName);
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
			Districts districtsist = districtsDao.getDistrictsByDistrictName(manager.getProvince());
			String provinceCode = districtsist.getDistrictcode();
			String provinceCode0to2 = provinceCode.substring(0,2);	
	        //获得该省所有的狗
			List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(provinceCode0to2);
			map.put("dogtotalnum", sdlist.size());
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
            }
			break;
		case 3:
			break;
			
		case 4:
			 break;
		case 5:
			 break;
		case 6:
			break;	
		}	
		return map;
	}

}
