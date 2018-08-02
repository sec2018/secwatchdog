package sec.secwatchdog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Districts;
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

	@Override
	public Map<String, Object> getUserProfile(String userName) {
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

}
