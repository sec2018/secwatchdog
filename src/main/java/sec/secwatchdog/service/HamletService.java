package sec.secwatchdog.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import sec.secwatchdog.model.PageBean;


public interface HamletService {

	//public Map<String, Object> Getuser_page_farmDogList(PageBean pageBean, String username);

	public Map<String, Object> GetHamletMap(String province, String city, String county, String village, String hamlet,HttpServletRequest request) throws Exception;

	public String GovToEchartsAreaName(String city) throws Exception;

	//public Map<String, Object> Getupuser_page_farmDogFeederList(String province, String city, String county,
	//		String village, String hamlet);

	public Map<String, Object> GetHamletFeederMap(String province, String city, String county, String village,
			String hamlet) throws Exception;

	public Map<String, Object> GetLevel6AdminDogNum(String username) throws Exception;

	//public Integer Getuser_page_farmDogListtotal(String username);

	public Map<String, Object> CombineNeckletAndFeederDogList(PageBean pageBean, String hamletCode) throws Exception;

	public Integer CombineNeckletAndFeederDogTotal(String hamletCode) throws Exception;
	public Map<String, Object> getCombineNeckletAndFeederDogByNeckletId(String neckletId, String hamletCode) throws Exception;
	
	
}
