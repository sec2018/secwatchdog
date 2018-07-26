package sec.secwatchdog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.SelectProvider;

import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.OrderProvider;


@Mapper
public interface SheepdogsDao {
	@Select("select * from sheepdogs where (neckletid ='-1' and apparatusid !='-1') or (apparatusid ='-1' and neckletid !='-1')")
	public List<Sheepdogs> getIndexInfor();
	@Select("select * from sheepdogs where districtcode like concat(#{districtCode},'%') and ((neckletid ='-1' and apparatusid !='-1') or (apparatusid ='-1' and neckletid !='-1'))")
    public List<Sheepdogs> getIndexInforByDistrictcode(String districtCode);
	@Select("select neckletid from sheepdogs where districtcode like concat(#{districtCode},'%')")
	public List<String> getAllNeckletIdByDistrictcode(String districtCode);
	@Select("select neckletid from sheepdogs where districtcode like concat('66','%')")
	public List<String> getArmyDogNum();


	@Select("select dogid,neckletid,dogname,managername from sheepdogs where districtcode = #{hamletcode} and neckletid != '-1'")
	public List<Sheepdogs> getHamletDogs(String hamletcode);

	@Select("select dogid,dogname,apparatusid,managername from sheepdogs where districtcode = #{hamletcode} and apparatusid !='-1'")
	public List<Sheepdogs> getFarmDogFeederList(String hamletcode);

	@Select("select neckletid from sheepdogs where username = #{username}")
	public List<Sheepdogs>  getLevel6AdminDogNum(String username);

	@SelectProvider(type = OrderProvider.class, method = "combineNeckletAndFeederDogListStr")
	public List<Sheepdogs> combineNeckletAndFeederDogList(Map<String, Object> paramsmap);

	@Select("SELECT count(*) FROM sheepdogs WHERE districtcode=#{hamletCode}")
	public Integer combiNeckletAndFeederDogTotal(String hamletCode);
	
}


