package sec.secwatchdog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.SelectProvider;

import sec.secwatchdog.model.Districts;
import sec.secwatchdog.model.Necklet;
import sec.secwatchdog.model.Sheepdogs;
import sec.secwatchdog.util.OrderProvider;


@Mapper
public interface SheepdogsDao {
	@Select("select * from sheepdogs")
	public List<Sheepdogs> getIndexInfor();
	@Select("select * from sheepdogs where dogid=#{dogId}")
	public Sheepdogs getSheepdogbyDogId(int dogId);
	@Select("select dogid from sheepdogs order by dogid desc limit 1")
	public Integer getLastId();
	@Select("select * from sheepdogs where districtcode like concat(#{districtCode},'%') ")//and ((neckletid ='-1' and apparatusid !='-1') or (apparatusid ='-1' and neckletid !='-1'))
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
	
	@Select("select dogid,dogname,neckletid,apparatusid from sheepdogs where username = #{username} and (apparatusid  = '-1' or neckletid = '-1')")
	public List<Sheepdogs>  getFarmDogList(String username);

	@SelectProvider(type = OrderProvider.class, method = "combineNeckletAndFeederDogListStr")
	public List<Sheepdogs> combineNeckletAndFeederDogList(Map<String, Object> paramsmap);

	@Select("SELECT count(*) FROM sheepdogs WHERE districtcode=#{hamletCode}")
	public Integer combiNeckletAndFeederDogTotal(String hamletCode);
	
	@Select("SELECT dogid,dogname,neckletid,apparatusid,managername FROM sheepdogs WHERE neckletid=#{param1} and districtcode=#{param2}")
	public List<Sheepdogs> getCombineNeckletAndFeederDogByNeckletId(String neckletId, String hamletCode);
	
	@Insert("insert into sheepdogs(dogname,neckletid,apparatusid,belonghamlet,username,managername,dogownerid,dogweight,dogcolor,dogage, doginfo,dogstatus,dogretirtime, logintime,dogsex,districtcode) values "
			+ "(#{dogname}, #{neckletid}, #{apparatusid},#{belonghamlet}, #{username}, #{managername}, #{dogownerid}, #{dogweight},#{dogcolor},#{dogage}, #{doginfo},#{dogstatus},#{dogretirtime}, #{logintime},#{dogsex},#{districtcode})")
	public void addSheepDog(Sheepdogs sheepdogs);
	
}


