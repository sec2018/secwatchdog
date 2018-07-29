package sec.secwatchdog.service;

import java.util.Map;

public interface ManageService {
	public Map<String, Object> getNextLevelAdminInfo(String managerName, int startPage, int pageSize);
	public int getNextLevelAdminInfoTotalNum(String managerName);
	public Map<String, Object> getManagerInfo(String managerName);
	public Map<String, Object>  getSearchByAdminInfo(String userName, String managerName,int startItem,int pageSize);
	public Map<String, Object>  getSearchByAdminInfo(String privilegeLevel, String userName, String managerName);
	public int getSearchByAdminInfoTotal(String userName, String managerName,int startItem,int pageSize);
}
