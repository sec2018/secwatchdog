package sec.secwatchdog.service;

import java.util.Map;

public interface CountyService {

	public Map<String, Integer> GetIndexLogoInfo(String provinceName,String cityName,String countyName) throws Exception;
    public Map<String, Object> GetCountyMap(String provinceName,String cityName,String countyName) throws Exception;
	public Map<String, Object> GetDistrictcode(String provinceName,String cityName,String countyName) throws Exception;
}
