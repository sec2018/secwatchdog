package sec.secwatchdog.service;

import java.util.Map;

public interface VillageService {

	public Map<String, Integer> GetIndexLogoInfo(String provinceName, String cityName, String countyName,String villagename);
    public Map<String, Object> GetVillageMap(String provinceName, String cityName, String countyName,String villagename);
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName,String villagename);
	
}
