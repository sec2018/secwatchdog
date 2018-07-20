package sec.secwatchdog.dao.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sec.secwatchdog.dao.VillageDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;
import org.apache.ibatis.annotations.Mapper;

@Repository("villageDao")
public class VillageDaoImpl implements VillageDao {
	@Autowired
    private SqlSession session;
	
/*	public VillageDaoImpl(){
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
	public Map<String, Integer> GetIndexLogoInfo(String provinceName,String cityName, String countyName,String villageName) {	
		provinceName = NameConversionUtil.EchartsAreaNameToGov(session, provinceName);
		cityName = NameConversionUtil.EchartsAreaNameToGov(session, cityName);
		countyName = NameConversionUtil.EchartsAreaNameToGov(session, countyName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provinceName", provinceName);
		mapparam.put("cityName", cityName);
		mapparam.put("countyName", countyName);
		mapparam.put("villageName", villageName);
		//��õ������ǰ��λ(ʡ)
		String statement = "sec.secwatchdog.dao.VillageDao.getProvince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provincCode0to2 = provinceCode.substring(0,2);
		mapparam.put("provincCode0to2", provincCode0to2);
		//��õ������ǰ��λ(��)
		statement = "sec.secwatchdog.dao.VillageDao.getCity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);
		//��õ������ǰ��λ(��)
		statement = "sec.secwatchdog.dao.VillageDao.getCounty";//ӳ��sql�ı�ʶ�ַ���  
		Districts county= session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	
		mapparam.put("countyCode0to6", countyCode0to6); 	
		//��õ������ǰ��λ(��)
		statement = "sec.secwatchdog.dao.VillageDao.getVillage";//ӳ��sql�ı�ʶ�ַ���  
		Districts village= session.selectOne(statement,mapparam);
		String villageCode = village.getDistrictcode();
		String villageCode0to9 = villageCode.substring(0,9);	
		mapparam.put("villageCode0to9", villageCode0to9); 		
		
		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.VillageDao.getVillageIndexInfo";//ӳ��sql�ı�ʶ�ַ���  
		
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
		statement = "sec.secwatchdog.dao.VillageDao.getExhiCount";//ӳ��sql�ı�ʶ�ַ���  
		int med1 = session.selectOne(statement, mapparam);//Ͷҩ����
		statement = "sec.secwatchdog.dao.VillageDao.getAppexhiCount";//ӳ��sql�ı�ʶ�ַ���  
		int med2 = session.selectOne(statement, mapparam);//ιʳ����
		int medNumTotal = med1 + med2;//�����ܴ���
		List<Districts> dislist = new ArrayList<Districts>();
		//������Ͼ�λ���ֿ�ͷ�ı�Ŷ�Ӧ�������������Ϣ
		statement = "sec.secwatchdog.dao.VillageDao.ywDisctricts";//ӳ��sql�ı�ʶ�ַ���  
		dislist = session.selectList(statement,mapparam);
		
        //������������
		int hamletEpidemicTotal = 0;	
		for(Districts each : dislist) {
			if(each.getEpidemic() == 1) {//������Ϊ������
					hamletEpidemicTotal++;	
			}
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.VillageDao.getManagerLevel";//ӳ��sql�ı�ʶ�ַ���  
		levellist = session.selectList(statement,mapparam);
		//�硢�弶����Ա��
		int villageAdminTotal = 0;
		int hamletAdminTotal = 0;

			for(Integer each:levellist) {
				switch(each) {
	            case 5:
	            	villageAdminTotal++;
	                break;
	            case 6:
	            	hamletAdminTotal++;
	                break;
				}
			}
		
		//�����ݱ��浽map��
		map.put("hamletepidemictotal",  hamletEpidemicTotal-1);

		map.put("villageadmintotal", villageAdminTotal);
		map.put("hamletadmintotal", hamletAdminTotal);
		
		statement = "sec.secwatchdog.dao.VillageDao.getAllDogNum";//ӳ��sql�ı�ʶ�ַ���  
		int allDogNumTotal = session.selectOne(statement,mapparam);
		map.put("neckdognumtotal", neckDogNumTotal);
		map.put("alldognumtotal",allDogNumTotal);
		map.put("countrymednumtotal", medNumTotal);
		map.put("feedernumtotal", feederNumTotal);
	   return map;
	}

	 

	@Override
	public Map<String, Object> GetVillageMap(String provinceName, String cityName, String countyName, String villageName) {
		provinceName = NameConversionUtil.EchartsAreaNameToGov(session,provinceName);
		cityName = NameConversionUtil.EchartsAreaNameToGov(session,cityName);
		countyName = NameConversionUtil.EchartsAreaNameToGov(session,countyName);
		villageName = NameConversionUtil.EchartsAreaNameToGov(session,villageName);
 
		Map<String, Object> map = new HashMap<String,Object>();//�������󷵻ص�����
		Map<String, String> mapparam = new HashMap<String,String>();//sql����
		mapparam.put("provinceName", provinceName);
		String statement = "sec.secwatchdog.dao.VillageDao.getProvinceMap";//ӳ��sql�ı�ʶ�ַ���   
		String thisProvince = session.selectOne(statement,mapparam);//���ʡ�������
	    String thisProvince0to2 = thisProvince.substring(0, 2);//����ǰ��λ��ʾʡ��
	    mapparam.put("thisProvince0to2", thisProvince0to2);
	    mapparam.put("cityName", cityName);
	    statement = "sec.secwatchdog.dao.VillageDao.getCityMap";//ӳ��sql�ı�ʶ�ַ���      
	    String thisCity = session.selectOne(statement,mapparam);//������������
	    String thisCity0to4 = thisCity.substring(0, 4);//����ǰ��λ��ʾ��
	    mapparam.put("thisCity0to4", thisCity0to4);
	    
	    mapparam.put("countyName", countyName);
	    statement = "sec.secwatchdog.dao.VillageDao.getCountyMap";//ӳ��sql�ı�ʶ�ַ���      
	    String thisCounty = session.selectOne(statement,mapparam);//������������
	    String thisCounty0to6 = thisCounty.substring(0, 6);//����ǰ��λ��ʾ�� 	    
	    mapparam.put("thisCounty0to6", thisCounty0to6);
	    
	    mapparam.put("villageName", villageName);
	    statement = "sec.secwatchdog.dao.VillageDao.getVillageMap";//ӳ��sql�ı�ʶ�ַ���      
	    String thisVillage = session.selectOne(statement,mapparam);//������������
	    String thisVillage0to9 = thisVillage.substring(0, 9);//����ǰ��λ��ʾ�� 	    
	    mapparam.put("thisVillage0to9", thisVillage0to9);
		statement = "sec.secwatchdog.dao.VillageDao.getHamlets";//ӳ��sql�ı�ʶ�ַ���  
		//������д�
		List<Districts> hamlets = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : hamlets)
        { 
			//����ÿ�����д�
         
        	//����ÿ����������Ϣ
        	Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("harmletname", pro.getShortname());
			String hamletCode = pro.getDistrictcode();
			
			//������ع���Ա����
			statement = "sec.secwatchdog.dao.VillageDao.getManagerNum";//ӳ��sql�ı�ʶ�ַ���  
			mapparam.put("districtName", pro.getDistrictname());
			int managerNum = session.selectOne(statement,mapparam);
			maptemp.put("managernum", managerNum);
			//������Ȯ����
			statement = "sec.secwatchdog.dao.VillageDao.getAllNecketId";//ӳ��sql�ı�ʶ�ַ���  
			mapparam.put("hamletCode", hamletCode);
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
			statement = "sec.secwatchdog.dao.VillageDao.getCountExhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
			int medNum = session.selectOne(statement,mapparam);
			maptemp.put("mednum", medNum);
			//����ιʳ����
			statement = "sec.secwatchdog.dao.VillageDao.getCountAppexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
			int feednum = session.selectOne(statement,mapparam);
			maptemp.put("feedernum", feednum);	

			map.put(""+i, maptemp);
			
			i++;
            
        }
		return map;
	}

 
	@Override
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName,String villageName) {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> mapparam = new HashMap<String,Object>();
	
		mapparam.put("provinceName", provinceName);
		mapparam.put("cityName", cityName);
		mapparam.put("countyName", countyName);
		mapparam.put("villageName", villageName);
		//��õ������ǰ��λ(ʡ)
		String statement = "sec.secwatchdog.dao.VillageDao.getProvince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		mapparam.put("provinceCode0to2", provinceCode0to2);
		//��õ������ǰ��λ(ʦ)
		statement = "sec.secwatchdog.dao.VillageDao.getCity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);		
		//��õ������ǰ��(��)
		statement = "sec.secwatchdog.dao.VillageDao.getCounty";//ӳ��sql�ı�ʶ�ַ���  
		Districts county = session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	
		mapparam.put("countyCode0to6", countyCode0to6);	
		//��õ������(��)
		statement = "sec.secwatchdog.dao.VillageDao.getVillage";//ӳ��sql�ı�ʶ�ַ���  
		Districts village = session.selectOne(statement,mapparam);
		String villageCode = village.getDistrictcode();
		
		map.put("villageGov",NameConversionUtil.EchartsAreaNameToGov(session, villageName));
		map.put("villageEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, villageName).replace("*",""));
		map.put("countyGov",NameConversionUtil.EchartsAreaNameToGov(session, countyName));
		map.put("countyEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, countyName).replace("*",""));
		map.put("cityGov",NameConversionUtil.EchartsAreaNameToGov(session, cityName));
		map.put("cityEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, cityName).replace("*",""));
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provinceName));
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provinceName).replace("*",""));
		map.put("districtcode",villageCode);
		return map;
		
	}
 

}
