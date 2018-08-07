package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Necklet;

@Mapper
public interface NeckletDao {
	@Select("select id,neckletid from necklet where dogid = -1 and username = #{username}")
	public List<Necklet> getNeckletsList(String username);
}
