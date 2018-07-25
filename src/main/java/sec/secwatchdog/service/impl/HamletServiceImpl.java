package sec.secwatchdog.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sec.secwatchdog.dao.HamletDao;
import sec.secwatchdog.model.PageBean;
import sec.secwatchdog.service.HamletService;

@Service("hamletService")
public class HamletServiceImpl implements HamletService{
	@Resource
	private HamletDao hamletDao;
 	
	public Map<String, Object> GetHamletMap(String province, String city, String county, String village, String hamlet){
		return hamletDao.GetHamletMap(province, city, county, village, hamlet);
	}

	public String GovToEchartsAreaName(String city) {
		return hamletDao.GovToEchartsAreaName(city);
	}


	public Map<String, Object> GetHamletFeederMap(String province, String city, String county, String village,
			String hamlet){
		return hamletDao.GetHamletFeederMap(province, city, county, village, hamlet);
	}

	public Map<String, Object> GetLevel6AdminDogNum(String username){
		return hamletDao.GetLevel6AdminDogNum(username);
	}
	
	public Map<String, Object> CombineNeckletAndFeederDogList(PageBean pageBean, String username){
		return hamletDao.CombineNeckletAndFeederDogList(pageBean,username);
	}
	
	public Integer CombineNeckletAndFeederDogTotal(String username) {
		return hamletDao.CombineNeckletAndFeederDogTotal(username);
	}
}
