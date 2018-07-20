package sec.secwatchdog.dao.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import sec.secwatchdog.dao.UserDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.AESUtil;
import org.apache.ibatis.annotations.Mapper;

@Repository("userDao")
public class UserDaoImpl implements UserDao{
	@Autowired
	private SqlSession session;
	
//	public UserDaoImpl(){
//		//ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���  
//        String resource = "mybatis-config.xml";      
//        Reader reader;
//		try {
//			reader = Resources.getResourceAsReader(resource);
//			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();      
//	        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);  
//	        session = sqlSessionFactory.openSession(); 
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}
	
	@Override
	public Managers login(Managers manager) {
		Managers resultUser = null;
        Map<String, Object> map = new HashMap<String, Object>();
        AESUtil util = new AESUtil(); // ��Կ 
//        String password ="";
//		try {
//			password = util.encryptData(manager.getPassword());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        map.put("username", manager.getUsername());
        /** 
         * ӳ��sql�ı�ʶ�ַ���org.guus.mapping.userMapper.getUser 
         * getUser��select��ǩ��id����ֵ��ͨ��select��ǩ��id����ֵ�Ϳ����ҵ�Ҫִ�е�SQL 
         */  
        String statement = "sec.secwatchdog.dao.UserDao.login";//ӳ��sql�ı�ʶ�ַ���  
        //ִ�в�ѯ����һ��Ψһuser�����sql  
        resultUser = session.selectOne(statement,map);
        try {
			resultUser.setPassword(util.decryptData(resultUser.getPassword()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultUser;
	}
	
	@Override
	public Managers checklogin(String username) {
		Managers resultUser = null;
        /** 
         * ӳ��sql�ı�ʶ�ַ���org.guus.mapping.userMapper.getUser 
         * getUser��select��ǩ��id����ֵ��ͨ��select��ǩ��id����ֵ�Ϳ����ҵ�Ҫִ�е�SQL 
         */  
        String statement = "sec.secwatchdog.dao.UserDao.checklogin";//ӳ��sql�ı�ʶ�ַ���  
        //ִ�в�ѯ����һ��Ψһuser�����sql  
        resultUser = session.selectOne(statement,username);
		return resultUser;
	}

	@Override
	public Map<String, Integer> GetIndexLogoInfo(Managers manager) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String,Integer>();
		if(manager.getPrivilegelevel() == 1) {
			List<Sheepdogs> sdlist = null;
			String statement = "sec.secwatchdog.dao.UserDao.getcountryindexinfo";//ӳ��sql�ı�ʶ�ַ���  
	        //ִ�в�ѯ����һ��Ψһuser�����sql  
			sdlist = session.selectList(statement);
			int dognumtotal = 0;
			int feedernumtotal = 0;
			for(Sheepdogs each:sdlist){
				if(!each.getNeckletid().equals("-1")) {
					dognumtotal++;
				}
				if(!each.getApparatusid().equals("-1")) {
					feedernumtotal++;
				}
			}
			statement = "sec.secwatchdog.dao.UserDao.getexhicount";//ӳ��sql�ı�ʶ�ַ���  
			int med1 = session.selectOne(statement);
			statement = "sec.secwatchdog.dao.UserDao.getappexhicount";//ӳ��sql�ı�ʶ�ַ���  
			int med2 = session.selectOne(statement);
			int mednumtotal = med1 + med2;
			
			List<Districts> alllist = new ArrayList<Districts>();
			List<Districts> dislist = new ArrayList<Districts>();
			List<Districts> armylist = new ArrayList<Districts>(); 
			statement = "sec.secwatchdog.dao.UserDao.ywdisctricts";//ӳ��sql�ı�ʶ�ַ���  
			alllist = session.selectList(statement);
			for(Districts al : alllist)
            {
                if (al.getDistrictcode().startsWith("66"))
                {
                    armylist.add(al);
                }
                else
                {
                    dislist.add(al);
                }
            }
			
			int armyepidemictotal = 1;
			
			int provinceepidemictotal = 0;
			int cityepidemictotal = 0;
			
			int countyepidemictotal = 0;
			int villageepidemictotal = 0;
			int hamletepidemictotal = 0;
			
			for(Districts each : dislist) {
				if(each.getEpidemic() == 1) {
					hamletepidemictotal++;
					if(each.getDistrictcode().substring(2, 12).equals("0000000000")) {
						provinceepidemictotal++;
					}
					if(each.getDistrictcode().substring(4, 12).equals("00000000")) {
						cityepidemictotal ++;
					}
					if(each.getDistrictcode().substring(6,12).equals("000000")) {
						countyepidemictotal ++;
					}
					if(each.getDistrictcode().substring(9,12).equals("000")) {
						villageepidemictotal ++;
					}
				}
			}
			
			int divisionepidemictotal=0;
			int regimentalepidemictotal =0;
			int companyepidemictotal = 0;
			for(Districts each : armylist) {
				if(each.getEpidemic() == 1) {
					companyepidemictotal++;
					if(each.getDistrictcode().substring(4,8).equals("0000")) {
						divisionepidemictotal++;
					}
					if(each.getDistrictcode().substring(6,8).equals("00")) {
						regimentalepidemictotal++;
					}
				}
			}
			
			List<Integer> levellist = new ArrayList<Integer>();
			int countryadmintotal = 0;
			int provinceadmintotal = 0;
			int cityadmintotal = 0;
			int countyadmintotal = 0;
			int villageadmintotal = 0;
			int hamletadmintotal = 0;
			statement = "sec.secwatchdog.dao.UserDao.getmanagerlevel";//ӳ��sql�ı�ʶ�ַ���  
			levellist = session.selectList(statement);
			for(Integer each:levellist) {
				switch(each) {
				case 1:
                    countryadmintotal++;
                    break;
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
			map.put("provinceepidemictotal", provinceepidemictotal + armyepidemictotal);
			map.put("cityepidemictotal", cityepidemictotal - provinceepidemictotal + (divisionepidemictotal - 1));
			map.put("countyepidemictotal", countyepidemictotal - cityepidemictotal + (regimentalepidemictotal - divisionepidemictotal));
			map.put("villageepidemictotal", villageepidemictotal - countyepidemictotal + (companyepidemictotal - regimentalepidemictotal));
			map.put("hamletepidemictotal",  hamletepidemictotal - villageepidemictotal - companyepidemictotal);
			map.put("countryadmintotal", countryadmintotal);
			map.put("provinceadmintotal", provinceadmintotal);
			map.put("cityadmintotal", cityadmintotal);
			map.put("countyadmintotal", countyadmintotal);
			map.put("villageadmintotal", villageadmintotal);
			map.put("hamletadmintotal", hamletadmintotal);
			map.put("countrydognumtotal", dognumtotal);
			map.put("alldognumtotal", 3280564);
			map.put("countrymednumtotal", mednumtotal);
			map.put("feedernumtotal", feedernumtotal);
		}
		return map;
	}

	public Map<String,Object> GetCountryMap(){
		Map<String, Object> map = new HashMap<String,Object>();
		String statement = "sec.secwatchdog.dao.UserDao.getcountrymap";//ӳ��sql�ı�ʶ�ַ���  
		List<Districts> provincelist = new ArrayList<Districts>();
		provincelist = session.selectList(statement);
		int i = 0;
		for(Districts each:provincelist) {
			if(each.getDistrictcode().endsWith("0000000000")) {
				Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("provincename", each.getShortname());
				String pro1to2 = each.getDistrictcode().substring(0, 2);
				int citynum = 0;
				for(Districts each2:provincelist) {
					if(each2.getDistrictcode().startsWith(pro1to2) && each2.getDistrictcode().endsWith("00000000") 
							&& !each2.getDistrictcode().equals(pro1to2+"0000000000") && each2.getEpidemic() == 1) {
						citynum++;
					}
				}
				maptemp.put("citynum", citynum);
				statement = "sec.secwatchdog.dao.UserDao.getmanagernum";//ӳ��sql�ı�ʶ�ַ���  
				Map<String, String> mapparam = new HashMap<String,String>();
				mapparam.put("districtname", each.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				statement = "sec.secwatchdog.dao.UserDao.getallnecketid";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("pro1to2", pro1to2);
				List<String> dognumlist = session.selectList(statement,mapparam);
				maptemp.put("dognum", dognumlist.size());
				int neckletnum = 0;
				for(String each3:dognumlist) {
					if(!each3.equals("-1")) {
						neckletnum++;
					}
				}
				maptemp.put("neckletnum", neckletnum);
				statement = "sec.secwatchdog.dao.UserDao.getcountexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				statement = "sec.secwatchdog.dao.UserDao.getcountappexhibitrealtime";//ӳ��sql�ı�ʶ�ַ���  
				int feednum = session.selectOne(statement,mapparam);
				maptemp.put("feedernum", feednum);
				
				map.put(""+i, maptemp);
				i++;
			}
		}
		return map;
	}
	
	public Map<String,Object> GetXinJiangArmyCountryMap(){
		Map<String, Object> map = new HashMap<String,Object>();
		String statement = "sec.secwatchdog.dao.UserDao.getdivisionnum";//ӳ��sql�ı�ʶ�ַ���  
		int divisionnum = session.selectOne(statement);
		map.put("armyname","�������");
		map.put("divisionnum", divisionnum);
		statement = "sec.secwatchdog.dao.UserDao.getarmymanagernum";//ӳ��sql�ı�ʶ�ַ���  
		int managernum = session.selectOne(statement);
		map.put("managernum", managernum);
		statement = "sec.secwatchdog.dao.UserDao.getarmydognum";//ӳ��sql�ı�ʶ�ַ���  
		List<String> dognumlist = session.selectList(statement);
		map.put("dognum", dognumlist.size());
		int neckletnum = 0;
		for(String each:dognumlist) {
			if(each != "-1") {
				neckletnum++;
			}
		}
		map.put("neckletnum", neckletnum);
		statement = "sec.secwatchdog.dao.UserDao.getarmyposition";//ӳ��sql�ı�ʶ�ַ���  
		List<Districts> positionlist = session.selectList(statement);
		Districts position = positionlist.get(0);
		map.put("lng", position.getLng());
		map.put("lat", position.getLat());
		statement = "sec.secwatchdog.dao.UserDao.getarmymednum";//ӳ��sql�ı�ʶ�ַ���  
		int mednum = session.selectOne(statement);
		map.put("mednum", mednum);
		statement = "sec.secwatchdog.dao.UserDao.getarmyfeedernum";//ӳ��sql�ı�ʶ�ַ���  
		int feedernum = session.selectOne(statement);
		map.put("feedernum", feedernum);
		return map;
	}
}
