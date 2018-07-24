package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AppexhibitrealtimeDao {
	@Select("select count(*) from appexhibitrealtime where districtcode like concat(#{districtCode},'%')")
	public int getAppExhiCountByDistrictcode(String districtCode);
}
