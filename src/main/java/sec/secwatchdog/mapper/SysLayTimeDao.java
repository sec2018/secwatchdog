package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.SysDeviceConf;

public interface SysLayTimeDao {

	@Select("SELECT * FROM sys_laytime where mid = #{mid} order by timegmt desc limit 1")
	public SysLayTimeDao getLastLayTimeByMid(String mid);
	
}
