package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Neckletrealtime;
 

@Mapper
public interface NeckletrealtimeDao {
	@Insert("insert into neckletrealtime(realtime,neckletid,neckletpower, neckletlongitude, neckletvdoing, necklethealthy, neckletbug) values "
			+ "(#{realtime},#{neckletid},#{neckletpower}, #{neckletlongitude}, #{neckletvdoing}, #{necklethealthy}, #{neckletbug})")
	public void addNeckletrealtime(Neckletrealtime neckletrealtime);
}
