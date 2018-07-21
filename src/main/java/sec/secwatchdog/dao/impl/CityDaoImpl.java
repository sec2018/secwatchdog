package sec.secwatchdog.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sec.secwatchdog.dao.CityDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;

@Repository("cityDao")
public class CityDaoImpl implements CityDao {
	@Autowired
    private SqlSession session;
	
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provincename,String cityname) {	
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
		cityname = NameConversionUtil.EchartsAreaNameToGov(session, cityname);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		mapparam.put("cityname", cityname);
		//获得该地区地区编码前两位(省)
		String statement = "sec.secwatchdog.dao.CityDao.getprovince";
		Districts province = session.selectOne(statement,mapparam);
		String provincecode = province.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	
		//获得该地区地区编码前四位(市)
		statement = "sec.secwatchdog.dao.CityDao.getcity";
		Districts city = session.selectOne(statement,mapparam);
		String citycode = city.getDistrictcode();
		String citycode0to4 = citycode.substring(0,4);	

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.CityDao.getcityindexinfo";

		mapparam.put("provincecode0to2", provincecode0to2);
		mapparam.put("citycode0to4", citycode0to4);
		
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
		statement = "sec.secwatchdog.dao.CityDao.getexhicount";
		//投药次数
		int med1 = session.selectOne(statement, mapparam);
		statement = "sec.secwatchdog.dao.CityDao.getappexhicount";
		//喂食次数
		int med2 = session.selectOne(statement, mapparam);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面四位数字开头的编号所对应的所有区域的信息
		statement = "sec.secwatchdog.dao.CityDao.ywdisctricts";
		dislist = session.selectList(statement,mapparam);
		
		 //县、乡、村流行区数量
		int countyepidemictotal = 0;
		int villageepidemictotal = 0;
		int hamletepidemictotal = 0;
		
		for(Districts each : dislist) {
			//该区域为流行区
			if(each.getEpidemic() == 1) {
			//根据区域编码将区域分类，如xx0000000000为省，xxxx00000000表明该区域为市级，xxxxxx000000为县级，xxxxxxxxx000为乡级，最后三位不全为0则表示村级
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
		statement = "sec.secwatchdog.dao.CityDao.getmanagerlevel";
		levellist = session.selectList(statement,mapparam);
		//市、县、乡、村级管理员数
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
		//将数据保存到map中
		map.put("countyepidemictotal", countyepidemictotal-1);
		map.put("villageepidemictotal", villageepidemictotal);
		map.put("hamletepidemictotal",  hamletepidemictotal);
		map.put("cityadmintotal", cityadmintotal);
		map.put("countyadmintotal", countyadmintotal);
		map.put("villageadmintotal", villageadmintotal);
		map.put("hamletadmintotal", hamletadmintotal);
		
		statement = "sec.secwatchdog.dao.CityDao.getalldognum";
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
		//兵团名
		provincename = NameConversionUtil.EchartsAreaNameToGov(session, provincename);
		//师名
		cityname = NameConversionUtil.EchartsAreaNameToGov(session, cityname);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		mapparam.put("cityname", cityname);
		//获得该地区地区编码前两位(兵团)
		String statement = "sec.secwatchdog.dao.CityDao.getprovince"; 
		Districts province = session.selectOne(statement,mapparam);
		String provincecode = province.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	
		//获得该地区地区编码前四位(师)
		statement = "sec.secwatchdog.dao.CityDao.getcity";
		Districts city = session.selectOne(statement,mapparam);
		String citycode = city.getDistrictcode();
		String citycode0to4 = citycode.substring(0,4);	

		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.CityDao.getcityindexinfo";

		mapparam.put("provincecode0to2", provincecode0to2);
		mapparam.put("citycode0to4", citycode0to4);
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
		statement = "sec.secwatchdog.dao.CityDao.getexhicount";
		//投药次数
		int med1 = session.selectOne(statement, mapparam);
		statement = "sec.secwatchdog.dao.CityDao.getappexhicount";
		//喂食次数
		int med2 = session.selectOne(statement, mapparam);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> armylist = new ArrayList<Districts>();
		//获得以上面四位数字开头的编号所对应的所有区域的信息
		statement = "sec.secwatchdog.dao.CityDao.ywdisctricts";
		armylist = session.selectList(statement,mapparam);
		
		//团、连流行区数量
		int regimentalepidemictotal=0;
		int companyepidemictotal=0;
		
		for(Districts each : armylist) {
			//该区域为流行区
			if(each.getEpidemic() == 1) {
				//根据区域编码将区域分类，如xx000000为兵团，xxxx0000表明该区域为师级，xxxxxx00为团级，最后两位不全为0则表示连级
				if(each.getDistrictcode().substring(6,8).equals("00")) {
					regimentalepidemictotal++;
				}else
					companyepidemictotal++;
			}
		
		}
		List<Integer> levellist = new ArrayList<Integer>();
		statement = "sec.secwatchdog.dao.CityDao.getmanagerlevel";
		levellist = session.selectList(statement,mapparam);
		//师、团、连级管理员数量
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
		
		//将数据保存到map
		map.put("regimentalepidemictotal", regimentalepidemictotal);
		map.put("companyepidemictotal",  companyepidemictotal);
		map.put("divisionadmintotal", divisionadmintotal);
		map.put("regimentaladmintotal", regimentaladmintotal);
		map.put("companyadmintotal", companyadmintotal);
		
		statement = "sec.secwatchdog.dao.CityDao.getalldognum";
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
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//sql语句的参数
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.CityDao.getprovincemap";
		//获得省区域编码
		String thisprovince = session.selectOne(statement,mapparam);
		//编码前两位表示省份
	    String thisprovince1to2 = thisprovince.substring(0, 2);
	    mapparam.put("thisprovince1to2", thisprovince1to2);
	    
	    mapparam.put("cityname", cityname);
	    statement = "sec.secwatchdog.dao.CityDao.getcitymap";
	    //获得市区域编码
	    String thiscity = session.selectOne(statement,mapparam);
	    //编码前四位表示市
	    String thiscity1to4 = thiscity.substring(0, 4);
	    
	    mapparam.put("thiscity1to4", thiscity1to4);
		statement = "sec.secwatchdog.dao.CityDao.getcountiesandtowns";
		//获得流行县和乡
		List<Districts> countiesandtowns = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : countiesandtowns)
        { 
			//对于每个流行县
            if (pro.getDistrictcode().endsWith("000000"))
            {
            	//保存每个县的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("countyname", pro.getShortname());
				String county1to6 = pro.getDistrictcode().substring(0, 6);
				int townnum = 0;
				//统计该县流行乡的个数
				for(Districts cn:countiesandtowns) {
					if(cn.getDistrictcode().startsWith(county1to6) && cn.getDistrictcode().endsWith("000") 
							&& !cn.getDistrictcode().equals(county1to6+"000000")) {
						townnum++;
					}
				}
				
				maptemp.put("townnum", townnum);
				//该县管理员总数
				statement = "sec.secwatchdog.dao.CityDao.getmanagernum";
				mapparam.put("districtname", pro.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//牧犬总数
				statement = "sec.secwatchdog.dao.CityDao.getallnecketid"; 
				mapparam.put("county1to6", county1to6);
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
				statement = "sec.secwatchdog.dao.CityDao.getcountexhibitrealtime";
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//喂食器数量
				statement = "sec.secwatchdog.dao.CityDao.getcountappexhibitrealtime";
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
 
		Map<String, Object> map = new HashMap<String,Object>();
		//sql语句参数
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provincename", provincename);
		String statement = "sec.secwatchdog.dao.CityDao.getprovincemap";
		//获得兵团区域编码
		String thisprovince = session.selectOne(statement,mapparam);
	    String thisprovince1to2 = thisprovince.substring(0, 2);
	    mapparam.put("thisprovince1to2", thisprovince1to2);
	    
	    mapparam.put("cityname", cityname);
	    statement = "sec.secwatchdog.dao.CityDao.getcitymap";  
	    //获得师区域编码
	    String thiscity = session.selectOne(statement,mapparam);
	    String thiscity1to4 = thiscity.substring(0, 4);
	    
	    mapparam.put("thiscity1to4", thiscity1to4);
		statement = "sec.secwatchdog.dao.CityDao.getregimentalandcompany";  
		 //获得流行团和连
		List<Districts> regimentalandcompany = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : regimentalandcompany)
        { 
			//对于每个流行团
            if (pro.getDistrictcode().endsWith("00"))
            {
            	//保存每个团的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("regimentalname", pro.getShortname());
				String regimental1to6 = pro.getDistrictcode().substring(0, 6);
				int companynum = 0;
				//该团所属的流行连的个数
				for(Districts cn:regimentalandcompany) {
					if(cn.getDistrictcode().startsWith(regimental1to6) && !cn.getDistrictcode().equals(regimental1to6+"00")) {
						companynum++;
					}
				}
				maptemp.put("companynum", companynum);
				//该团管理员总数
				statement = "sec.secwatchdog.dao.CityDao.getmanagernum";//ӳ��sql�ı�ʶ�ַ���  
				mapparam.put("districtname", pro.getDistrictname());
				int managernum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managernum);
				//牧犬总数
				statement = "sec.secwatchdog.dao.CityDao.getarmyallnecketid";
				mapparam.put("regimental1to6", regimental1to6);
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
				maptemp.put("lng", pro.getLng());
				maptemp.put("lat", pro.getLat());
				//投药次数
				statement = "sec.secwatchdog.dao.CityDao.getarmycountexhibitrealtime";
				int mednum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", mednum);
				//喂食次数
				statement = "sec.secwatchdog.dao.CityDao.getarmycountappexhibitrealtime";
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
		//获得该地区地区编码前两位(省)
		String statement = "sec.secwatchdog.dao.CityDao.getprovince";
		Districts province = session.selectOne(statement,mapparam);
		String provincecode = province.getDistrictcode();
		String provincecode0to2 = provincecode.substring(0,2);	
		mapparam.put("provincecode0to2", provincecode0to2);
		//获得该地区地区编码
		statement = "sec.secwatchdog.dao.CityDao.getcity";
		Districts city = session.selectOne(statement,mapparam);
		String citycode = city.getDistrictcode();
		//该市或师的官方名
		map.put("cityGov",NameConversionUtil.EchartsAreaNameToGov(session, cityname));
		//该市或师的echart地图所用名
		map.put("cityEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, cityname).replace("*",""));
		//该省或军区的官方名
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provincename));
		//该省或军区的echart地图所用名
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provincename).replace("*",""));
		map.put("districtcode",citycode);
		return map;
	}
 
 


}
