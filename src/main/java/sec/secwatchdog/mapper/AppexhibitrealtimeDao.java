package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AppexhibitrealtimeDao {
	@Select("select count(*) from appexhibitrealtime")
	public int getAppExhiCount();
	
	@Select("select count(*) from appexhibitrealtime where districtcode like concat(#{districtCode},'%')")
	public int getAppExhiCountByDistrictcode(String districtCode);
	
	@Select("select count(*) from appexhibitrealtime where districtcode like concat('66','%')")
	public int getArmyFeederNum();
}
