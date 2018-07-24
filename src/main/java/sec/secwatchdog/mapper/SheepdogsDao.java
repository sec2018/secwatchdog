package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Sheepdogs;


@Mapper
public interface SheepdogsDao {
	@Select("select * from sheepdogs where (neckletid ='-1' and apparatusid !='-1') or (apparatusid ='-1' and neckletid !='-1')")
	public List<Sheepdogs> getIndexInfor();
	@Select("select * from sheepdogs where districtcode like concat(#{districtCode},'%') and ((neckletid ='-1' and apparatusid !='-1') or (apparatusid ='-1' and neckletid !='-1'))")
    public List<Sheepdogs> getIndexInforByDistrictcode(String districtCode);
	@Select("select neckletid from sheepdogs where districtcode like concat(#{districtCode},'%')")
	public List<String> getAllNeckletIdByDistrictcode(String districtCode);
	@Select("select neckletid from sheepdogs where districtcode like concat('66','%')")
	public List<String> getArmyDogNum();
	

}
