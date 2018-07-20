package sec.secwatchdog.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sec.secwatchdog.dao.CountyDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;
import org.apache.ibatis.annotations.Mapper;

@Repository("countyDao")
public class CountyDaoImpl implements CountyDao {
	@Autowired
    private SqlSession session;
	
	/*public CountyDaoImpl(){
		//ʹ�������������mybatis�������ļ�(��Ҳ���ع�����ӳ���ļ�)
        String resource = "mybatis-config.xml";
        Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();      
	        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);
	        session = sqlSessionFactory.openSession();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}*/
	
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provinceName,String cityName, String countyName) {	
		provinceName = NameConversionUtil.EchartsAreaNameToGov(session, provinceName);
		cityName = NameConversionUtil.EchartsAreaNameToGov(session, cityName);
		countyName = NameConversionUtil.EchartsAreaNameToGov(session, countyName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provinceName", provinceName);
		mapparam.put("cityName", cityName);
		mapparam.put("countyName", countyName);
		//��õ������ǰ��λ(ʡ)
		String statement = "sec.secwatchdog.dao.CountyDao.getProvince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provincCode0to2 = provinceCode.substring(0,2);	
		mapparam.put("provincCode0to2", provincCode0to2);
		//��õ������ǰ��λ(��)
		statement = "sec.secwatchdog.dao.CountyDao.getCity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);
		
		//��õ������ǰ��λ(��)
		statement = "sec.secwatchdog.dao.CountyDao.getCounty";//ӳ��sql�ı�ʶ�ַ���  
		Districts county= session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	
       	mapparam.put("countyCode0to6", countyCode0to6); 
		
		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.CountyDao.getCountyIndexInfo";//ӳ��sql�ı�ʶ�ַ���  
		
		sdlist = session.selectList(statement, mapparam);
		//�����Ȧ��Ȯ������ιʳ������
		int neckDogNumTotal = 0;
		int feederNumTotal = 0;
		for(Sheepdogs each:sdlist){
			if(!each.getNeckletid().equals("-1")) {//"-1"��ʾδ�����Ȧ
				neckDogNumTotal++;
			}
			if(!each.getApparatusid().equals("-1")) {//"-1"��ʾ��ιʳ��
				feederNumTotal++;
			}
		}
		statement = "sec.secwatchdog.dao.CountyDao.getExhiCount";//ӳ��sql�ı�ʶ�ַ���  
		int med1 = session.selectOne(statement, mapparam);//Ͷҩ����
		statement = "sec.secwatchdog.dao.CountyDao.getAppexhiCount";//ӳ��sql�ı�ʶ�ַ���  
		int med2 = session.selectOne(statement, mapparam);//ιʳ����
		int medNumTotal = med1 + med2;//�����ܴ���
		List<Districts> dislist = new ArrayList<Districts>();
		//���������λ���ֿ�ͷ�ı�Ŷ�Ӧ�������������Ϣ
		statement = "sec.secwatchdog.dao.CountyDao.ywDisctricts";//ӳ��sql�ı�ʶ�ַ���  
		dislist = session.selectList(statement,mapparam);
		
        //�硢������������
		int villageEpidemicTotal = 0;
		int hamletEpidemicTotal = 0;
		
		for(Districts each : dislist) {
			if(each.getEpidemic() == 1) {//������Ϊ������
				//����������뽫������࣬��xx0000000000Ϊʡ��xxxx00000000����������Ϊ�м���xxxxxx000000Ϊ�ؼ���xxxxxxxxx000Ϊ�缶�������λ��ȫΪ0���ʾ�弶
			  if(each.getDistrictcode().substring(9,12).equals("000")) {
				  villageEpidemicTotal++;
				}else
					hamletEpidemicTotal++;
			}
		
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.CountyDao.getManagerLevel";//ӳ��sql�ı�ʶ�ַ���  
		levellist = session.selectList(statement,mapparam);
		//�ء��硢�弶����Ա��
		int countyAadminTotal = 0;
		int villageAdminTotal = 0;
		int hamletAdminTotal = 0;

			for(Integer each:levellist) {
				switch(each) {
	            case 4:
	            	countyAadminTotal++;
	                break;
	            case 5:
	            	villageAdminTotal++;
	                break;
	            case 6:
	            	hamletAdminTotal++;
	                break;
				}
			}
		
		//�����ݱ��浽map��
		map.put("villageepidemictotal", villageEpidemicTotal-1);
		map.put("hamletepidemictotal",  hamletEpidemicTotal);

		map.put("countyadmintotal", countyAadminTotal);
		map.put("villageadmintotal", villageAdminTotal);
		map.put("hamletadmintotal", hamletAdminTotal);
		
		statement = "sec.secwatchdog.dao.CountyDao.getAllDogNum";//ӳ��sql�ı�ʶ�ַ���  
		int allDogNumTotal = session.selectOne(statement,mapparam);
		
		map.put("neckdognumtotal", neckDogNumTotal);
		map.put("alldognumtotal",allDogNumTotal );
		map.put("countrymednumtotal", medNumTotal);
		map.put("feedernumtotal", feederNumTotal);
	   return map;
	}

	 

	@Override
	public Map<String, Object> GetCountyMap(String provinceName, String cityName, String countyName) {
		provinceName = NameConversionUtil.EchartsAreaNameToGov(session,provinceName);
		cityName = NameConversionUtil.EchartsAreaNameToGov(session,cityName);
		countyName = NameConversionUtil.EchartsAreaNameToGov(session,countyName);
 
		Map<String, Object> map = new HashMap<String,Object>();//�������󷵻ص�����
		Map<String, String> mapparam = new HashMap<String,String>();//sql����
		mapparam.put("provinceName", provinceName);
		String statement = "sec.secwatchdog.dao.CountyDao.getProvinceMap";//ӳ��sql�ı�ʶ�ַ���   
		String thisProvince = session.selectOne(statement,mapparam);//���ʡ�������
	    String thisProvince0to2 = thisProvince.substring(0, 2);//����ǰ��λ��ʾʡ��
	    mapparam.put("thisProvince0to2", thisProvince0to2);
	    mapparam.put("cityName", cityName);
	    statement = "sec.secwatchdog.dao.CountyDao.getCityMap";//ӳ��sql�ı�ʶ�ַ���      
	    String thisCity = session.selectOne(statement,mapparam);//������������
	    String thisCity0to4 = thisCity.substring(0, 4);//����ǰ��λ��ʾ��
	    mapparam.put("thisCity0to4", thisCity0to4);
	    
	    mapparam.put("countyName", countyName);
	    statement = "sec.secwatchdog.dao.CountyDao.getCountyMap";//ӳ��sql�ı�ʶ�ַ���      
	    String thisCounty = session.selectOne(statement,mapparam);//������������
	    String thisCounty0to6 = thisCounty.substring(0, 6);//����ǰ��λ��ʾ�� 	    
	    mapparam.put("thisCounty0to6", thisCounty0to6);
		statement = "sec.secwatchdog.dao.CountyDao.getVillagesAndHamlets";//ӳ��sql�ı�ʶ�ַ���  
		//���������ʹ�
		List<Districts> villagesAndHamlets = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : villagesAndHamlets)
        { 
			//����ÿ��������
            if (pro.getDistrictcode().endsWith("000"))
            {
            	//����ÿ����������Ϣ
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("townname", pro.getShortname());
				String village0to9 = pro.getDistrictcode().substring(0, 9);
				int HamletsNum = 0;
				//ͳ��ÿ���������������д�ĸ���
				for(Districts cn:villagesAndHamlets) {
					if(cn.getDistrictcode().startsWith(village0to9)
							&& !cn.getDistrictcode().equals(village0to9+"000")) {
						HamletsNum++;
					}
				}
				//�������д���
				maptemp.put("hamletnum", HamletsNum);
				//������ع���Ա����
				statement = "sec.secwatchdog.dao.CountyDao.getManagerNum";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("districtName", pro.getDistrictname());
				int managerNum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managerNum);
				//������Ȯ����
				statement = "sec.secwatchdog.dao.CountyDao.getAllNecketId";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("village0to9", village0to9);
				List<String> dogNumList = session.selectList(statement,mapparam);
				maptemp.put("dognum", dogNumList.size());
				//������Ȧ����
				int neckletNum = 0;
				for(String n1:dogNumList) {
					//"-1"��ʾδ�����Ȧ
					if(!n1.equals("-1")) {
						neckletNum++;
					}
				}
				maptemp.put("neckletnum", neckletNum);
				//��γ��
				maptemp.put("lng", pro.getLng());
				maptemp.put("lat", pro.getLat());
				//����Ͷҩ�ܴ���
				statement = "sec.secwatchdog.dao.CountyDao.getCountExhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int medNum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", medNum);
				//����ιʳ����
				statement = "sec.secwatchdog.dao.CountyDao.getCountAppexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int feednum = session.selectOne(statement,mapparam);
				maptemp.put("feedernum", feednum);	

				map.put(""+i, maptemp);
				
				i++;
            }
        }
		return map;
	}

 
	@Override
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName) {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> mapparam = new HashMap<String,Object>();
	
		mapparam.put("provinceName", provinceName);
		mapparam.put("cityName", cityName);
		mapparam.put("countyName", countyName);
		//��õ������ǰ��λ(ʡ)
		String statement = "sec.secwatchdog.dao.CountyDao.getProvince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		mapparam.put("provinceCode0to2", provinceCode0to2);
		//��õ������ǰ��λ(ʦ)
		statement = "sec.secwatchdog.dao.CountyDao.getCity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);
		
		//��õ������(��)
		statement = "sec.secwatchdog.dao.CountyDao.getCounty";//ӳ��sql�ı�ʶ�ַ���  
		Districts county = session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		
		map.put("countyGov",NameConversionUtil.EchartsAreaNameToGov(session, countyName));
		map.put("countyEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, countyName).replace("*",""));
		map.put("cityGov",NameConversionUtil.EchartsAreaNameToGov(session, cityName));
		map.put("cityEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, cityName).replace("*",""));
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provinceName));
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provinceName).replace("*",""));
		map.put("districtcode",countyCode);
		return map;
		
	}
 

}
