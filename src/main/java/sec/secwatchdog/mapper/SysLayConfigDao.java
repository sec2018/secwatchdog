package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.SysLayConfig;


@Mapper
public interface SysLayConfigDao {
	
	@Select("select * from sys_layconfig where mid = #{mid}")
	public SysLayConfig getLayConfByMid(String mid);
}
