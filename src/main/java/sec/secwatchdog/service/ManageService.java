package sec.secwatchdog.service;

import java.util.Map;

public interface ManageService {
	public Map<String, Object> getNextLevelAdminInfo(String managerName,String districtcode, int startItem, int pageSize) throws Exception;
	public Map<String, Object> getNextLevelAdminInfo(String managerName, int startItem, int pageSize) throws Exception;
	public Map<String, Object> getVillageManagersList(String districtcode) throws Exception;
	public Map<String, Object> getNecksList(String username) throws Exception;
	public Map<String, Object> getFeedersList(String username) throws Exception;
	public Map<String, Object> getDogNeckletInfo(int dogId) throws Exception;
	public Map<String, Object> getDogFeederInfo(int dogId) throws Exception;
	public Map<String, Object> getDogInfo(int dogId) throws Exception;
	public Map<String, Object> getDogOwnerInfo(int dogId) throws Exception;


	public Map<String, Object> getManagerInfo(String managerName) throws Exception;
	public Map<String, Object>  getSearchByAdminInfo(String userName, String managerName,int startItem,int pageSize) throws Exception;
	public Map<String, Object>  getSearchByAdminInfo(String districtlevel, String managerName, String districtCode, int startItem,int pageSize) throws Exception;

	public String addOwer(String ownername, String owneridentity, String ownersex,String ownerhamletcode, int ownerage, String ownerjob, String homeaddress, String telphone) throws Exception;
	public String addNecklet(String neckletid,int medtotal, String category, String username) throws Exception;
	public String addFeeder(String apparatusid, int medtotal, String category, String username) throws Exception;
	public String bindFeeder(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid, String dogweight, String dogcolor, String dogage, String dogfeederid) throws Exception;
	public String bindNecklet(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid, String dogweight, String dogcolor, String dogage, String dogneckletid) throws Exception;
	public String addDog(String username, String dogname, String dogsex, String dogbelonghamlet, String ownerhamletcode, String dogownerid, String dogweight, String dogcolor, String dogage) throws Exception;
	
	public String modifyNecklet(String neckletid, String power, String medtotal, String medleft, String areacycle, String exhibitcycle, String firstmedtime) throws Exception;
	public String modifyFeeder(String feederid, String power, String medtotal, String medleft, String areacycle, String exhibitcycle, String firstmedtime) throws Exception;
	public String modifyOwner(String ownerid, String ownername, String owneridentity, String ownersex, String ownerage, String ownerjob, String homeaddress, String telphone) throws Exception;
	public String modifyDog(String username,String dogid, String dogname, String dogsex, String dogbelonghamlet, String districtcode, String dogownerid,String dogweight, String dogcolor, String dogage,String dogneckletid, String dogfeederid) throws Exception;
	
	

}
