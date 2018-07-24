package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagersDao {
	@Select("select privilegelevel from managers where province=#{provinceName}")
	public List<Integer> getProvinceManagerLevelByDistrictName(String provinceName);
	
	@Select("select privilegelevel from managers where province=#{param1} and city=#{param2}")
	public List<Integer> getCityManagerLevelByDistrictName(String provinceName,String cityName);
	
	@Select("select count(*) from managers where province=#{param1} and city=#{param2}")
	public int getCityManagerNumByDistrictName(String provinceName,String cityName);
	
	@Select("select count(*) from managers where province=#{param1} and city=#{param2} and county=#{param3}")
	public int getCountyManagerNumByDistrictName(String provinceName,String cityName, String countyName);
}
