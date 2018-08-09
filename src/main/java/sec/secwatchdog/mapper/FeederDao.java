package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Feeder;
import sec.secwatchdog.model.Necklet;


@Mapper
public interface FeederDao {
	@Select("select id,apparatusid from feeder where dogid = -1 and username = #{username}")
	public List<Feeder> getFeedersList(String username);
	
	@Select("select * from feeder where apparatusid = #{apparatusid}")
	public Feeder getFeederByFeederid(String apparatusid);
	@Insert("insert into feeder(apparatusid,apparatushealthy,apparatus, dogid, logintime, retiretime, medtotal, category, username,districtcode) values "
			+ "(#{apparatusid}, #{apparatushealthy}, #{apparatus}, #{dogid}, #{logintime}, #{retiretime}, #{medtotal}, #{category}, #{username}, #{districtcode})")
	public void addFeeder(Feeder feeder);
	
	@Update("update feeder set dogid = #{dogid} where id = #{id}")
	public void updateFeeder(Feeder feeder);
}
