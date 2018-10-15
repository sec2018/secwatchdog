package sec.secwatchdog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.SysDeviceConf;


@Mapper
public interface SysDeviceConfDao {
	@Select("select * from sys_deviceconf where mid = #{mid}")
	public SysDeviceConf getDeviceConfByMid(String mid);
}
