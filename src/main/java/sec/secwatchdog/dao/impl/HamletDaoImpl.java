package sec.secwatchdog.dao.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sec.secwatchdog.dao.HamletDao;
import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Feedback;
import sec.secwatchdog.model.Feederback;
import sec.secwatchdog.model.Lastapparatusrealtime;
import sec.secwatchdog.model.Lastappexhibitrealtime;
import sec.secwatchdog.model.Lastexhibitrealtime;
import sec.secwatchdog.model.Lastneckletrealtime;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.model.PageBean;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.service.UserService;

import org.apache.ibatis.annotations.Mapper;

@Repository("hamletDao")
public class HamletDaoImpl implements HamletDao{
	
@Autowired	
private SqlSession session;

	@Resource
	private UserService userService;

//	@Override
//	public Map<String, Object> CheckUser(String username) {
//		// TODO Auto-generated method stub
//		Managers resultUser=userService.checklogin(username);
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("username", resultUser.getUsername());
//		map.put("managername", resultUser.getManagername());
//		map.put("privilegelevel", resultUser.getPrivilegelevel());
//		String area = "全国";
//		switch (resultUser.getPrivilegelevel())
//        {
//            case 1:
//            	area = "全国";
//                break;
//            case 2:
//            	area = resultUser.getProvince();
//                break;
//            case 3:
//            	area = resultUser.getProvince() + resultUser.getCity();
//                break;
//            case 4:
//            	area = resultUser.getProvince() + resultUser.getCity() + resultUser.getCounty();
//                break;
//            case 5:
//            	area = resultUser.getProvince() + resultUser.getCity() + resultUser.getCounty() + resultUser.getVillage();
//                break;
//            case 6:
//            	area = resultUser.getProvince() + resultUser.getCity() + resultUser.getCounty() + resultUser.getVillage() + resultUser.getHamlet();
//                break;
//            case 7:
//            	area = "游客模式";
//                break;
//        }
//		map.put("area", area);
//		map.put("province", resultUser.getProvince());
//		map.put("city", resultUser.getCity());
//		map.put("county", resultUser.getCounty());
//		map.put("village", resultUser.getVillage());
//		map.put("hamlet", resultUser.getHamlet());
//		map.put("job", resultUser.getWorkplace());
//		map.put("manageridentity", resultUser.getManageridentity());
//		map.put("officecall", resultUser.getOfficecall());
//		map.put("telphone", resultUser.getManagertel());
//		map.put("address", resultUser.getAddress());
//		map.put("email", resultUser.getEmail());
//		map.put("password", "");
//		map.put("chargehamlet", resultUser.getChargehamlet());
//		map.put("districtcode", resultUser.getDistrictcode());
//		map.put("adminstatus", resultUser.getManagerstatus() == 1 ?  "已激活" : "未激活");
//		return map;
//	}
//	
	public Map<String,Object> Getuser_page_farmDogList(PageBean pageBean,String username){
		Map<String,Object> Getuser_page_farmDogList = new HashMap<String,Object>();
		
		String statement = "sec.secwatchdog.dao.HamletDao.Getuser_page_farmDogList"; 
		Map<String, Object> paramsmap = new HashMap<String, Object>();
		paramsmap.put("pageBean1", pageBean);
		if(pageBean!=null){
			paramsmap.put("pagestart", pageBean.getStart());
			paramsmap.put("pagesize", pageBean.getPageSize());
		}
		paramsmap.put("username", username);
		List<Sheepdogs> doglist = new ArrayList<Sheepdogs>();
		doglist = session.selectList(statement,paramsmap);
		int i = 0;
		for(Sheepdogs each:doglist) {
			Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("dogid", each.getDogid());
			maptemp.put("dogname", each.getDogname());
			String neckletid = each.getNeckletid();
			statement = "sec.secwatchdog.dao.HamletDao.getlastexhibitrealtime"; 
			Lastexhibitrealtime lb = session.selectOne(statement,neckletid);
			if(lb != null) {
				maptemp.put("neckletid",  neckletid);
				statement = "sec.secwatchdog.dao.HamletDao.getfeedback"; 
				Feedback feedback = session.selectOne(statement,neckletid);
				maptemp.put("firstmedtime",  feedback.getFirstmedtime());
				maptemp.put("lastmed",  lb.getRealtime());
				maptemp.put("timemed",  feedback.getMedtotal() - lb.getTableremain());
				maptemp.put("nextmed",  lb.getNextexhibittime());
				maptemp.put("exhibitcycle", (Double.parseDouble(feedback.getExhibitcycle())/1440)+"");
			}else {
				maptemp.put("neckletid",  "----");
				maptemp.put("firstmedtime",  "");
				maptemp.put("lastmed",  "");
				maptemp.put("timemed",  0);
				maptemp.put("nextmed",  "");
				maptemp.put("exhibitcycle","10000");
			}
			Getuser_page_farmDogList.put(""+i, maptemp);
//			maptemp.clear();
			i++;
		}
		return Getuser_page_farmDogList;
	}
	
