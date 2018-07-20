package sec.secwatchdog.dao;

import java.util.Map;

public interface VillageDao {
	public Map<String, Integer> GetIndexLogoInfo(String provinceName, String cityName, String countyName,String villageName);
    public Map<String, Object> GetVillageMap(String provinceName, String cityName, String countyName,String villageName);
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName,String villageName);
	
}
