package sec.secwatchdog.service;

import java.util.Map;

public interface ProvinceService {

	public Map<String, Integer> GetIndexLogoInfo(String provincename);
    public Map<String, Integer> GetArmyIndexLogo(String provincename);
    public Map<String, Object> GetProvinceMap(String provincename);
    public Map<String, Object> GetArmyProvinceMap(String provincename);
	public Map<String, Object> GetDistrictcode(String provincename);
}
