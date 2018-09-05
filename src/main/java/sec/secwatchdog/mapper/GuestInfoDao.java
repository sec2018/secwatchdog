package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Guestinfo;

@Mapper
public interface GuestInfoDao {

	@Select("select * from guestinfo where feederid = '-1'")
	public List<Guestinfo> getGuestNeckletInfo();
	
	@Select("select * from guestinfo where neckletid = '-1'")
	public List<Guestinfo> getGuestFeederInfo();
	
}
