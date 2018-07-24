package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Managers;

@Mapper
public interface ManagersDao {
	@Select("select * from managers where userName=#{ManagerName}")
	public Managers login(String ManagerName);
	
	@Select("select * from managers where userName=#{ManagerName}")
	public Managers checkLogin(String ManagerName);
	
	@Select("select privilegelevel from managers ")
	public List<Integer> getManagerLevel();

	@Select("select privilegelevel from managers where province=#{provinceName}")
	public List<Integer> getProvinceManagerLevelAndBelowByDistrictName(String provinceName);
	
	@Select("select count(*) from managers where province = '建设兵团'")
	public int getArmyManagerNum();
	
	@Select("select privilegelevel from managers where province=#{param1} and city=#{param2}")
	public List<Integer> getCityManagerAndBelowByDistrictName(String provinceName,String cityName);

	@Select("select privilegelevel from managers where province=#{param1} and city=#{param2} and county=#{param3}")
	public List<Integer> getCountyManagerAndBelowByDistrictName(String provinceName,String cityName, String countyName);

	@Select("select privilegelevel from managers where province=#{param1} and city=#{param2} and county=#{param3} and village=#{param4}")
	public List<Integer> getVillageManagerAndBelowByDistrictName(String provinceName,String cityName, String countyName, String villageName);

	@Select("select privilegelevel from managers where province=#{param1} and city=#{param2} and county=#{param3} and village=#{param4} and hamlet=#{param5}")
	public List<Integer> getHamletManagerByDistrictName(String provinceName,String cityName, String countyName, String villageName ,String hamletName);
}