	public Integer Getuser_page_farmDogListtotal(String username){
		String statement = "sec.secwatchdog.dao.HamletDao.Getuser_page_farmDogListtotal"; 
		int total = session.selectOne(statement,username);
		return total;
	}
	
	public Map<String,Object> GetHamletMap(String provincename, String cityname, String countyname, String villagename, String hamletname){
		Map<String,Object> GetHamletMap = new HashMap<String,Object>();
		
        provincename = EchartsAreaNameToGov(provincename);
        cityname = EchartsAreaNameToGov(cityname);
        countyname = EchartsAreaNameToGov(countyname);
        villagename = EchartsAreaNameToGov(villagename);
        hamletname = EchartsAreaNameToGov(hamletname);
        Map<String,String> mapparam = new HashMap<String,String>();
        mapparam.put("provincename", provincename);
        System.out.println(provincename);
        mapparam.put("cityname", cityname);
        mapparam.put("countyname", countyname);
        mapparam.put("villagename", villagename);
        mapparam.put("hamletname", hamletname);
		String statement = "sec.secwatchdog.dao.HamletDao.getprovincecode"; 
		String provincecode0to2 = session.selectOne(statement,mapparam).toString().substring(0, 2);
		mapparam.put("provincecode0to2", provincecode0to2);
		statement = "sec.secwatchdog.dao.HamletDao.getcitycode"; 
		String citycode0to4 = session.selectOne(statement,mapparam).toString().substring(0, 4);
		mapparam.put("citycode0to4", citycode0to4);
		statement = "sec.secwatchdog.dao.HamletDao.getcountycode"; 
		String countycode0to6 = session.selectOne(statement,mapparam).toString().substring(0, 6);
		mapparam.put("countycode0to6", countycode0to6);
		statement = "sec.secwatchdog.dao.HamletDao.getvillagecode"; 
		String thisvillage0to9 = session.selectOne(statement,mapparam).toString().substring(0, 9);
		mapparam.put("thisvillage0to9", thisvillage0to9);
		statement = "sec.secwatchdog.dao.HamletDao.gethamletcode"; 
		Districts thishamlettemp = session.selectOne(statement,mapparam);
		String thishamletcode = "";
		if (thishamlettemp != null)
        {
            thishamletcode = thishamlettemp.getDistrictcode();
        }
        else
        {
            return null;
        }
		statement = "sec.secwatchdog.dao.HamletDao.gethamletdogs"; 
		List<Sheepdogs> doginfo = new ArrayList<Sheepdogs>();
		doginfo = session.selectList(statement,thishamletcode);
		int i = 0;
		for(Sheepdogs each:doginfo) {
			Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("dogid", each.getDogid());
			maptemp.put("neckletid", each.getNeckletid());
			maptemp.put("dogname", each.getDogname());
			statement = "sec.secwatchdog.dao.HamletDao.getlastneckletrealtime"; 
			Lastneckletrealtime lr = session.selectOne(statement,each.getNeckletid());
			if(lr!=null) {
				maptemp.put("lng", Double.parseDouble(lr.getNeckletlongitude()));
				maptemp.put("lat", Double.parseDouble(lr.getNeckletvdoing()));
			}else {
				maptemp.put("lng", Double.parseDouble(thishamlettemp.getLng()));
				maptemp.put("lat", Double.parseDouble(thishamlettemp.getLat()));
			}
			statement = "sec.secwatchdog.dao.HamletDao.getlastexhibitrealtime";
			Lastexhibitrealtime lb = session.selectOne(statement,each.getNeckletid());
			maptemp.put("nextmedtime", lb.getNextexhibittime());
			maptemp.put("manager", each.getManagername());
			maptemp.put("hamletname", hamletname);
			GetHamletMap.put(""+i, maptemp);
//			maptemp.clear();
			i++;
		}
		return GetHamletMap;
	}
	
