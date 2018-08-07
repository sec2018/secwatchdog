package sec.secwatchdog.service;

import java.util.Map;

public interface ManageService {
	public Map<String, Object> getNextLevelAdminInfo(String managerName,String districtcode, int startItem, int pageSize);
	public Map<String, Object> getNextLevelAdminInfo(String managerName, int startItem, int pageSize);
	public Map<String, Object> getVillageManagersList(String districtcode);
	public Map<String, Object> getNecksList(String username);
	public Map<String, Object> getFeedersList(String username);

	public Map<String, Object> getManagerInfo(String managerName);
	public Map<String, Object>  getSearchByAdminInfo(String userName, String managerName,int startItem,int pageSize);
	public Map<String, Object>  getSearchByAdminInfo(String districtlevel, String managerName, String districtCode, int startItem,int pageSize);
}
