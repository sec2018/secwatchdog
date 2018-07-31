package sec.secwatchdog.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.NewUserService;
import sec.secwatchdog.util.AESUtil;
import sec.secwatchdog.util.ChangeTimeFormat;
import sec.secwatchdog.util.DistrictCode;
@Service("newUserService")
public class NewUserServiceImpl implements NewUserService {
	
	@Autowired
	private DistrictsDao districtsDao;
	@Autowired
	private DistrictCode districtCode;
	@Autowired
	private ManagersDao managersDao;

	@Override
	public Map<String, Object> getProvinces() {
		Map<String, Object> map = new HashMap<String,Object>();
		List<Districts> provinces =  districtsDao.getProvinces();
		int i=0;
		for(Districts each : provinces) {
			Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("districtlevel", each.getDistrictlevel());
			maptemp.put("districtcode", each.getDistrictcode());
			maptemp.put("districtname", each.getDistrictname());
			maptemp.put("epidemic", each.getEpidemic());
			map.put(""+i, maptemp);
			i++;
			
		}
		return map;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public String addUser(String addtype, int privilegelevel, String username, String managername, String address,
			String identity, String area, String officecall, String tel, String password) throws Exception {
		String result = null;
		// 如果用户存在，则无法再次创建
	
		if (managersDao.getManagerByName(username)!=null) {
			result = "添加失败，用户名已经存在！";
		}
		Managers manager = new Managers();

		String province = "";
		String city = "";
		String county = "";
		String village = "";
		String hamlet = "";
		String chargehamlet = "";
  
		String[] areaarr = area.split("-");
		 if (areaarr.length ==1)
         {
             province = areaarr[0];
             city = "";
             county = "";
             village = "";
             hamlet = "";
             chargehamlet = "";
         }
         else if (areaarr.length ==2)
         {
             province = areaarr[0];
             city = areaarr[1];
             county = "";
             village = "";
             hamlet = "";
             chargehamlet = "";
         }
         else if (areaarr.length ==3)
         {
             province = areaarr[0];
             city = areaarr[1];
             county = areaarr[2];
             village = "";
             hamlet = "";
             chargehamlet = "";
         }
         else if (areaarr.length ==4)
         {
             province = areaarr[0];
             city = areaarr[1];
             county = areaarr[2];
             village = areaarr[3];
             hamlet = "";
             chargehamlet = "";
         }
         else if (areaarr.length ==5)
         {
             province = areaarr[0];
             city = areaarr[1];
             county = areaarr[2];
             village = areaarr[3];
             hamlet = areaarr[4];
             chargehamlet = areaarr[4];
         }
		manager.setProvince(province);
		manager.setCity(city);
		manager.setCounty(county);
		manager.setVillage(village);
		manager.setHamlet(hamlet);
		manager.setChargehamlet(hamlet);
		
 
		manager.setUsername(username);
		manager.setManagername(managername); 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		manager.setLogintime(ChangeTimeFormat.changeTimeFormat(df.format(new Date()).toString()));
		manager.setManagertel(tel);
		manager.setManagerphone("");
		AESUtil aes = new AESUtil();
		manager.setPassword(aes.encryptData(password));
		manager.setPrivilegelevel(privilegelevel);
		manager.setPrivilegedetail("all");
	 
		if (addtype.equals("self")) {
			manager.setManagerstatus(0);
		} else {
			manager.setManagerstatus(1);
		}
		manager.setManagerretirtime("");
		manager.setOfficecall(officecall);
		manager.setAddress(address);
		manager.setUpusername("");
		manager.setWorkplace("");
		manager.setManageridentity(identity);
		manager.setEmail("");
		// 获取地区编码
		System.out.println(manager.getLogintime());
		System.out.println(manager.getPassword());
		manager.setDistrictcode(Long.valueOf((districtCode.getDistrictCode(province, city, county, village, hamlet))));
		managersDao.insertManager(manager);

		result = "添加用户成功！";
		return result;
	}
}
