package sec.secwatchdog.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class NameConversionUtil {
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
