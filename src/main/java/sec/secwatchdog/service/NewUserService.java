package sec.secwatchdog.service;

import java.util.Map;

public interface NewUserService {
	public Map<String, Object> getProvinces();
	public String addUser(String addtype, int privilegelevel, String username, String managername, String address, String identity, String area, String officecall, String tel, String password) throws Exception;

}
