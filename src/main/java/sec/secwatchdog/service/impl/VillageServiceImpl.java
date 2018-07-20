package sec.secwatchdog.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sec.secwatchdog.dao.VillageDao;
import sec.secwatchdog.service.VillageService;

@Service("villageService")
public class VillageServiceImpl implements VillageService {


	@Resource
	private VillageDao villageDao;

	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provinceName, String cityName, String countyName,String villageName) {
 
		return villageDao.GetIndexLogoInfo(provinceName, cityName, countyName,villageName);
	}

	@Override
	public Map<String, Object> GetVillageMap(String provinceName, String cityName, String countyName,String villageName) {
		// TODO Auto-generated method stub
		return villageDao.GetVillageMap(provinceName, cityName, countyName, villageName);
	}

	@Override
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName,String villageName) {
		// TODO Auto-generated method stub
		return villageDao.GetDistrictcode(provinceName, cityName, countyName,villageName);
	}
	
 

}
