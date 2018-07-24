package sec.secwatchdog.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.DistrictsDao;
@Service
public class NameConversionUtil {
	@Autowired
	private DistrictsDao districtsDao;
	public String GovToEchartsAreaName(String districtName) {	 
		String echartsareaname =  districtsDao.getEchartsareanametempByDistrictName(districtName);      
        return echartsareaname;
	}
	
	public String EchartsAreaNameToGov(String districtName) {
		String govareaname = districtsDao.getGovareanametempByDistrictName(districtName) ;	
		return govareaname;
	}
	public static String GovToEchartsAreaName(SqlSession session, String areaname) {
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("areaname", areaname);
		String statement = "sec.secwatchdog.util.echartsareanametemp";//ӳ��sql�ı�ʶ�ַ���  
		String echartsareaname =  session.selectOne(statement, mapparam);
        
        return echartsareaname;
	}
	
	public static String EchartsAreaNameToGov(SqlSession session, String areaname) {
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("areaname", areaname);
		String statement = "sec.secwatchdog.util.govareanametemp";//ӳ��sql�ı�ʶ�ַ���  
		String govareaname =  session.selectOne(statement, mapparam);
	 
		
		return govareaname;
	}
}
