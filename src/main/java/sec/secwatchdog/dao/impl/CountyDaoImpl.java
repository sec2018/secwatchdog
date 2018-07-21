package sec.secwatchdog.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sec.secwatchdog.dao.CountyDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.NameConversionUtil;

@Repository("countyDao")
public class CountyDaoImpl implements CountyDao {
	@Autowired
    private SqlSession session;
	
	@Override
	public Map<String, Integer> GetIndexLogoInfo(String provinceName,String cityName, String countyName) {	
		provinceName = NameConversionUtil.EchartsAreaNameToGov(session, provinceName);
		cityName = NameConversionUtil.EchartsAreaNameToGov(session, cityName);
		countyName = NameConversionUtil.EchartsAreaNameToGov(session, countyName);
		Map<String, Integer> map = new HashMap<String,Integer>();		 
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provinceName", provinceName);
		mapparam.put("cityName", cityName);
		mapparam.put("countyName", countyName);
		//获得该地区地区编码前两位(省)
		String statement = "sec.secwatchdog.dao.CountyDao.getProvince";
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provincCode0to2 = provinceCode.substring(0,2);	
		mapparam.put("provincCode0to2", provincCode0to2);
		//获得该地区地区编码前四位(市)
		statement = "sec.secwatchdog.dao.CountyDao.getCity";
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);
		
		//获得该地区地区编码前六位(县)
		statement = "sec.secwatchdog.dao.CountyDao.getCounty";
		Districts county= session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
		String countyCode0to6 = countyCode.substring(0,6);	
       	mapparam.put("countyCode0to6", countyCode0to6); 
		
		List<Sheepdogs> sdlist = null;
		statement = "sec.secwatchdog.dao.CountyDao.getCountyIndexInfo";
		
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
		statement = "sec.secwatchdog.dao.CountyDao.getExhiCount";
		//投药次数
		int med1 = session.selectOne(statement, mapparam);
		statement = "sec.secwatchdog.dao.CountyDao.getAppexhiCount";
		//喂食次数
		int med2 = session.selectOne(statement, mapparam);
		//驱虫总次数
		int medNumTotal = med1 + med2;
		List<Districts> dislist = new ArrayList<Districts>();
		//获得以上面六位数字开头的编号所对应的所有区域的信息
		statement = "sec.secwatchdog.dao.CountyDao.ywDisctricts";
		dislist = session.selectList(statement,mapparam);
		
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
		statement = "sec.secwatchdog.dao.CountyDao.getManagerLevel";
		levellist = session.selectList(statement,mapparam);
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
		
		statement = "sec.secwatchdog.dao.CountyDao.getAllDogNum";
		int allDogNumTotal = session.selectOne(statement,mapparam);
		
		map.put("neckdognumtotal", neckDogNumTotal);
		map.put("alldognumtotal",allDogNumTotal );
		map.put("countrymednumtotal", medNumTotal);
		map.put("feedernumtotal", feederNumTotal);
	   return map;
	}

	 

	@Override
	public Map<String, Object> GetCountyMap(String provinceName, String cityName, String countyName) {
		provinceName = NameConversionUtil.EchartsAreaNameToGov(session,provinceName);
		cityName = NameConversionUtil.EchartsAreaNameToGov(session,cityName);
		countyName = NameConversionUtil.EchartsAreaNameToGov(session,countyName);
		//保存请求返回的数据
		Map<String, Object> map = new HashMap<String,Object>();
		//sql语句的参数
		Map<String, String> mapparam = new HashMap<String,String>();
		mapparam.put("provinceName", provinceName);
		String statement = "sec.secwatchdog.dao.CountyDao.getProvinceMap";
		//获得省区域编码
		String thisProvince = session.selectOne(statement,mapparam);
		//编码前两位表示省份
	    String thisProvince0to2 = thisProvince.substring(0, 2);
	    mapparam.put("thisProvince0to2", thisProvince0to2);
	    mapparam.put("cityName", cityName);
	    statement = "sec.secwatchdog.dao.CountyDao.getCityMap";
	    //获得市区域编码
	    String thisCity = session.selectOne(statement,mapparam);
	    //编码前四位表示市
	    String thisCity0to4 = thisCity.substring(0, 4);
	    mapparam.put("thisCity0to4", thisCity0to4);
	    
	    mapparam.put("countyName", countyName);
	    statement = "sec.secwatchdog.dao.CountyDao.getCountyMap";
	    //获得县区域编码
	    String thisCounty = session.selectOne(statement,mapparam);
	    //编码前六位表示县
	    String thisCounty0to6 = thisCounty.substring(0, 6);    
	    mapparam.put("thisCounty0to6", thisCounty0to6);
		statement = "sec.secwatchdog.dao.CountyDao.getVillagesAndHamlets"; 
		//获得流行乡和村
		List<Districts> villagesAndHamlets = session.selectList(statement,mapparam);
		int i=0;
		for(Districts pro : villagesAndHamlets)
        { 
			//对于每个流行乡
            if (pro.getDistrictcode().endsWith("000"))
            {
            	//保存每个乡的相关信息
            	Map<String, Object> maptemp = new HashMap<String,Object>();
				maptemp.put("townname", pro.getShortname());
				String village0to9 = pro.getDistrictcode().substring(0, 9);
				int HamletsNum = 0;
				//统计该乡流行村的个数
				for(Districts cn:villagesAndHamlets) {
					if(cn.getDistrictcode().startsWith(village0to9)
							&& !cn.getDistrictcode().equals(village0to9+"000")) {
						HamletsNum++;
					}
				}
				maptemp.put("hamletnum", HamletsNum);
				//该乡管理员总数
				statement = "sec.secwatchdog.dao.CountyDao.getManagerNum";
				mapparam.put("districtName", pro.getDistrictname());
				int managerNum = session.selectOne(statement,mapparam);
				maptemp.put("managernum", managerNum);
				//牧犬总数
				statement = "sec.secwatchdog.dao.CountyDao.getAllNecketId";  
				mapparam.put("village0to9", village0to9);
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
				statement = "sec.secwatchdog.dao.CountyDao.getCountExhibitrealtime";
				int medNum = session.selectOne(statement,mapparam);
				maptemp.put("mednum", medNum);
				//喂食器数量
				statement = "sec.secwatchdog.dao.CountyDao.getCountAppexhibitrealtime";
				int feednum = session.selectOne(statement,mapparam);
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
		Map<String, Object> mapparam = new HashMap<String,Object>();
	
		mapparam.put("provinceName", provinceName);
		mapparam.put("cityName", cityName);
		mapparam.put("countyName", countyName);
		//获得该地区地区编码前两位(省)
		String statement = "sec.secwatchdog.dao.CountyDao.getProvince";
		Districts province = session.selectOne(statement,mapparam);
		String provinceCode = province.getDistrictcode();
		String provinceCode0to2 = provinceCode.substring(0,2);	
		mapparam.put("provinceCode0to2", provinceCode0to2);
		//获得该地区地区编码前四位(市)
		statement = "sec.secwatchdog.dao.CountyDao.getCity";
		Districts city = session.selectOne(statement,mapparam);
		String cityCode = city.getDistrictcode();
		String cityCode0to4 = cityCode.substring(0,4);	
		mapparam.put("cityCode0to4", cityCode0to4);
		//获得该地区地区编码
		statement = "sec.secwatchdog.dao.CountyDao.getCounty"; 
		Districts county = session.selectOne(statement,mapparam);
		String countyCode = county.getDistrictcode();
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
		map.put("districtcode",countyCode);
		return map;
		
	}
 

}
