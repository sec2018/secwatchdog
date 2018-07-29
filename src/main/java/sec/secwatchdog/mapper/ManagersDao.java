package sec.secwatchdog.mapper;

import java.util.ArrayList;
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

	@Select("select * from managers where username=#{managerName}")
	public Managers getManagerByName(String managerName);
	
	@Select("select username,managername,logintime,province,workplace,managertel,officecall from managers where privilegelevel=#{privilegelevel}")
	public List<Managers> getManagersByPrivilegelevel(int privilegelevel);
	
	@Select("select * from managers where privilegelevel > #{param1} and ( managername REGEXP #{param2} or province REGEXP #{param2} or city REGEXP #{param2} or village REGEXP #{param2} or hamlet REGEXP #{param2} or managertel REGEXP #{param2} )")
	public List<Managers> getAllManagersByManagerName(int getPrivilegelevel, String managerName);
}
