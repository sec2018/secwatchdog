package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.SysDeviceConf;

@Mapper
public interface SysLayTimeDao {

	@Select("SELECT * FROM sys_laytime where mid = #{mid} order by timegmt desc limit 1")
	public SysLayTimeDao getLastLayTimeByMid(String mid);
	
}
