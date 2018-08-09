package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Lastexhibitrealtime;
import sec.secwatchdog.model.Lastneckletrealtime;
import sec.secwatchdog.model.Neckletrealtime;

@Mapper
public interface LastneckletrealtimeDao {
    @Select("select * from lastneckletrealtime where neckletid = #{neckletid}")
    public Lastneckletrealtime getLastneckletrealtime(String neckletid);
    @Insert("insert into lastneckletrealtime(realtime,neckletid,neckletpower, neckletlongitude, neckletvdoing, necklethealthy, neckletbug,districtcode) values "
			+ "(#{realtime},#{neckletid},#{neckletpower}, #{neckletlongitude}, #{neckletvdoing}, #{necklethealthy}, #{neckletbug}, #{districtcode})")
	public void addLastneckletrealtime(Lastneckletrealtime lastneckletrealtime);
    
    @Update("update lastneckletrealtime set districtcode = #{districtcode} where id = #{id}")
 	public void updateLastneckletrealtime(Lastneckletrealtime lastneckletrealtime);
}