	public Map<String,Object> Getupuser_page_farmDogFeederList(String provincename, String cityname, String countyname, String villagename, String hamletname){
		Map<String,Object> Getupuser_page_farmDogFeederList = new HashMap<String,Object>();
		
		provincename = EchartsAreaNameToGov(provincename);
        cityname = EchartsAreaNameToGov(cityname);
        countyname = EchartsAreaNameToGov(countyname);
        villagename = EchartsAreaNameToGov(villagename);
        provincename = EchartsAreaNameToGov(provincename);
        cityname = EchartsAreaNameToGov(cityname);
        countyname = EchartsAreaNameToGov(countyname);
        villagename = EchartsAreaNameToGov(villagename);
        hamletname = EchartsAreaNameToGov(hamletname);
        Map<String,String> mapparam = new HashMap<String,String>();
        mapparam.put("provincename", provincename);
        mapparam.put("cityname", cityname);
        mapparam.put("countyname", countyname);
        mapparam.put("villagename", villagename);
        mapparam.put("hamletname", hamletname);
		String statement = "sec.secwatchdog.dao.HamletDao.getprovincecode"; 
		String provincecode0to2 = session.selectOne(statement,mapparam).toString().substring(0, 2);
		mapparam.put("provincecode0to2", provincecode0to2);
		statement = "sec.secwatchdog.dao.HamletDao.getcitycode"; 
		String citycode0to4 = session.selectOne(statement,mapparam).toString().substring(0, 4);
		mapparam.put("citycode0to4", citycode0to4);
		statement = "sec.secwatchdog.dao.HamletDao.getcountycode"; 
		String countycode0to6 = session.selectOne(statement,mapparam).toString().substring(0, 6);
		mapparam.put("countycode0to6", countycode0to6);
		statement = "sec.secwatchdog.dao.HamletDao.getvillagecode"; 
		String thisvillage0to9 = session.selectOne(statement,mapparam).toString().substring(0, 9);
		mapparam.put("thisvillage0to9", thisvillage0to9);
		statement = "sec.secwatchdog.dao.HamletDao.gethamletcode"; 
		Districts thishamlettemp = session.selectOne(statement,mapparam);
		String thishamletcode = "";
		if (thishamlettemp != null)
        {
            thishamletcode = thishamlettemp.getDistrictcode();
        }
        else
        {
            return null;
        }
		statement = "sec.secwatchdog.dao.HamletDao.Getupuser_page_farmDogFeederList";
		List<Sheepdogs> doginfo = new ArrayList<Sheepdogs>();
		doginfo = session.selectList(statement,thishamletcode);
		int i = 0;
		Map<String, Object> maptemp = new HashMap<String,Object>();
		for(Sheepdogs each:doginfo) {
			maptemp.put("dogid", each.getDogid());
			maptemp.put("dogname", each.getDogname());
			String feederid = each.getApparatusid();
			statement = "sec.secwatchdog.dao.HamletDao.getlastappexhibitrealtime"; 
			Lastappexhibitrealtime le = session.selectOne(statement,feederid);
			if(le!=null) {
				maptemp.put("feederid", feederid);
				statement = "sec.secwatchdog.dao.HamletDao.getfeederback"; 
				Feederback fb = session.selectOne(statement,feederid);
				maptemp.put("firstmedtime", fb.getFirstmedtime());
				maptemp.put("lastmed", le.getRealtime());
				maptemp.put("timemed", fb.getMedtotal()-le.getTableremain());
				maptemp.put("nextmed", le.getNextexhibittime());
			}else {
				maptemp.put("feederid", "----");
				maptemp.put("firstmedtime", "");
				maptemp.put("lastmed", "");
				maptemp.put("timemed", 0);
				maptemp.put("nextmed", "");
			}
			Getupuser_page_farmDogFeederList.put(""+i, maptemp);
//			maptemp.clear();
			i++;
		}
		return Getupuser_page_farmDogFeederList;
	}
	
