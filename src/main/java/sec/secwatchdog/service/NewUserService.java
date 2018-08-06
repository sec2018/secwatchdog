package sec.secwatchdog.service;

import java.util.Map;

public interface NewUserService {
	public Map<String, Object> getProvinces();
	public Map<String, Object> getCitys(String provincename);
	public Map<String, Object> getCountys(String provincename,String cityname);
	public Map<String, Object> getVillages(String provincename,String cityname,String countyname);
	public Map<String, Object> getHamlets(String provincename,String cityname,String countyname,String villagename);
	public String addUser(String addtype, int privilegelevel, String username, String managername, String address, String identity, String area, String officecall, String tel, String password) throws Exception;

}
