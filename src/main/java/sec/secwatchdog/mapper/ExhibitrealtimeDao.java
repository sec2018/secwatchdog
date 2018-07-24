package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface ExhibitrealtimeDao {
	@Select("select count(*) from exhibitrealtime where districtcode like concat(#{districtCode},'%')")
	public int getExhiCountByDistrictcode(String districtCode);
}
