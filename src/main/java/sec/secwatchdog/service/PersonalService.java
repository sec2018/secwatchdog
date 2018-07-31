package sec.secwatchdog.service;

import java.util.Map;

public interface PersonalService {
	public Map<String,Object> getUnActiveUsers(String usrname);
	public String activeAdmin(String username, String activeUsername) throws Exception;
	public String modifyUser(String username, String managername, String managerjob, String manageridentity, String officecall, String managertel, String manageraddress, String email, String password) throws Exception;
}
