package sec.secwatchdog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.DistrictsDao;
@Service
public class DistrictCode {
	@Autowired
	private DistrictsDao districtsDao;
	@Autowired
	private NameConversionUtil nameConversionUtil;
	
	public String getDistrictCode(String provincename, String cityname, String countyname, String villagename,String hamletname) {
		 String districtcode = "";
         provincename =nameConversionUtil.EchartsAreaNameToGov(provincename);


         districtcode = districtsDao.getDistrictsByDistrictName(provincename).getDistrictcode();
         if (cityname != "")
         {
             cityname = nameConversionUtil.EchartsAreaNameToGov(cityname);
             String provincecode0to2 = districtcode.substring(0, 2);
             districtcode = districtsDao.getCityAndBelowDistrictsByDistrictName(cityname, provincecode0to2).getDistrictcode();
         }
         if (countyname != "")
         {
             countyname = nameConversionUtil.EchartsAreaNameToGov(countyname);
             String citycode0to4 = districtcode.substring(0, 4);
             districtcode = districtsDao.getCityAndBelowDistrictsByDistrictName(countyname, citycode0to4).getDistrictcode();
               }
         if (villagename != "")
         {
             villagename = nameConversionUtil.EchartsAreaNameToGov(villagename);
             String countycode0to6 = districtcode.substring(0, 6);
             districtcode = districtsDao.getCityAndBelowDistrictsByDistrictName(villagename, countycode0to6).getDistrictcode();
               }
         if (hamletname != "")
         {
             hamletname = nameConversionUtil.EchartsAreaNameToGov(hamletname);
             String villagecode0to9 = districtcode.substring(0, 9);
             districtcode = districtsDao.getCityAndBelowDistrictsByDistrictName(hamletname, villagecode0to9).getDistrictcode();
              }
 
         return districtcode;
	}
}
