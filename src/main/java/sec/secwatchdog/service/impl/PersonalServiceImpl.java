package sec.secwatchdog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.PersonalService;
import sec.secwatchdog.util.AESUtil;
@Service("personalService")
public class PersonalServiceImpl implements PersonalService {

	@Autowired
	private ManagersDao managersDao;
	@Override
	public Map<String, Object> getUnActiveUsers(String usrname) {
		Managers manager = managersDao.getManagerByName(usrname);
		Map<String, Object> map = new HashMap<String,Object>();
		List<Managers> magagers = new ArrayList<Managers>();
		int i = 0;
		switch(manager.getPrivilegelevel()) {
			case 1:
				magagers = managersDao.getManagersByPrivilegelevelAndManagerstatus(2);
				if(magagers == null) {
					return null;
				}
				for(Managers magager : magagers) {
					Map<String, Object> maptemp = new HashMap<String,Object>();
					maptemp.put("username", magager.getUsername());
					maptemp.put("managername", magager.getManagername());
					maptemp.put("logintime", magager.getLogintime());
				
					maptemp.put("area", magager.getProvince() + magager.getCity() + magager.getCounty() + magager.getVillage() + magager.getHamlet());			 
					maptemp.put("telphonecall", magager.getManagertel());
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
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String activeAdmin(String username, String activeUsername) throws Exception{
		 String result = null;
		 List<Managers> userAll = managersDao.getUserAndActiveUser(username,activeUsername);
		 if (userAll.size() != 2)
         {
             return result;
         }
         else
         {
        	 Managers user = userAll.get(0).getUsername().equals(username)?userAll.get(0):userAll.get(1);
        	 Managers activeUser=userAll.get(0).getUsername().equals(activeUsername)?userAll.get(0):userAll.get(1);
        	 
        	 if (user.getPrivilegelevel() >= activeUser.getPrivilegelevel())
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
	public String modifyUser(String username, String managername, String managerjob,
			String manageridentity, String officecall, String managertel, String manageraddress, String email,
			String password) throws Exception {
		 String result = null;
		 Managers user = managersDao.getManagerByName(username);
		 //给密码加密
		 AESUtil aes = new AESUtil();
		 //根据username进行更新
		 managersDao.updateManager(username, managername, managerjob, manageridentity, officecall, managertel, manageraddress, email, aes.encryptData(password));
		
		 result =  "修改成功！";
		 return result;
	}

}
