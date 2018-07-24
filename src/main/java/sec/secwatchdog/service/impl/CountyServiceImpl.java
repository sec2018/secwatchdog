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
import sec.secwatchdog.service.CountyService;
import sec.secwatchdog.util.NameConversionUtil;

@Service("countyService")
public class CountyServiceImpl implements CountyService {

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
	public Map<String, Integer> GetIndexLogoInfo(String provinceName, String cityName, String countyName) {
		provinceName = nameConversionUtil.EchartsAreaNameToGov(provinceName);
		cityName = nameConversionUtil.EchartsAreaNameToGov(cityName);
		countyName = nameConversionUtil.EchartsAreaNameToGov(countyName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		//获得该地区地区编码前两位(省)
		Districts province = districtsDao.getDistrictsByDistrictName(provinceName);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		//获得该地区地区编码前四位(市)
		Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		
		//获得该地区地区编码前六位(县)
		Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	 
		
		List<Sheepdogs> sdlist = sheepdogsDao.getIndexInforByDistrictcode(countyCode0to6);
		//佩戴项圈牧犬数量和喂食器数量
		int neckDogNumTotal = 0;
		int feederNumTotal = 0;
		for(Sheepdogs each:sdlist){
			//"-1"表示未佩戴项圈
			if(!each.getNeckletid().equals("-1")) {
				neckDogNumTotal++;
			}
			//"-1"表示无喂食器
			if(!each.getApparatusid().equals("-1")) {
				feederNumTotal++;
			}
		}
		//投药次数
		int med1 =exhibitrealtimeDao.getExhiCountByDistrictcode(countyCode0to6);
		//喂食次数
		int med2 = appexhibitrealtimeDao.getAppExhiCountByDistrictcode(countyCode0to6);
		//驱虫总次数
		int medNumTotal = med1 + med2;
		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面六位数字开头的编号所对应的所有区域的信息
		dislist = districtsDao.getDistrictsByDistrictcode(countyCode0to6);
		
        //乡、村流行区数量
		int villageEpidemicTotal = 0;
		int hamletEpidemicTotal = 0;
		
		for(Districts each : dislist) {
			//该区域为流行区
			if(each.getEpidemic() == 1) {
			//根据区域编码将区域分类，如xx0000000000为省，xxxx00000000表明该区域为市级，xxxxxx000000为县级，xxxxxxxxx000为乡级，最后三位不全为0则表示村级
			  if(each.getDistrictcode().substring(9,12).equals("000")) {
				  villageEpidemicTotal++;
				}else
					hamletEpidemicTotal++;
			}
		
		}
		List<Integer> levellist = new ArrayList<Integer>();
		levellist = managersDao.getCountyManagerAndBelowByDistrictName(provinceName, cityName, countyName);
		//县、乡、村级管理员数
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
		//将数据保存到map中
		map.put("villageepidemictotal", villageEpidemicTotal-1);
		map.put("hamletepidemictotal",  hamletEpidemicTotal);

		map.put("countyadmintotal", countyAadminTotal);
		map.put("villageadmintotal", villageAdminTotal);
		map.put("hamletadmintotal", hamletAdminTotal);
		
		int allDogNumTotal = sheepdogsDao.getAllNeckletIdByDistrictcode(countyCode0to6).size();
		
		map.put("neckdognumtotal", neckDogNumTotal);
		map.put("alldognumtotal",allDogNumTotal );
		map.put("countrymednumtotal", medNumTotal);
		map.put("feedernumtotal", feederNumTotal);
	   return map;
	}

	@Override
	public Map<String, Object> GetCountyMap(String provinceName, String cityName, String countyName) {
		provinceName = nameConversionUtil.EchartsAreaNameToGov(provinceName);
		cityName = nameConversionUtil.EchartsAreaNameToGov(cityName);
		countyName = nameConversionUtil.EchartsAreaNameToGov(countyName);
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//获得该地区地区编码前两位(省)
		Districts province = districtsDao.getDistrictsByDistrictName(provinceName);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		//获得该地区地区编码前四位(市)
		Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		
		//获得该地区地区编码前六位(县)
		Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	 
		//获得流行乡和村
		List<Districts> villagesAndHamlets = districtsDao.getVillagesAndHamletsByDistrictcode(countyCode0to6);
		int i=0;
		for(Districts pro : villagesAndHamlets)
        { 
			//对于每个流行乡
            if (pro.getDistrictcode().endsWith("000"))
            {
            	//保存每个乡的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("townname", pro.getShortname());
				String villageCode0to9 = pro.getDistrictcode().substring(0, 9);
				int HamletsNum = 0;
				//统计该乡流行村的个数
				for(Districts cn:villagesAndHamlets) {
					if(cn.getDistrictcode().startsWith(villageCode0to9)
							&& !cn.getDistrictcode().equals(villageCode0to9+"000")) {
						HamletsNum++;
					}
				}
				maptemp.put("hamletnum", HamletsNum);
				//该乡管理员总数
				int managerNum = managersDao.getVillageManagerAndBelowByDistrictName(provinceName, cityName, countyName, pro.getDistrictname()).size();
				maptemp.put("managernum", managerNum);
				//牧犬总数
				List<String> dogNumList = sheepdogsDao.getAllNeckletIdByDistrictcode(villageCode0to9);
				maptemp.put("dognum", dogNumList.size());
				//项圈总数
				int neckletNum = 0;
				for(String n1:dogNumList) {
					if(!n1.equals("-1")) {
						neckletNum++;
					}
				}
				maptemp.put("neckletnum", neckletNum);
				//经纬度
				maptemp.put("lng", pro.getLng());
				maptemp.put("lat", pro.getLat());
				//投药总次数
				int medNum =  exhibitrealtimeDao.getExhiCountByDistrictcode(villageCode0to9);
				maptemp.put("mednum", medNum);
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
	public Map<String, Object> GetDistrictcode(String provinceName, String cityName, String countyName) {
		Map<String, Object> map = new HashMap<String,Object>();
	
		//获得该地区地区编码前两位(省)
		Districts province = districtsDao.getDistrictsByDistrictName(provinceName);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		//获得该地区地区编码前四位(市)
		Districts city = districtsDao.getCityAndBelowDistrictsByDistrictName(cityName, provinceCode0to2);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		
		//获得该地区地区编码前六位(县)
		Districts county = districtsDao.getCityAndBelowDistrictsByDistrictName(countyName, cityCode0to4);
		String countyCode = county.getDistrictcode();
		//该县的官方名
		map.put("countyGov",nameConversionUtil.EchartsAreaNameToGov(countyName));		
		//该县的echart地图所用名
		map.put("countyEchartsAreaName",nameConversionUtil.GovToEchartsAreaName(countyName).replace("*",""));
		//该市的官方名
		map.put("cityGov",nameConversionUtil.EchartsAreaNameToGov(cityName));
		//该市的echart地图所用名
		map.put("cityEchartsAreaName",nameConversionUtil.GovToEchartsAreaName(cityName).replace("*",""));
		//该省的官方名
		map.put("provinceGov",nameConversionUtil.EchartsAreaNameToGov(provinceName));
		//该省的echart地图所用名
		map.put("provinceEchartsAreaName",nameConversionUtil.GovToEchartsAreaName(provinceName).replace("*",""));
		map.put("districtcode",countyCode);
		return map;
	}
	
 

}
