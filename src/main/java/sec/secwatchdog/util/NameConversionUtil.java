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
 
}
