package sec.secwatchdog.service;

import java.util.List;
import java.util.Map;

import sec.secwatchdog.model.Managers;

public interface UserService {

	public Managers login(Managers manager) throws Exception;
	
	public Map<String, Integer> GetIndexLogoInfo(Managers resultUser) throws Exception;

	public Map<String, Object> GetCountryMap() throws Exception;

	public Map<String, Object> GetXinJiangArmyCountryMap() throws Exception;
	
	public Managers checklogin(String username) throws Exception;
	
	Managers findUserByName(String name) throws Exception;

  /*  List<Role> findLoginUserRoles(Managers manager);

    String getUserPassword(Integer uid);*/
	
	public List<Map<String,String>> GetAllCities() throws Exception;
	
	public List<Map<String,String>> GetAllCounties() throws Exception;
	
	public List<Map<String,String>> GetAllVillages() throws Exception;
	
	public List<Map<String,String>> GetAllHamlets() throws Exception;
}

