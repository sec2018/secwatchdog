package sec.secwatchdog.service;

import java.util.Map;

public interface UserProfileService {
	public Map<String, Object> getUserProfile(String userName) throws Exception;
	public Map<String, Object> getFarmDogList(String userName, int startItem, int pageSize) throws Exception;
	/*public Map<String, Object> getFarmFeederDogList(String userName);*/
	public String rebackPwd(String userName, String rebackUserName) throws Exception;
	public String activeUser(String username, String activeUsername) throws Exception;
	public String freezeUser(String username, String activeUsername) throws Exception;
}
