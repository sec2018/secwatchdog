package sec.secwatchdog.dao;

import java.util.Map;

public interface CityDao {
	public Map<String, Integer> GetIndexLogoInfo(String provincename, String cityname);
    public Map<String, Integer> GetDivisionIndexLogo(String provincename, String cityname);
    public Map<String, Object> GetCityMap(String provincename, String cityname);
    public Map<String, Object> GetArmyCityMap(String provincename, String cityname);
	public Map<String, Object> GetDistrictcode(String provincename, String cityname);
	
}