	public Map<String,Object> GetHamletFeederMap(String provincename, String cityname, String countyname, String villagename, String hamlet)
    {
		Map<String,Object> GetHamletFeederMap = new HashMap<String,Object>();
		
        provincename = EchartsAreaNameToGov(provincename);
        cityname = EchartsAreaNameToGov(cityname);
        countyname = EchartsAreaNameToGov(countyname);
        villagename = EchartsAreaNameToGov(villagename);
        hamlet = EchartsAreaNameToGov(hamlet);
        Map<String,String> mapparam = new HashMap<String,String>();
        mapparam.put("provincename", provincename);
        mapparam.put("cityname", cityname);
        mapparam.put("countyname", countyname);
        mapparam.put("villagename", villagename);
        mapparam.put("hamletname", hamlet);
		String statement = "sec.secwatchdog.dao.HamletDao.getprovincecode";
		String provincecode0to2 = session.selectOne(statement,mapparam).toString().substring(0, 2);
		mapparam.put("provincecode0to2", provincecode0to2);
		statement = "sec.secwatchdog.dao.HamletDao.getcitycode";
		String citycode0to4 = session.selectOne(statement,mapparam).toString().substring(0, 4);
		mapparam.put("citycode0to4", citycode0to4);
		statement = "sec.secwatchdog.dao.HamletDao.getcountycode";
		String countycode0to6 = session.selectOne(statement,mapparam).toString().substring(0, 6);
		mapparam.put("countycode0to6", countycode0to6);
		statement = "sec.secwatchdog.dao.HamletDao.getvillagecode";
		String thisvillage0to9 = session.selectOne(statement,mapparam).toString().substring(0, 9);
		mapparam.put("thisvillage0to9", thisvillage0to9);
		statement = "sec.secwatchdog.dao.HamletDao.gethamletcode";
		Districts thishamlettemp = session.selectOne(statement,mapparam);
		String thishamletcode = "";
		if (thishamlettemp != null)
        {
            thishamletcode = thishamlettemp.getDistrictcode();
        }
        else
        {
            return null;
        }
		statement = "sec.secwatchdog.dao.HamletDao.Getupuser_page_farmDogFeederList";
		List<Sheepdogs> doginfo = new ArrayList<Sheepdogs>();
		doginfo = session.selectList(statement,thishamletcode);
		int i = 0;
		Map<String, Object> maptemp = new HashMap<String,Object>();
		for(Sheepdogs each:doginfo) {
			maptemp.put("dogid", each.getDogid());
			maptemp.put("feederid", each.getApparatusid());
			maptemp.put("dogname", each.getDogname());
			statement = "sec.secwatchdog.dao.HamletDao.getlastapparatusrealtime";
			Lastapparatusrealtime lu = session.selectOne(statement,each.getApparatusid());
			if(lu!=null) {
				maptemp.put("lng", Double.parseDouble(lu.getApparatuslongitude()));
				maptemp.put("lat", Double.parseDouble(lu.getApparatusvdoing()));
			}else {
				maptemp.put("lng", Double.parseDouble(thishamlettemp.getLng()));
				maptemp.put("lat", Double.parseDouble(thishamlettemp.getLat()));
			}
			statement = "sec.secwatchdog.dao.HamletDao.getlastappexhibitrealtime";
			Lastappexhibitrealtime lb = session.selectOne(statement,each.getApparatusid());
			maptemp.put("nextmedtime", lb.getNextexhibittime());
			maptemp.put("manager", each.getManagername());
			maptemp.put("hamletname", hamlet);
			GetHamletFeederMap.put(""+i, maptemp);
//			maptemp.clear();
			i++;
		}
		return GetHamletFeederMap;
    }
	
	public Map<String,Object> GetLevel6AdminDogNum(String username)
    {
		Map<String,Object> GetLevel6AdminDogNum = new HashMap<String,Object>();
		
		String statement = "sec.secwatchdog.dao.HamletDao.GetLevel6AdminDogNum";
		List<Sheepdogs> thisadmin = session.selectList(statement,username);
		if(thisadmin.size() ==0) {
			GetLevel6AdminDogNum.put("dogtotalnum", 0);
			GetLevel6AdminDogNum.put("neckletedtotal", 0);
		}
		else {
			GetLevel6AdminDogNum.put("dogtotalnum", thisadmin.size());
			int i = 0;
			for(Sheepdogs each:thisadmin) {
				if(!each.getNeckletid().equals("-1")) {
					i++;
				}
			}
			GetLevel6AdminDogNum.put("neckletedtotal", i);
		}
		return GetLevel6AdminDogNum;
    }
	
	
	public String GovToEchartsAreaName(String areaname)
    {
        String echartsareaname = "";
        String statement = "sec.secwatchdog.dao.HamletDao.GovToEchartsAreaName";
        String echartsareanametemp = session.selectOne(statement,areaname);
        if (echartsareanametemp != null && echartsareanametemp.length() !=0)
        {
            echartsareaname = echartsareanametemp;
        }
        return echartsareaname;
    }


