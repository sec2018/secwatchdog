package sec.secwatchdog.service;

import java.util.Map;

public interface ProvinceService {

	public Map<String, Integer> GetIndexLogoInfo(String provincename) throws Exception;
    public Map<String, Integer> GetArmyIndexLogo(String provincename) throws Exception;
    public Map<String, Object> GetProvinceMap(String provincename) throws Exception;
    public Map<String, Object> GetArmyProvinceMap(String provincename) throws Exception;
	public Map<String, Object> GetDistrictcode(String provincename) throws Exception;
}
