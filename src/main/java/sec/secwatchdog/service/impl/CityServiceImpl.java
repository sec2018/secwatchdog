package sec.secwatchdog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.AppexhibitrealtimeDao;
import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.ExhibitrealtimeDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.CityService;
import sec.secwatchdog.util.NameConversionUtil;

@Service("cityService")
public class CityServiceImpl implements CityService {

	@Autowired
	private DistrictsDao districtsDao;
	@Autowired
	private SheepdogsDao sheepdogsDao;
	@Autowired
	private ExhibitrealtimeDao exhibitrealtimeDao;
	@Autowired 
	private AppexhibitrealtimeDao appexhibitrealtimeDao;
	@Autowired
	private ManagersDao managersDao;
	@Autowired
	private NameConversionUtil nameConversionUtil;
	
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provinceName, String cityName) {
		provinceName = nameConversionUtil.EchartsAreaNameToGov(provinceName);
		cityName = nameConversionUtil.EchartsAreaNameToGov(cityName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 	

		//获得该地区地区编码前两位(省)
		Districts province = districtsDao.getDistrictsByDistrictName(provinceName);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		//获得该地区地区编码前四位(市)
		Districts city = districtsDao.getCityDistrictsByDistrictName(cityName, provinceCode0to2);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	

		List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(cityCode0to4);;
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
		//投药次数
		int med1 = exhibitrealtimeDao.getExhiCountByDistrictcode(cityCode0to4);
		//喂食次数
		int med2 = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(cityCode0to4);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面四位数字开头的编号所对应的所有区域的信息
		dislist = districtsDao.getDistrictsByDistrictcode(cityCode0to4);
		
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
		levellist = managersDao.getCityManagerLevelByDistrictName(provinceName, cityName);
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
		
		int alldognumtotal = sheepdogsDao.getAllDogNumByDistrictCode(cityCode0to4);
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Integer> GetDivisionIndexLogo(String armyName,String divisionName) {
		//兵团名
		armyName = nameConversionUtil.EchartsAreaNameToGov(armyName);
		//师名
		divisionName = nameConversionUtil.EchartsAreaNameToGov(divisionName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		//获得该地区地区编码前两位(兵团)
		Districts districtsist = districtsDao.getDistrictsByDistrictName(armyName);
		String armyCode = districtsist.getDistrictcode();
		String armyCode0to2 = armyCode.substring(0,2);
		//获得该地区地区编码前四位(师)
		Districts division = districtsDao.getCityDistrictsByDistrictName(divisionName, armyCode0to2);
		String divisionCode = division.getDistrictcode();
		String divisionCode0to4 = divisionCode.substring(0,4);	

		List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(divisionCode0to4);;

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
		//投药次数
		int med1 = exhibitrealtimeDao.getExhiCountByDistrictcode(divisionCode0to4);
		//喂食次数
		int med2 = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(divisionCode0to4);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> armylist = new ArrayList<Districts>();
		//获得以上面四位数字开头的编号所对应的所有区域的信息
		armylist = districtsDao.getDistrictsByDistrictcode(divisionCode0to4);
		
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
		levellist = managersDao.getCityManagerLevelByDistrictName(armyName, divisionName);
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
		
		int alldognumtotal = sheepdogsDao.getAllDogNumByDistrictCode(divisionCode0to4);
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Object> GetCityMap(String provinceName,String cityName) {
		provinceName = nameConversionUtil.EchartsAreaNameToGov(provinceName);
		cityName = nameConversionUtil.EchartsAreaNameToGov(cityName);
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//获得省区域编码
		String provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();;
		//编码前两位表示省份
	    String provinceCode0to2 = provinceCode.substring(0, 2);
	    
	    //获得市区域编码
	    String cityCode = districtsDao.getCityDistrictsByDistrictName(cityName, provinceCode0to2).getDistrictcode();
	    //编码前四位表示市
	    String cityCode0to4 = cityCode.substring(0, 4);
	    
		//获得流行县和乡
		List<Districts> countiesandtowns = districtsDao.getCountiesAndTownsByDistrictcode(cityCode0to4);
		int i=0;
		for(Districts pro : countiesandtowns)
        { 
			//对于每个流行县
            if (pro.getDistrictcode().endsWith("000000"))
            {
            	//保存每个县的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("countyname", pro.getShortname());
				String countyCode0to6 = pro.getDistrictcode().substring(0, 6);
				int townnum = 0;
				//统计该县流行乡的个数
				for(Districts cn:countiesandtowns) {
					if(cn.getDistrictcode().startsWith(countyCode0to6) && cn.getDistrictcode().endsWith("000") 
							&& !cn.getDistrictcode().equals(countyCode0to6+"000000")) {
						townnum++;
					}
				}
				
				maptemp.put("townnum", townnum);
				//该县管理员总数
				int managernum = managersDao.getCountyManagerNumByDistrictName(provinceName,cityName,pro.getDistrictname());
				maptemp.put("managernum", managernum);
				//牧犬总数
				List<String> dognumlist = sheepdogsDao.getAllNeckletIdByDistrictcode(countyCode0to6);
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
				int mednum = exhibitrealtimeDao.getExhiCountByDistrictcode(countyCode0to6);
				maptemp.put("mednum", mednum);
				//喂食器数量
				int feednum = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(countyCode0to6);
				maptemp.put("feedernum", feednum);			
				
				map.put(""+i, maptemp);
				
				i++;
            }
        }

		return map;
	}

	@Override
	public Map<String, Object> GetArmyCityMap(String armyName,String divisionName) {
		armyName = nameConversionUtil.EchartsAreaNameToGov(armyName);
		divisionName = nameConversionUtil.EchartsAreaNameToGov(divisionName);
 
		Map<String, Object> map = new HashMap<String,Object>();
		//获得兵团区域编码
		String armyCode = districtsDao.getDistrictsByDistrictName(armyName).getDistrictcode();
	    String armyCode0to2 = armyCode.substring(0, 2);
	    //获得师区域编码
		String divisionCode = districtsDao.getCityDistrictsByDistrictName(divisionName, armyCode0to2).getDistrictcode();
	    String divisionCode0to4 = divisionCode.substring(0, 4);
	    
		 //获得流行团和连
		List<Districts> regimentalandcompany = districtsDao.getRegimentalAndCompanyByDistrictcode(divisionCode0to4);
		int i=0;
		for(Districts pro : regimentalandcompany)
        { 
			//对于每个流行团
            if (pro.getDistrictcode().endsWith("00"))
            {
            	//保存每个团的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("regimentalname", pro.getShortname());
				String regimentalCode0to6 = pro.getDistrictcode().substring(0, 6);
				int companynum = 0;
				//该团所属的流行连的个数
				for(Districts cn:regimentalandcompany) {
					if(cn.getDistrictcode().startsWith(regimentalCode0to6) && !cn.getDistrictcode().equals(regimentalCode0to6+"00")) {
						companynum++;
					}
				}
				maptemp.put("companynum", companynum);
				//该团管理员总数
				int managernum = managersDao.getCountyManagerNumByDistrictName(armyName,divisionName,pro.getDistrictname());
				maptemp.put("managernum", managernum);
				//牧犬总数
				List<String> dognumlist = sheepdogsDao.getAllNeckletIdByDistrictcode(regimentalCode0to6);
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
				int mednum = exhibitrealtimeDao.getExhiCountByDistrictcode(regimentalCode0to6);
				maptemp.put("mednum", mednum);
				//喂食次数
				int feednum = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(divisionCode0to4);
				maptemp.put("feedernum", feednum);						
				map.put(""+i, maptemp);
				
				i++;
            }
        }

		return map;
	}


	@Override
	public Map<String, Object> GetDistrictcode(String provinceName,String cityName) {
		Map<String, Object> map = new HashMap<String,Object>();
		//获得该地区地区编码前两位(省)
		Districts province = districtsDao.getDistrictsByDistrictName(provinceName);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		//获得该地区地区编码
		Districts city = districtsDao.getCityDistrictsByDistrictName(cityName, provinceCode0to2);
		String cityCode = city.getDistrictcode();
		//该市或师的官方名
		map.put("cityGov",nameConversionUtil.EchartsAreaNameToGov(cityName));
		//该市或师的echart地图所用名
		map.put("cityEchartsAreaName",nameConversionUtil.GovToEchartsAreaName(cityName).replace("*",""));
		//该省或军区的官方名
		map.put("provinceGov",nameConversionUtil.EchartsAreaNameToGov(provinceName));
		//该省或军区的echart地图所用名
		map.put("provinceEchartsAreaName",nameConversionUtil.GovToEchartsAreaName(provinceName).replace("*",""));
		map.put("districtcode",cityCode);
		return map;
	}


}
