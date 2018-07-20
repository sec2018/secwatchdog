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

import sec.secwatchdog.dao.CityDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;
import org.apache.ibatis.annotations.Mapper;

@Repository("cityDao")
public class CityDaoImpl implements CityDao {
	@Autowired
    private SqlSession session;
	
/*	public CityDaoImpl(){
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
	}
	*/
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provincename,String cityname) {	
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
		cityname = NameConversionUtil.EchartsAreaNameToGov(session, cityname);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		mapparam.put("cityname", cityname);
		//��õ������ǰ��λ(ʡ)
		String statement = "sec.secwatchdog.dao.CityDao.getprovince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provincecode = province.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	
		//��õ������ǰ��λ(��)
		statement = "sec.secwatchdog.dao.CityDao.getcity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String citycode = city.getDistrictcode();
		String citycode0to4 = citycode.substring(0,4);	

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.CityDao.getcityindexinfo";//ӳ��sql�ı�ʶ�ַ���  

		mapparam.put("provincecode0to2", provincecode0to2);
		mapparam.put("citycode0to4", citycode0to4);
		
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
		statement = "sec.secwatchdog.dao.CityDao.getexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med1 = session.selectOne(statement, mapparam);//Ͷҩ����
		statement = "sec.secwatchdog.dao.CityDao.getappexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med2 = session.selectOne(statement, mapparam);//ιʳ����
		int mednumtotal = med1 + med2;//�����ܴ���
		List<Districts> dislist = new ArrayList<Districts>();
		//���������λ���ֿ�ͷ�ı�Ŷ�Ӧ�������������Ϣ
		statement = "sec.secwatchdog.dao.CityDao.ywdisctricts";//ӳ��sql�ı�ʶ�ַ���  
		dislist = session.selectList(statement,mapparam);
		
        //�ء��硢������������
		int countyepidemictotal = 0;
		int villageepidemictotal = 0;
		int hamletepidemictotal = 0;
		
		for(Districts each : dislist) {
			if(each.getEpidemic() == 1) {//������Ϊ������
				//����������뽫������࣬��xx0000000000Ϊʡ��xxxx00000000����������Ϊ�м���xxxxxx000000Ϊ�ؼ���xxxxxxxxx000Ϊ�缶�������λ��ȫΪ0���ʾ�弶
			 if(each.getDistrictcode().substring(6,12).equals("000000")) {
					countyepidemictotal++;
				}
				else if(each.getDistrictcode().substring(9,12).equals("000")) {
					villageepidemictotal++;
				}else
					hamletepidemictotal++;
			}
		
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.CityDao.getmanagerlevel";//ӳ��sql�ı�ʶ�ַ���  
		levellist = session.selectList(statement,mapparam);
		//�С��ء��硢�弶����Ա��
		int cityadmintotal = 0;
		int countyadmintotal = 0;
		int villageadmintotal = 0;
		int hamletadmintotal = 0;

			for(Integer each:levellist) {
				switch(each) {
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
		map.put("countyepidemictotal", countyepidemictotal-1);
		map.put("villageepidemictotal", villageepidemictotal);
		map.put("hamletepidemictotal",  hamletepidemictotal);
		map.put("cityadmintotal", cityadmintotal);
		map.put("countyadmintotal", countyadmintotal);
		map.put("villageadmintotal", villageadmintotal);
		map.put("hamletadmintotal", hamletadmintotal);
		
		statement = "sec.secwatchdog.dao.CityDao.getalldognum";//ӳ��sql�ı�ʶ�ַ���  
		mapparam.put("citycode", citycode);
		int alldognumtotal = session.selectOne(statement,mapparam);
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Integer> GetDivisionIndexLogo(String provincename, String cityname) {
		//������
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
		//ʦ��
		cityname = NameConversionUtil.EchartsAreaNameToGov(session, cityname);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		mapparam.put("cityname", cityname);
		//��õ������ǰ��λ(����)
		String statement = "sec.secwatchdog.dao.CityDao.getprovince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provincecode = province.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	
		//��õ������ǰ��λ(ʦ)
		statement = "sec.secwatchdog.dao.CityDao.getcity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String citycode = city.getDistrictcode();
		String citycode0to4 = citycode.substring(0,4);	

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.CityDao.getcityindexinfo";//ӳ��sql�ı�ʶ�ַ���  

		mapparam.put("provincecode0to2", provincecode0to2);
		mapparam.put("citycode0to4", citycode0to4);
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
		statement = "sec.secwatchdog.dao.CityDao.getexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med1 = session.selectOne(statement, mapparam);//Ͷҩ����
		statement = "sec.secwatchdog.dao.CityDao.getappexhicount";//ӳ��sql�ı�ʶ�ַ���  
		int med2 = session.selectOne(statement, mapparam);//ιʳ����
		int mednumtotal = med1 + med2;//�����ܴ���
		List<Districts> armylist = new ArrayList<Districts>();
		//�����������λ���ֿ�ͷ�ı�Ŷ�Ӧ�������������Ϣ
		statement = "sec.secwatchdog.dao.CityDao.ywdisctricts";//ӳ��sql�ı�ʶ�ַ���  
		armylist = session.selectList(statement,mapparam);
		
		//�ţ�������������
		int regimentalepidemictotal=0;
		int companyepidemictotal=0;
		
		for(Districts each : armylist) {
			if(each.getEpidemic() == 1) {//������Ϊ������
				//����������뽫������࣬��xx000000Ϊ���ţ�xxxx0000����������Ϊʦ����xxxxxx00Ϊ�ż�������λ��ȫΪ0���ʾ����
				if(each.getDistrictcode().substring(6,8).equals("00")) {
					regimentalepidemictotal++;
				}else
					companyepidemictotal++;
			}
		
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.CityDao.getmanagerlevel";//ӳ��sql�ı�ʶ�ַ���  
		levellist = session.selectList(statement,mapparam);
		//ʦ���ţ���������Ա����
        int divisionadmintotal = 0;
        int regimentaladmintotal = 0;
        int companyadmintotal = 0;

			for(Integer each:levellist) {
				switch(each) {
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
		map.put("regimentalepidemictotal", regimentalepidemictotal);
		map.put("companyepidemictotal",  companyepidemictotal);
		map.put("divisionadmintotal", divisionadmintotal);
		map.put("regimentaladmintotal", regimentaladmintotal);
		map.put("companyadmintotal", companyadmintotal);
		
		statement = "sec.secwatchdog.dao.CityDao.getalldognum";//ӳ��sql�ı�ʶ�ַ���  
		mapparam.put("citycode", citycode);
		int alldognumtotal = session.selectOne(statement,mapparam);
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Object> GetCityMap(String provincename, String cityname) {
		provincename = NameConversionUtil.EchartsAreaNameToGov(session,provincename);
		cityname = NameConversionUtil.EchartsAreaNameToGov(session,cityname);
 
		Map<String, Object> map = new HashMap<String,Object>();//�������󷵻ص�����
		Map<String, String> mapparam = new HashMap<String,String>();//sql����
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.CityDao.getprovincemap";//ӳ��sql�ı�ʶ�ַ���   
		String thisprovince = session.selectOne(statement,mapparam);//���ʡ�������
	    String thisprovince1to2 = thisprovince.substring(0, 2);//����ǰ��λ��ʾʡ��
	    mapparam.put("thisprovince1to2", thisprovince1to2);
	    
	    mapparam.put("cityname", cityname);
	    statement = "sec.secwatchdog.dao.CityDao.getcitymap";//ӳ��sql�ı�ʶ�ַ���      
	    String thiscity = session.selectOne(statement,mapparam);//������������
	    String thiscity1to4 = thiscity.substring(0, 4);//����ǰ��λ��ʾ��
	    
	    mapparam.put("thiscity1to4", thiscity1to4);
		statement = "sec.secwatchdog.dao.CityDao.getcountiesandtowns";//ӳ��sql�ı�ʶ�ַ���  
		//��������غ���
		List<Districts> countiesandtowns = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : countiesandtowns)
        { 
			//����ÿ��������
            if (pro.getDistrictcode().endsWith("000000"))
            {
            	//����ÿ���ص������Ϣ
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("countyname", pro.getShortname());
				String county1to6 = pro.getDistrictcode().substring(0, 6);
				int townnum = 0;
				//ͳ��ÿ������������������ĸ���
				for(Districts cn:countiesandtowns) {
					if(cn.getDistrictcode().startsWith(county1to6) && cn.getDistrictcode().endsWith("000") 
							&& !cn.getDistrictcode().equals(county1to6+"000000")) {
						townnum++;
					}
				}
				//������������
				maptemp.put("townnum", townnum);
				//������ع���Ա����
				statement = "sec.secwatchdog.dao.CityDao.getmanagernum";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("districtname", pro.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//������Ȯ����
				statement = "sec.secwatchdog.dao.CityDao.getallnecketid";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("county1to6", county1to6);
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
				statement = "sec.secwatchdog.dao.CityDao.getcountexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//����ιʳ������
				statement = "sec.secwatchdog.dao.CityDao.getcountappexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int feednum = session.selectOne(statement,mapparam);
				maptemp.put("feedernum", feednum);			
				
				map.put(""+i, maptemp);
				
				i++;
            }
        }

		return map;
	}

	@Override
	public Map<String, Object> GetArmyCityMap(String provincename, String cityname) {
		provincename = NameConversionUtil.EchartsAreaNameToGov(session,provincename);
		cityname = NameConversionUtil.EchartsAreaNameToGov(session,cityname);
 
		Map<String, Object> map = new HashMap<String,Object>();//�������󷵻ص�����
		Map<String, String> mapparam = new HashMap<String,String>();//sql����
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.CityDao.getprovincemap";//ӳ��sql�ı�ʶ�ַ���   
		String thisprovince = session.selectOne(statement,mapparam);//���ʡ�������
	    String thisprovince1to2 = thisprovince.substring(0, 2);//����ǰ��λ��ʾʡ��
	    mapparam.put("thisprovince1to2", thisprovince1to2);
	    
	    mapparam.put("cityname", cityname);
	    statement = "sec.secwatchdog.dao.CityDao.getcitymap";//ӳ��sql�ı�ʶ�ַ���      
	    String thiscity = session.selectOne(statement,mapparam);//������������
	    String thiscity1to4 = thiscity.substring(0, 4);//����ǰ��λ��ʾ��
	    
	    mapparam.put("thiscity1to4", thiscity1to4);
		statement = "sec.secwatchdog.dao.CityDao.getregimentalandcompany";//ӳ��sql�ı�ʶ�ַ���  
		//��������ź���
		List<Districts> regimentalandcompany = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : regimentalandcompany)
        { 
			//����ÿ��������
            if (pro.getDistrictcode().endsWith("00"))
            {
            	//����ÿ���ŵ������Ϣ
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("regimentalname", pro.getShortname());
				String regimental1to6 = pro.getDistrictcode().substring(0, 6);
				int companynum = 0;
				//ͳ��ÿ�������������������ĸ���
				for(Districts cn:regimentalandcompany) {
					if(cn.getDistrictcode().startsWith(regimental1to6) && !cn.getDistrictcode().equals(regimental1to6+"00")) {
						companynum++;
					}
				}
				//������������
				maptemp.put("companynum", companynum);
				//������Ź���Ա����
				statement = "sec.secwatchdog.dao.CityDao.getmanagernum";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("districtname", pro.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//������Ȯ����
				statement = "sec.secwatchdog.dao.CityDao.getarmyallnecketid";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("regimental1to6", regimental1to6);
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
				maptemp.put("lng", pro.getLng());
				maptemp.put("lat", pro.getLat());
				//����Ͷҩ�ܴ���
				statement = "sec.secwatchdog.dao.CityDao.getarmycountexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//����ιʳ������
				statement = "sec.secwatchdog.dao.CityDao.getarmycountappexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int feednum = session.selectOne(statement,mapparam);
				maptemp.put("feedernum", feednum);			
				
				map.put(""+i, maptemp);
				
				i++;
            }
        }

		return map;
	}

	@Override
	public Map<String, Object> GetDistrictcode(String provincename, String cityname) {
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> mapparam = new HashMap<String,Object>();
	
		mapparam.put("provincename", provincename);
		mapparam.put("cityname", cityname);
		//��õ������ǰ��λ(���Ż�ʡ)
		String statement = "sec.secwatchdog.dao.CityDao.getprovince";//ӳ��sql�ı�ʶ�ַ���  
		Districts province = session.selectOne(statement,mapparam);
		String provincecode = province.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	
		mapparam.put("provincecode0to2", provincecode0to2);
		//��õ������(ʦ����)
		statement = "sec.secwatchdog.dao.CityDao.getcity";//ӳ��sql�ı�ʶ�ַ���  
		Districts city = session.selectOne(statement,mapparam);
		String citycode = city.getDistrictcode();
		map.put("cityGov",NameConversionUtil.EchartsAreaNameToGov(session, cityname));
		map.put("cityEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, cityname).replace("*",""));
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provincename));
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provincename).replace("*",""));
		map.put("districtcode",citycode);
		return map;
	}
 
 


}
