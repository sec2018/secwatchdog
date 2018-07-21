package sec.secwatchdog.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sec.secwatchdog.dao.VillageDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;

@Repository("villageDao")
public class VillageDaoImpl implements VillageDao {
	@Autowired
    private SqlSession session;
	
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
		//获得该地区地区编码前两位(省)
		String statement = "sec.secwatchdog.dao.VillageDao.getProvince";
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provincCode0to2 = provinceCode.substring(0,2);
		mapparam.put("provincCode0to2", provincCode0to2);
		//获得该地区地区编码前四位(市)
		statement = "sec.secwatchdog.dao.VillageDao.getCity";
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);
		//获得该地区地区编码前六位(县)
		statement = "sec.secwatchdog.dao.VillageDao.getCounty";
		Districts county= session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	
		mapparam.put("countyCode0to6", countyCode0to6); 	
		//获得该地区地区编码前九位(乡)
		statement = "sec.secwatchdog.dao.VillageDao.getVillage";  
		Districts village= session.selectOne(statement,mapparam);
		String villageCode = village.getDistrictcode();
		String villageCode0to9 = villageCode.substring(0,9);	
		mapparam.put("villageCode0to9", villageCode0to9); 		
		
		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.VillageDao.getVillageIndexInfo";
		
		sdlist = session.selectList(statement, mapparam);
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
		statement = "sec.secwatchdog.dao.VillageDao.getExhiCount";
		//投药次数
		int med1 = session.selectOne(statement, mapparam);
		statement = "sec.secwatchdog.dao.VillageDao.getAppexhiCount";
		//喂食次数
		int med2 = session.selectOne(statement, mapparam);
		//驱虫总次数
		int medNumTotal = med1 + med2;

		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面九位数字开头的编号所对应的所有区域的信息
		statement = "sec.secwatchdog.dao.VillageDao.ywDisctricts";
		dislist = session.selectList(statement,mapparam);
		
        //村流行区数量
		int hamletEpidemicTotal = 0;	
		for(Districts each : dislist) {
			//该区域为流行区
			if(each.getEpidemic() == 1) {
					hamletEpidemicTotal++;	
			}
		}
		List<Integer> levellist = new ArrayList<Integer>();
		
		statement = "sec.secwatchdog.dao.VillageDao.getManagerLevel";
		levellist = session.selectList(statement,mapparam);
		//乡、村级管理员数
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
		//将数据保存到map中
		map.put("hamletepidemictotal",  hamletEpidemicTotal-1);
		map.put("villageadmintotal", villageAdminTotal);
		map.put("hamletadmintotal", hamletAdminTotal);
		
		statement = "sec.secwatchdog.dao.VillageDao.getAllDogNum";
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
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//sql语句的参数
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provinceName", provinceName);
		String statement = "sec.secwatchdog.dao.VillageDao.getProvinceMap";
		//获得省区域编码
		String thisProvince = session.selectOne(statement,mapparam);
		//编码前两位表示省份
	    String thisProvince0to2 = thisProvince.substring(0, 2);
	    mapparam.put("thisProvince0to2", thisProvince0to2);
	    mapparam.put("cityName", cityName);
	    statement = "sec.secwatchdog.dao.VillageDao.getCityMap"; 
	    //获得市区域编码
	    String thisCity = session.selectOne(statement,mapparam);
	    //编码前四位表示市
	    String thisCity0to4 = thisCity.substring(0, 4);
	    mapparam.put("thisCity0to4", thisCity0to4);
	    
	    mapparam.put("countyName", countyName);
	    statement = "sec.secwatchdog.dao.VillageDao.getCountyMap";
	   //获得县区域编码
	    String thisCounty = session.selectOne(statement,mapparam);
	    //编码前六位表示县
	    String thisCounty0to6 = thisCounty.substring(0, 6);    
	    mapparam.put("thisCounty0to6", thisCounty0to6);

	    mapparam.put("villageName", villageName);
	    statement = "sec.secwatchdog.dao.VillageDao.getVillageMap";
	    //获得乡区域编码
	    String thisVillage = session.selectOne(statement,mapparam);
	   //编码前九位表示县
	    String thisVillage0to9 = thisVillage.substring(0, 9);	    
	    mapparam.put("thisVillage0to9", thisVillage0to9);
		statement = "sec.secwatchdog.dao.VillageDao.getHamlets";
		//获得流行村
		List<Districts> hamlets = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : hamlets)
        { 
			//对于每个流行村
        	Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("harmletname", pro.getShortname());
			String hamletCode = pro.getDistrictcode();
			
			//该村管理员总数
			statement = "sec.secwatchdog.dao.VillageDao.getManagerNum";
			mapparam.put("districtName", pro.getDistrictname());
			int managerNum = session.selectOne(statement,mapparam);
			maptemp.put("managernum", managerNum);
			//牧犬总数
			statement = "sec.secwatchdog.dao.VillageDao.getAllNecketId";  
			mapparam.put("hamletCode", hamletCode);
			List<String> dogNumList = session.selectList(statement,mapparam);
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
			statement = "sec.secwatchdog.dao.VillageDao.getCountExhibitrealtime";
			int medNum = session.selectOne(statement,mapparam);
			maptemp.put("mednum", medNum);
			//喂食器数量
			statement = "sec.secwatchdog.dao.VillageDao.getCountAppexhibitrealtime";
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
		//获得该地区地区编码前两位(省)
		String statement = "sec.secwatchdog.dao.VillageDao.getProvince";
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		mapparam.put("provinceCode0to2", provinceCode0to2);
		//获得该地区地区编码前四位(市)
		statement = "sec.secwatchdog.dao.VillageDao.getCity";
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);		
		//获得该地区地区编码前六位(县)
		statement = "sec.secwatchdog.dao.VillageDao.getCounty";
		Districts county = session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	
		mapparam.put("countyCode0to6", countyCode0to6);	
		//获得该地区地区编码
		statement = "sec.secwatchdog.dao.VillageDao.getVillage";
		Districts village = session.selectOne(statement,mapparam);
		String villageCode = village.getDistrictcode();
		//该乡的官方名
		map.put("villageGov",NameConversionUtil.EchartsAreaNameToGov(session, villageName));
		//该乡的echart地图所用名
		map.put("villageEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, villageName).replace("*",""));
		//该县的官方名
		map.put("countyGov",NameConversionUtil.EchartsAreaNameToGov(session, countyName));
		//该县的echart地图所用名
		map.put("countyEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, countyName).replace("*",""));
		//该市的官方名
		map.put("cityGov",NameConversionUtil.EchartsAreaNameToGov(session, cityName));
		//该市的echart地图所用名
		map.put("cityEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, cityName).replace("*",""));
		//该省的官方名
		map.put("provinceGov",NameConversionUtil.EchartsAreaNameToGov(session, provinceName));
		//该省的echart地图所用名
		map.put("provinceEchartsAreaName",NameConversionUtil.GovToEchartsAreaName(session, provinceName).replace("*",""));
		map.put("districtcode",villageCode);
		return map;
		
	}
 

}
