package sec.secwatchdog.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sec.secwatchdog.dao.ProvinceDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;


@Repository("provinceDao")
public class ProvinceDaoImpl implements ProvinceDao {
	@Autowired
    private SqlSession session;
	
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provincename) {	
		 
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Districts districtsist = null;
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement="";
		//获得该地区地区编码前两位(省)
		statement = "sec.secwatchdog.dao.ProvinceDao.getdistrictsist";
		districtsist = session.selectOne(statement,mapparam);
		String provincecode = districtsist.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.ProvinceDao.getprovinceindexinfo"; 
		mapparam.put("provincecode0to2", provincecode0to2);
		sdlist = session.selectList(statement, mapparam);
		//佩戴项圈牧犬数量和喂食器数量
		int neckdognumtotal = 0;
		int feedernumtotal = 0;
		for(Sheepdogs each:sdlist){
			//"-1"表示未佩戴项圈
			if(!each.getNeckletid().equals("-1")) {
				neckdognumtotal++;
			}
			//"-1"表示无喂食器
			if(!each.getApparatusid().equals("-1")) {
				feedernumtotal++;
			}
		}
		statement = "sec.secwatchdog.dao.ProvinceDao.getexhicount";
		//投药次数
		int med1 = session.selectOne(statement, mapparam);
		statement = "sec.secwatchdog.dao.ProvinceDao.getappexhicount"; 
		//喂食次数
		int med2 = session.selectOne(statement, mapparam);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面两位数字开头的编号所对应的所有区域的信息
		statement = "sec.secwatchdog.dao.ProvinceDao.ywdisctricts";
		dislist = session.selectList(statement,mapparam);
		
        //市、县、乡、村流行区数量
		int cityepidemictotal = 0;	
		int countyepidemictotal = 0;
		int villageepidemictotal = 0;
		int hamletepidemictotal = 0;
		
		for(Districts each : dislist) {
			//该区域为流行区
			if(each.getEpidemic() == 1) {
				//根据区域编码将区域分类，如xx0000000000为省，xxxx00000000表明该区域为市级，xxxxxx000000为县级，xxxxxxxxx000为乡级，最后三位不全为0则表示村级
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
		statement = "sec.secwatchdog.dao.ProvinceDao.getmanagerlevel";
		levellist = session.selectList(statement,mapparam);
		//省、市、乡、乡、村级管理员数
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
		
		//将数据保存到map中
		map.put("cityepidemictotal", cityepidemictotal-1);
		map.put("countyepidemictotal", countyepidemictotal);
		map.put("villageepidemictotal", villageepidemictotal);
		map.put("hamletepidemictotal",  hamletepidemictotal);
		map.put("provinceadmintotal", provinceadmintotal);
		map.put("cityadmintotal", cityadmintotal);
		map.put("countyadmintotal", countyadmintotal);
		map.put("villageadmintotal", villageadmintotal);
		map.put("hamletadmintotal", hamletadmintotal);
		
		statement = "sec.secwatchdog.dao.ProvinceDao.getalldognum";
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
		//获得该地区地区编码前两位(兵团)
		statement = "sec.secwatchdog.dao.ProvinceDao.getdistrictsist"; 
		districtsist = session.selectOne(statement,mapparam);
		String provincecode = districtsist.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);
		

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.ProvinceDao.getprovinceindexinfo"; 
		mapparam.put("provincecode0to2", provincecode0to2);
		sdlist = session.selectList(statement, mapparam);
		//佩戴项圈牧犬数量和喂食器数量
		int neckdognumtotal = 0;
		int feedernumtotal = 0;
		for(Sheepdogs each:sdlist){
			//"-1"表示未佩戴项圈
			if(!each.getNeckletid().equals("-1")) {
				neckdognumtotal++;
			}
			//"-1"表示无喂食器
			if(!each.getApparatusid().equals("-1")) {
				feedernumtotal++;
			}
		}
		statement = "sec.secwatchdog.dao.ProvinceDao.getexhicount"; 
		//投药次数
		int med1 = session.selectOne(statement, mapparam);
		statement = "sec.secwatchdog.dao.ProvinceDao.getappexhicount"; 
		//喂食次数
		int med2 = session.selectOne(statement, mapparam);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> armylist = new ArrayList<Districts>(); 
		//获得以上面两位数字开头的编号所对应的所有区域的信息
		statement = "sec.secwatchdog.dao.ProvinceDao.ywdisctricts"; 
		armylist = session.selectList(statement,mapparam);
		
		//师、团、连流行区数量
		int divisionepidemictotal=0;
		int regimentalepidemictotal=0;
		int companyepidemictotal=0;
		
		for(Districts each : armylist) {
			//该区域为流行区
			if(each.getEpidemic() == 1) {
				//根据区域编码将区域分类，如xx000000为兵团，xxxx0000表明该区域为师级，xxxxxx00为团级，最后两位不全为0则表示连级
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
		statement = "sec.secwatchdog.dao.ProvinceDao.getmanagerlevel"; 
		levellist = session.selectList(statement,mapparam);
		//兵团、师、团、连级管理员数量
		int armyadmintotal = 0;
        int divisionadmintotal = 0;
        int regimentaladmintotal = 0;
        int companyadmintotal = 0;

			 for (Integer each:levellist)
	           {
	               switch (each)
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
		
		//将数据保存到map
		map.put("divisionepidemictotal", divisionepidemictotal-1);
        map.put("regimentalepidemictotal", regimentalepidemictotal);
        map.put("companyepidemictotal",companyepidemictotal);
		map.put("armyadmintotal", armyadmintotal);
        map.put("divisionadmintotal", divisionadmintotal);
        map.put("regimentaladmintotal",regimentaladmintotal);
        map.put("companyadmintotal", companyadmintotal);
		
		statement = "sec.secwatchdog.dao.ProvinceDao.getalldognum";
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
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//sql语句的参数
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.ProvinceDao.getprovincemap";
		//获得省区域编码
		String thisprovince = session.selectOne(statement,mapparam);
		//编码前两位表示省份
	    String thisprovince1to2 = thisprovince.substring(0, 2);
	    mapparam.put("thisprovince1to2", thisprovince1to2);
		statement = "sec.secwatchdog.dao.ProvinceDao.getcitiesandcounties";
		//获得流行市和县
		List<Districts> citiesandcounties = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : citiesandcounties)
        { 
			//对于每个流行市
            if (pro.getDistrictcode().endsWith("00000000"))
            {
            	//保存每个市的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("cityname", pro.getShortname());
				String city1to4 = pro.getDistrictcode().substring(0, 4);
				//统计该市流行县的个数
				int countynum = 0;		
				for(Districts cn:citiesandcounties) {
					if(cn.getDistrictcode().startsWith(city1to4) && cn.getDistrictcode().endsWith("000000") 
							&& !cn.getDistrictcode().equals(city1to4+"00000000")) {
						countynum++;
					}
				}
				maptemp.put("countynum", countynum);
				//该市管理员总数
				statement = "sec.secwatchdog.dao.ProvinceDao.getmanagernum";
				mapparam.put("districtname", pro.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//牧犬总数
				statement = "sec.secwatchdog.dao.ProvinceDao.getallnecketid";
				mapparam.put("city1to4", city1to4);
				List<String> dognumlist = session.selectList(statement,mapparam);
				maptemp.put("dognum", dognumlist.size());
				//项圈总数
				int neckletnum = 0;
				for(String n1:dognumlist) {
					if(!n1.equals("-1")) {
						neckletnum++;
					}
				}
				maptemp.put("neckletnum", neckletnum);
				//投药总次数
				statement = "sec.secwatchdog.dao.ProvinceDao.getcountexhibitrealtime";
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//喂食器数量
				statement = "sec.secwatchdog.dao.ProvinceDao.getcountappexhibitrealtime";
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
		//sql语句参数
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.ProvinceDao.getprovincemap"; 
		//获得兵团区域编码
		String thisprovince = session.selectOne(statement,mapparam);
	    String thisprovince1to2 = thisprovince.substring(0, 2);
	    mapparam.put("thisprovince1to2", thisprovince1to2);
	    //获得流行师和团
		statement = "sec.secwatchdog.dao.ProvinceDao.getdivisionsandregimental";
		List<Districts> divisionsandregimental = session.selectList(statement,mapparam);
		int i=0;
		for(Districts divisions : divisionsandregimental)
        { 
			//对于每个流行师
            if (divisions.getDistrictcode().endsWith("0000"))
            {
            	//保存每个师的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("divisionname", divisions.getDistrictname());
				String division1to4 = divisions.getDistrictcode().substring(0, 4);
				int regimentalnum = 0;
				//该师所属的流行团的个数
				for(Districts rt:divisionsandregimental) {
					if(rt.getDistrictcode().startsWith(division1to4) && rt.getDistrictcode().endsWith("00") 
							&& !rt.getDistrictcode().equals(division1to4+"0000")) {					 
						regimentalnum++;
					}
				}
				maptemp.put("regimentalnum", regimentalnum);
				//该师管理员总数
				statement = "sec.secwatchdog.dao.ProvinceDao.getmanagernum"; 
				mapparam.put("districtname", divisions.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//牧犬总数
				statement = "sec.secwatchdog.dao.ProvinceDao.getarmyallnecketid";  
				mapparam.put("division1to4", division1to4);
				List<String> dognumlist = session.selectList(statement,mapparam);
				maptemp.put("dognum", dognumlist.size());
				//项圈总数
				int neckletnum = 0;
				for(String n1:dognumlist) {
					if(!n1.equals("-1")) {
						neckletnum++;
					}
				}
				maptemp.put("neckletnum", neckletnum);
				//经纬度
				maptemp.put("lng", divisions.getLng());
				maptemp.put("lat", divisions.getLat());
				//投药次数
				statement = "sec.secwatchdog.dao.ProvinceDao.getarmycountexhibitrealtime";
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//喂食次数
				statement = "sec.secwatchdog.dao.ProvinceDao.getarmycountappexhibitrealtime";
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
		String statement = "sec.secwatchdog.dao.ProvinceDao.getdistrictsist";
		
		districtsist = session.selectOne(statement,mapparam);
		String provincecode = districtsist.getDistrictcode();
		//该省或军区的官方名
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provincename));
		//该省或军区的echart地图所用名
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provincename).replace("*",""));
		map.put("districtcode",provincecode);
		return map;
	}
 


}
