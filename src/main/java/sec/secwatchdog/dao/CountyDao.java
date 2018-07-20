package sec.secwatchdog.dao;

import java.util.Map;

public interface CountyDao {
	public Map<String, Integer> GetIndexLogoInfo(String provinceName, String cityName, String countyName);
    public Map<String, Object> GetCountyMap(String provinceName, String cityName, String countyName);
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName);
	
}
