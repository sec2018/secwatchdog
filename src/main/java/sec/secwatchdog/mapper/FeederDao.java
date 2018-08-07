package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Feeder;


@Mapper
public interface FeederDao {
	@Select("select id,apparatusid from feeder where dogid = -1 and username = #{username}")
	public List<Feeder> getFeedersList(String username);
}