    public String EchartsAreaNameToGov(String areaname)
    {
    	String govareaname = "";
        String statement = "sec.secwatchdog.dao.HamletDao.EchartsAreaNameToGov";
        String govareanametemp = session.selectOne(statement,areaname);
        if (govareanametemp != null && govareanametemp.length() !=0)
        {
            govareaname = govareanametemp;
        }
        return govareaname;
    }
	
    
    public Map<String,Object> CombineNeckletAndFeederDogList(PageBean pageBean,String username){
		Map<String,Object> combineneckletandfeederdoglist = new HashMap<String,Object>();
		
		String statement = "sec.secwatchdog.dao.HamletDao.combineneckletandfeederdoglist";
		Map<String, Object> paramsmap = new HashMap<String, Object>();
		paramsmap.put("pageBean1", pageBean);
		if(pageBean!=null){
			paramsmap.put("pagestart", pageBean.getStart());
			paramsmap.put("pagesize", pageBean.getPageSize());
		}
		paramsmap.put("username", username);
		List<Sheepdogs> doglist = new ArrayList<Sheepdogs>();
		doglist = session.selectList(statement,paramsmap);
		int i = 0;
		for(Sheepdogs each:doglist) {
			Map<String, Object> maptemp = new HashMap<String,Object>();
			maptemp.put("dogid", each.getDogid());
			maptemp.put("dogname", each.getDogname());
			String neckletid = each.getNeckletid();
			String feederid = each.getApparatusid();
			if(feederid.equals("-1") && !neckletid.equals("-1")) {
				statement = "sec.secwatchdog.dao.HamletDao.getlastexhibitrealtime";
				Lastexhibitrealtime lb = session.selectOne(statement,neckletid);
				if(lb != null) {
					maptemp.put("neckletid",  neckletid);
					statement = "sec.secwatchdog.dao.HamletDao.getfeedback";
					Feedback feedback = session.selectOne(statement,neckletid);
					maptemp.put("firstmedtime",  feedback.getFirstmedtime());
					maptemp.put("lastmed",  lb.getRealtime());
					maptemp.put("timemed",  feedback.getMedtotal() - lb.getTableremain());
					maptemp.put("nextmed",  lb.getNextexhibittime());
					maptemp.put("exhibitcycle", (Double.parseDouble(feedback.getExhibitcycle())/1440)+"");
				}else {
					maptemp.put("neckletid",  "----");
					maptemp.put("firstmedtime",  "");
					maptemp.put("lastmed",  "");
					maptemp.put("timemed",  0);
					maptemp.put("nextmed",  "");
					maptemp.put("exhibitcycle","10000");
				}
			}else if(!feederid.equals("-1") && neckletid.equals("-1")){
				statement = "sec.secwatchdog.dao.HamletDao.getlastappexhibitrealtime";
				Lastappexhibitrealtime le = session.selectOne(statement,feederid);
				if(le!=null) {
					maptemp.put("feederid", feederid);
					statement = "sec.secwatchdog.dao.HamletDao.getfeederback";
					Feederback fb = session.selectOne(statement,feederid);
					maptemp.put("firstmedtime", fb.getFirstmedtime());
					maptemp.put("lastmed", le.getRealtime());
					maptemp.put("timemed", fb.getMedtotal()-le.getTableremain());
					maptemp.put("nextmed", le.getNextexhibittime());
					maptemp.put("exhibitcycle", "10000");
				}else {
					maptemp.put("feederid", "----");
					maptemp.put("firstmedtime", "");
					maptemp.put("lastmed", "");
					maptemp.put("timemed", 0);
					maptemp.put("nextmed", "");
					maptemp.put("exhibitcycle","10000");
				}
			}
			combineneckletandfeederdoglist.put(""+i, maptemp);
			i++;
		}
		return combineneckletandfeederdoglist;
	}
    
    public Integer CombineNeckletAndFeederDogTotal(String username) {
    	String statement = "sec.secwatchdog.dao.HamletDao.combineneckletandfeederdogtotal";
		int total = session.selectOne(statement,username);
		return total;
    }
}
