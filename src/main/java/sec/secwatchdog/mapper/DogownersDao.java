package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Dogowners;


@Mapper
public interface DogownersDao {
	@Select("select ownerid,ownername,districtcode from dogowners where districtcode like concat(#{districtcode},'%')")
	public List<Dogowners> getDogownersList(String districtcode);
}
