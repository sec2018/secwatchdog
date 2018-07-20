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

import sec.secwatchdog.dao.ProvinceDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;
import org.apache.ibatis.annotations.Mapper;

@Repository("provinceDao")
public class ProvinceDaoImpl implements ProvinceDao {
	@Autowired
    private SqlSession session;
   /* ProvinceDaoImpl(){
		//ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���  
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
	public Map<String, Integer> GetIndexLogoInfo(String provincename) {	
		 
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Districts districtsist = null;
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement="";
		//��øõ����������ǰ��λ(ʡ)
		statement = "sec.secwatchdog.dao.ProvinceDao.getdistrictsist";//ӳ��sql�ı�ʶ�ַ���  
		districtsist = session.selectOne(statement,mapparam);
		String provincecode = districtsist.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.ProvinceDao.getprovinceindexinfo";//ӳ��sql�ı�ʶ�ַ���  
		mapparam.put("provincecode0to2", provincecode0to2);
		sdlist = session.selectList(statement, mapparam);
		//�����Ȧ��Ȯ������ιʳ������
		int neckdognumtotal = 0;
		int feedernumtotal = 0;
		for(Sheepdogs each:sdlist){
			if(!each.getNeckletid().equals("-1")) {//"-1"��ʾδ�����Ȧ
				neckdognumtotal++;
			}
			if(!each.getApparatusid().equals("-1")) {//"-1"��ʾ��ιʳ��
				feedernumtotal++;
			}
		}
		statement = "sec.secwatchdog.dao.ProvinceDao.getexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med1 = session.selectOne(statement, mapparam);//Ͷҩ����
		statement = "sec.secwatchdog.dao.ProvinceDao.getappexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med2 = session.selectOne(statement, mapparam);//ιʳ����
		int mednumtotal = med1 + med2;//�����ܴ���
		List<Districts> dislist = new ArrayList<Districts>();
		//�����������λ���ֿ�ͷ�ı�Ŷ�Ӧ������������Ϣ
		statement = "sec.secwatchdog.dao.ProvinceDao.ywdisctricts";//ӳ��sql�ı�ʶ�ַ���  
		dislist = session.selectList(statement,mapparam);
		
        //�С��ء��硢������������
		int cityepidemictotal = 0;	
		int countyepidemictotal = 0;
		int villageepidemictotal = 0;
		int hamletepidemictotal = 0;
		
		for(Districts each : dislist) {
			if(each.getEpidemic() == 1) {//������Ϊ������
				//����������뽫������࣬��xx0000000000Ϊʡ��xxxx00000000����������Ϊ�м���xxxxxx000000Ϊ�ؼ���xxxxxxxxx000Ϊ�缶�������λ��ȫΪ0���ʾ�弶
				if(each.getDistrictcode().substring(4, 12).equals("00000000")) {
					cityepidemictotal++;
				}
				else if(each.getDistrictcode().substring(6,12).equals("000000")) {
					countyepidemictotal++;
				}
				else if(each.getDistrictcode().substring(9,12).equals("000")) {
					villageepidemictotal++;
				}else
					hamletepidemictotal++;
			}
		
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.ProvinceDao.getmanagerlevel";//ӳ��sql�ı�ʶ�ַ���  
		levellist = session.selectList(statement,mapparam);
		//ʡ���С��ء��硢�弶����Ա��
		int provinceadmintotal = 0;
		int cityadmintotal = 0;
		int countyadmintotal = 0;
		int villageadmintotal = 0;
		int hamletadmintotal = 0;

	
			for(Integer each:levellist) {
				switch(each) {
	            case 2:
	                provinceadmintotal++;
	                break;
	            case 3:
	                cityadmintotal++;
	                break;
	            case 4:
	                countyadmintotal++;
	                break;
	            case 5:
	                villageadmintotal++;
	                break;
	            case 6:
	                hamletadmintotal++;
	                break;
				}
			}
		
		//�����ݱ��浽map��
		map.put("cityepidemictotal", cityepidemictotal-1);
		map.put("countyepidemictotal", countyepidemictotal);
		map.put("villageepidemictotal", villageepidemictotal);
		map.put("hamletepidemictotal",  hamletepidemictotal);
		map.put("provinceadmintotal", provinceadmintotal);
		map.put("cityadmintotal", cityadmintotal);
		map.put("countyadmintotal", countyadmintotal);
		map.put("villageadmintotal", villageadmintotal);
		map.put("hamletadmintotal", hamletadmintotal);
		
		statement = "sec.secwatchdog.dao.ProvinceDao.getalldognum";//ӳ��sql�ı�ʶ�ַ���  
		mapparam.put("provincecode", provincecode);
		int alldognumtotal = session.selectOne(statement,mapparam);
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Integer> GetArmyIndexLogo(String provincename) {
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
 
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Districts districtsist = null;
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement="";
		//��øõ����������ǰ��λ(ʡ)	
		statement = "sec.secwatchdog.dao.ProvinceDao.getdistrictsist";//ӳ��sql�ı�ʶ�ַ���  
		districtsist = session.selectOne(statement,mapparam);
		String provincecode = districtsist.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);
		

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.ProvinceDao.getprovinceindexinfo";//ӳ��sql�ı�ʶ�ַ���  
		mapparam.put("provincecode0to2", provincecode0to2);
		sdlist = session.selectList(statement, mapparam);
		//�����Ȧ��Ȯ������ιʳ������
		int neckdognumtotal = 0;
		int feedernumtotal = 0;
		for(Sheepdogs each:sdlist){
			if(!each.getNeckletid().equals("-1")) {//"-1"��ʾδ�����Ȧ
				neckdognumtotal++;
			}
			if(!each.getApparatusid().equals("-1")) {//"-1"��ʾ��ιʳ��
				feedernumtotal++;
			}
		}
		statement = "sec.secwatchdog.dao.ProvinceDao.getexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med1 = session.selectOne(statement, mapparam);//Ͷҩ����
		statement = "sec.secwatchdog.dao.ProvinceDao.getappexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med2 = session.selectOne(statement, mapparam);//ιʳ����
		int mednumtotal = med1 + med2;//�����ܴ���
		List<Districts> armylist = new ArrayList<Districts>(); 
		//�����������λ���ֿ�ͷ�ı�Ŷ�Ӧ������������Ϣ
		statement = "sec.secwatchdog.dao.ProvinceDao.ywdisctricts";//ӳ��sql�ı�ʶ�ַ���  
		armylist = session.selectList(statement,mapparam);
		
		//ʦ���ţ�������������
		int divisionepidemictotal=0;
		int regimentalepidemictotal=0;
		int companyepidemictotal=0;
		
		for(Districts each : armylist) {
			if(each.getEpidemic() == 1) {//������Ϊ������
				//����������뽫������࣬��xx000000Ϊ���ţ�xxxx0000����������Ϊʦ����xxxxxx00Ϊ�ż�������λ��ȫΪ0���ʾ����
				if(each.getDistrictcode().substring(4, 8).equals("0000")) {
					divisionepidemictotal++;
				}
				else if(each.getDistrictcode().substring(6,8).equals("00")) {
					regimentalepidemictotal++;
				}else
					companyepidemictotal++;
			}
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.ProvinceDao.getmanagerlevel";//ӳ��sql�ı�ʶ�ַ���  
		levellist = session.selectList(statement,mapparam);
		//���ţ�ʦ���ţ���������Ա����
		int armyadmintotal = 0;
        int divisionadmintotal = 0;
        int regimentaladmintotal = 0;
        int companyadmintotal = 0;

			 for (Integer each:levellist)
	           {
	               switch (each)//���ݹ���Ա����ͳ�Ƹ�������Ա����
	               {
	                   case 2:
	                       armyadmintotal++;
	                       break;
	                   case 3:
	                       divisionadmintotal++;
	                       break;
	                   case 4:
	                       regimentaladmintotal++;
	                       break;
	                   case 5:
	                       companyadmintotal++;
	                       break;
	               }
	           }
		
		//�����ݱ��浽map��
		map.put("divisionepidemictotal", divisionepidemictotal-1);
        map.put("regimentalepidemictotal", regimentalepidemictotal);
        map.put("companyepidemictotal",companyepidemictotal);
		map.put("armyadmintotal", armyadmintotal);
        map.put("divisionadmintotal", divisionadmintotal);
        map.put("regimentaladmintotal",regimentaladmintotal);
        map.put("companyadmintotal", companyadmintotal);
		
		statement = "sec.secwatchdog.dao.ProvinceDao.getalldognum";//ӳ��sql�ı�ʶ�ַ���  
		mapparam.put("provincecode", provincecode);
		int alldognumtotal = session.selectOne(statement,mapparam);
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Object> GetProvinceMap(String provincename) {
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
 
		Map<String, Object> map = new HashMap<String,Object>();//�������󷵻ص�����
		Map<String, String> mapparam = new HashMap<String,String>();//sql����
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.ProvinceDao.getprovincemap";//ӳ��sql�ı�ʶ�ַ���  
		 
		String thisprovince = session.selectOne(statement,mapparam);//���ʡ�������
	    String thisprovince1to2 = thisprovince.substring(0, 2);//����ǰ��λ��ʾʡ��
	    mapparam.put("thisprovince1to2", thisprovince1to2);
		statement = "sec.secwatchdog.dao.ProvinceDao.getcitiesandcounties";//ӳ��sql�ı�ʶ�ַ���  
		//��������к���
		List<Districts> citiesandcounties = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : citiesandcounties)
        { 
			//����ÿ��������
            if (pro.getDistrictcode().endsWith("00000000"))
            {
            	//����ÿ���е������Ϣ
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("cityname", pro.getShortname());
				String city1to4 = pro.getDistrictcode().substring(0, 4);
				int countynum = 0;
				//ͳ��ÿ�����������������صĸ���
				for(Districts cn:citiesandcounties) {
					if(cn.getDistrictcode().startsWith(city1to4) && cn.getDistrictcode().endsWith("000000") 
							&& !cn.getDistrictcode().equals(city1to4+"00000000")) {
						countynum++;
					}
				}
				//������������
				maptemp.put("countynum", countynum);
				//������й���Ա����
				statement = "sec.secwatchdog.dao.ProvinceDao.getmanagernum";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("districtname", pro.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//������Ȯ����
				statement = "sec.secwatchdog.dao.ProvinceDao.getallnecketid";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("city1to4", city1to4);
				List<String> dognumlist = session.selectList(statement,mapparam);
				maptemp.put("dognum", dognumlist.size());
				//������Ȧ����
				int neckletnum = 0;
				for(String n1:dognumlist) {
					//"-1"��ʾδ�����Ȧ
					if(!n1.equals("-1")) {
						neckletnum++;
					}
				}
				maptemp.put("neckletnum", neckletnum);
				//����Ͷҩ�ܴ���
				statement = "sec.secwatchdog.dao.ProvinceDao.getcountexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//����ιʳ������
				statement = "sec.secwatchdog.dao.ProvinceDao.getcountappexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int feednum = session.selectOne(statement,mapparam);
				maptemp.put("feedernum", feednum);			
				
				map.put(""+i, maptemp);
				
				i++;
            }
        }

		return map;
	}

	@Override
	public Map<String, Object> GetArmyProvinceMap(String provincename) {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, String> mapparam = new HashMap<String,String>();//sql����
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.ProvinceDao.getprovincemap";//ӳ��sql�ı�ʶ�ַ���  
		 
		String thisprovince = session.selectOne(statement,mapparam);
	    String thisprovince1to2 = thisprovince.substring(0, 2);
	    mapparam.put("thisprovince1to2", thisprovince1to2);
	  //�������ʦ����
		statement = "sec.secwatchdog.dao.ProvinceDao.getdivisionsandregimental";//ӳ��sql�ı�ʶ�ַ���  
		List<Districts> divisionsandregimental = session.selectList(statement,mapparam);
		int i=0;
		for(Districts divisions : divisionsandregimental)
        { 
			//����ÿ������ʦ
            if (divisions.getDistrictcode().endsWith("0000"))
            {
            	//����ÿ��ʦ�������Ϣ
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("divisionname", divisions.getDistrictname());
				String division1to4 = divisions.getDistrictcode().substring(0, 4);
				int regimentalnum = 0;
				//ͳ��ÿ������ʦ���������ŵĸ���
				for(Districts rt:divisionsandregimental) {
					if(rt.getDistrictcode().startsWith(division1to4) && rt.getDistrictcode().endsWith("00") 
							&& !rt.getDistrictcode().equals(division1to4+"0000")) {					 
						regimentalnum++;
					}
				}
				maptemp.put("regimentalnum", regimentalnum);
				//�����ʦ����Ա����
				statement = "sec.secwatchdog.dao.ProvinceDao.getmanagernum";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("districtname", divisions.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//������Ȯ����
				statement = "sec.secwatchdog.dao.ProvinceDao.getarmyallnecketid";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("division1to4", division1to4);
				List<String> dognumlist = session.selectList(statement,mapparam);
				maptemp.put("dognum", dognumlist.size());
				//������Ȧ����
				int neckletnum = 0;
				for(String n1:dognumlist) {
					//"-1"��ʾδ�����Ȧ
					if(!n1.equals("-1")) {
						neckletnum++;
					}
				}
				maptemp.put("neckletnum", neckletnum);
				//��γ��
				maptemp.put("lng", divisions.getLng());
				maptemp.put("lat", divisions.getLat());
				//����Ͷҩ����
				statement = "sec.secwatchdog.dao.ProvinceDao.getarmycountexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//����ιʳ����
				statement = "sec.secwatchdog.dao.ProvinceDao.getarmycountappexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int feednum = session.selectOne(statement,mapparam);
				maptemp.put("feedernum", feednum);	
 
				map.put(""+i, maptemp);
				i++;
            }
        }

		return map;
	}

	@Override
	public Map<String, Object> GetDistrictcode(String provincename) {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> mapparam = new HashMap<String,Object>();
		Districts districtsist = null;
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.ProvinceDao.getdistrictsist";//ӳ��sql�ı�ʶ�ַ���  
		
		districtsist = session.selectOne(statement,mapparam);
		String provincecode = districtsist.getDistrictcode();
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provincename));
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provincename).replace("*",""));
		map.put("districtcode",provincecode);
		return map;
	}
 


}
