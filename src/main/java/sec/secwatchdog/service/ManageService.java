package sec.secwatchdog.service;

import java.util.Map;

public interface ManageService {
	public Map<String, Object> getNextLevelAdminInfo(String managerName,String districtcode, int startItem, int pageSize);
	public Map<String, Object> getNextLevelAdminInfo(String managerName, int startItem, int pageSize);
	public Map<String, Object> getVillageManagersList(String districtcode);
	public Map<String, Object> getNecksList(String username);
	public Map<String, Object> getFeedersList(String username);
	public Map<String, Object> getDogNeckletInfo(int dogId);
	public Map<String, Object> getDogFeederInfo(int dogId);
	public Map<String, Object> getDogInfo(int dogId);
	public Map<String, Object> getDogOwnerInfo(int dogId);


	public Map<String, Object> getManagerInfo(String managerName);
	public Map<String, Object>  getSearchByAdminInfo(String userName, String managerName,int startItem,int pageSize);
	public Map<String, Object>  getSearchByAdminInfo(String districtlevel, String managerName, String districtCode, int startItem,int pageSize);

	public String addOwer(String ownername, String owneridentity, String ownersex,String ownerhamletcode, int ownerage, String ownerjob, String homeaddress, String telphone) throws Exception;
	public String addNecklet(String neckletid,int medtotal, String category, String username) throws Exception;
	public String addFeeder(String apparatusid, int medtotal, String category, String username) throws Exception;
	public String bindFeeder(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid, String dogweight, String dogcolor, String dogage, String dogfeederid) throws Exception;
	public String bindNecklet(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid, String dogweight, String dogcolor, String dogage, String dogneckletid) throws Exception;
	public String addDog(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid, String dogweight, String dogcolor, String dogage) throws Exception;

}
