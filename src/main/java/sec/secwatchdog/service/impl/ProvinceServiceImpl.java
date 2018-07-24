package sec.secwatchdog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.AppexhibitrealtimeDao;
import sec.secwatchdog.mapper.DistrictsDao;
import sec.secwatchdog.mapper.ExhibitrealtimeDao;
import sec.secwatchdog.mapper.ManagersDao;
import sec.secwatchdog.mapper.SheepdogsDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.ProvinceService;
import sec.secwatchdog.util.NameConversionUtil;

@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {

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
	public Map<String, Integer> GetIndexLogoInfo(String provinceName) {
		
		provinceName = nameConversionUtil.EchartsAreaNameToGov(provinceName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		//获得该地区地区编码前两位(省)
		Districts districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
		String provinceCode = districtsist.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
        //获得该省所有的狗
		List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(provinceCode0to2);

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
		int med1 = exhibitrealtimeDao.getExhiCountByDistrictcode(provinceCode0to2);
		//喂食次数
		int med2 = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(provinceCode0to2);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面两位数字开头的编号所对应的所有区域的信息
		dislist = districtsDao.getDistrictsByDistrictcode(provinceCode0to2);
		
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
		//省级及省级以下管理员
		List<Integer> levellist = new ArrayList<Integer>();
		levellist = managersDao.getProvinceManagerLevelAndBelowByDistrictName(provinceName);
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
		
		int alldognumtotal = sheepdogsDao.getAllNeckletIdByDistrictcode(provinceCode0to2).size();
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Integer> GetArmyIndexLogo(String armyName) {
		armyName = nameConversionUtil.EchartsAreaNameToGov(armyName);
		 
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Districts districtsist = null;
		//获得该地区地区编码前两位(兵团)
		districtsist = districtsDao.getDistrictsByDistrictName(armyName);
		String armyCode = districtsist.getDistrictcode();
		String armyCode0to2 = armyCode.substring(0,2);
		
		List<Sheepdogs> sdlist = null;
		sdlist = sheepdogsDao.getIndexInforByDistrictcode(armyCode0to2);
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
		int med1 = exhibitrealtimeDao.getExhiCountByDistrictcode(armyCode0to2);
		//喂食次数
		int med2 = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(armyCode0to2);
		//驱虫总次数
		int mednumtotal = med1 + med2;
		List<Districts> armylist = new ArrayList<Districts>(); 
		//获得以上面两位数字开头的编号所对应的所有区域的信息
		armylist = districtsDao.getDistrictsByDistrictcode(armyCode0to2);
		
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
		//兵团级及兵团级以下管理员
		List<Integer> levellist = new ArrayList<Integer>();
		levellist = managersDao.getProvinceManagerLevelAndBelowByDistrictName(armyName);
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
		
		int alldognumtotal = sheepdogsDao.getAllNeckletIdByDistrictcode(armyCode0to2).size();
		
		map.put("neckdognumtotal", neckdognumtotal);
		map.put("alldognumtotal",alldognumtotal );
		map.put("countrymednumtotal", mednumtotal);
		map.put("feedernumtotal", feedernumtotal);
	   return map;
	}

	@Override
	public Map<String, Object> GetProvinceMap(String provinceName) {
		provinceName = nameConversionUtil.EchartsAreaNameToGov(provinceName);
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//获得省区域编码
		String provinceCode = districtsDao.getDistrictsByDistrictName(provinceName).getDistrictcode();
		//编码前两位表示省份
	    String provinceCode0to2 = provinceCode.substring(0, 2);
		//获得流行市和县
		List<Districts> citiesandcounties = districtsDao.getCitiesAndCountiesByDistrictcode(provinceCode0to2);
		int i=0;
		for(Districts pro : citiesandcounties)
        { 
			//对于每个流行市
            if (pro.getDistrictcode().endsWith("00000000"))
            {
            	//保存每个市的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("cityname", pro.getShortname());
				String cityCode0to4 = pro.getDistrictcode().substring(0, 4);
				//统计该市流行县的个数
				int countynum = 0;		
				for(Districts cn:citiesandcounties) {
					if(cn.getDistrictcode().startsWith(cityCode0to4) && cn.getDistrictcode().endsWith("000000") 
							&& !cn.getDistrictcode().equals(cityCode0to4+"00000000")) {
						countynum++;
					}
				}
				maptemp.put("countynum", countynum);
				//该市管理员总数
				int managernum = managersDao.getCityManagerAndBelowByDistrictName(provinceName, pro.getDistrictname()).size();
				maptemp.put("managernum", managernum);
				//牧犬总数
				List<String> dognumlist = sheepdogsDao.getAllNeckletIdByDistrictcode(cityCode0to4);
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
				int mednum = exhibitrealtimeDao.getExhiCountByDistrictcode(cityCode0to4);
				maptemp.put("mednum", mednum);
				//喂食器数量
				int feednum = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(cityCode0to4);
				maptemp.put("feedernum", feednum);			
				
				map.put(""+i, maptemp);
				
				i++;
            }
        }

		return map;
	}

	@Override
	public Map<String, Object> GetArmyProvinceMap(String armyName) {
		armyName = nameConversionUtil.EchartsAreaNameToGov(armyName);

		Map<String, Object> map = new HashMap<String,Object>();
		//获得兵团区域编码
		String armyCode = districtsDao.getDistrictsByDistrictName(armyName).getDistrictcode();
	    String armyCode0to2 = armyCode.substring(0, 2);
	    //获得流行师和团
		List<Districts> divisionsandregimental = districtsDao.getDivisionsAndRegimentalByDistrictcode(armyCode0to2);
		int i=0;
		for(Districts divisions : divisionsandregimental)
        { 
			//对于每个流行师
            if (divisions.getDistrictcode().endsWith("0000"))
            {
            	//保存每个师的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("divisionname", divisions.getDistrictname());
				String divisionCode0to4 = divisions.getDistrictcode().substring(0, 4);
				int regimentalnum = 0;
				//该师所属的流行团的个数
				for(Districts rt:divisionsandregimental) {
					if(rt.getDistrictcode().startsWith(divisionCode0to4) && rt.getDistrictcode().endsWith("00") 
							&& !rt.getDistrictcode().equals(divisionCode0to4+"0000")) {					 
						regimentalnum++;
					}
				}
				maptemp.put("regimentalnum", regimentalnum);
				//该师管理员总数
				int managernum = managersDao.getCityManagerAndBelowByDistrictName(armyName,divisions.getDistrictname()).size();
				maptemp.put("managernum", managernum);
				//牧犬总数
				List<String> dognumlist = sheepdogsDao.getAllNeckletIdByDistrictcode(divisionCode0to4);
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
				int mednum = exhibitrealtimeDao.getExhiCountByDistrictcode(divisionCode0to4);
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
	public Map<String, Object> GetDistrictcode(String provinceName) {
		Map<String, Object> map = new HashMap<String,Object>();
		Districts districtsist = null;
		
		districtsist = districtsDao.getDistrictsByDistrictName(provinceName);
		String provincCode = districtsist.getDistrictcode();
		//该省或军区的官方名
		map.put("provinceGov",nameConversionUtil.EchartsAreaNameToGov(provinceName));
		//该省或军区的echart地图所用名
		map.put("provinceEchartsAreaName",nameConversionUtil.GovToEchartsAreaName(provinceName).replace("*",""));
		map.put("districtcode",provincCode);
		return map;
	}

}
