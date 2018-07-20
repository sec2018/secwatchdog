package sec.secwatchdog.dao;

import java.util.Map;

import sec.secwatchdog.model.Managers;

public interface UserDao {
	
	public Managers login(Managers manager);

	public Map<String, Integer> GetIndexLogoInfo(Managers manager);
	
	public Map<String, Object> GetCountryMap();

	public Map<String, Object> GetXinJiangArmyCountryMap();
	
	public Managers checklogin(String username);
}
