package sec.secwatchdog.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sec.secwatchdog.dao.ProvinceDao;
import sec.secwatchdog.service.ProvinceService;

@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {


	@Resource
	private ProvinceDao provinceDao;
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provincename) {
		// TODO Auto-generated method stub
		return provinceDao.GetIndexLogoInfo(provincename);
	}

	@Override
	public Map<String, Integer> GetArmyIndexLogo(String provincename) {
		// TODO Auto-generated method stub
		return provinceDao.GetArmyIndexLogo(provincename);
	}

	@Override
	public Map<String, Object> GetProvinceMap(String provincename) {
		// TODO Auto-generated method stub
		return provinceDao.GetProvinceMap(provincename);
	}

	@Override
	public Map<String, Object> GetArmyProvinceMap(String provincename) {
		// TODO Auto-generated method stub
		return provinceDao.GetArmyProvinceMap(provincename);
	}

 
	@Override
	public Map<String, Object> GetDistrictcode(String provincename) {
		// TODO Auto-generated method stub
		return provinceDao.GetDistrictcode(provincename);
	}

}
