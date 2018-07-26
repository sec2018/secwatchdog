package sec.secwatchdog.service;

import java.util.List;
import java.util.Map;

import sec.secwatchdog.model.Managers;

public interface UserService {

	public Managers login(Managers manager);
	
	public Map<String, Integer> GetIndexLogoInfo(Managers resultUser);

	public Map<String, Object> GetCountryMap();

	public Map<String, Object> GetXinJiangArmyCountryMap();
	
	public Managers checklogin(String username);
	
	Managers findUserByName(String name);

  /*  List<Role> findLoginUserRoles(Managers manager);

    String getUserPassword(Integer uid);*/
}

