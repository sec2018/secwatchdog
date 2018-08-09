package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Lastexhibitrealtime;
import sec.secwatchdog.model.Lastneckletrealtime;
import sec.secwatchdog.model.Necklet;

@Mapper
public interface LastexhibitrealtimeDao {

    @Select("select * from lastexhibitrealtime where neckletid=#{neckletid}")
    public Lastexhibitrealtime getLastexhibitrealtime(String neckletid);
    
    @Insert("insert into lastexhibitrealtime(realtime,neckletid, exhibitlongitude, exhibitvdoing, nextexhibittime,districtcode,tableremain) values "
			+ "(#{realtime},#{neckletid},#{exhibitlongitude}, #{exhibitvdoing}, #{nextexhibittime}, #{districtcode}, #{tableremain})")
	public void addLastexhibitrealtime(Lastexhibitrealtime lastexhibitrealtime);
    
    @Update("update lastexhibitrealtime set districtcode = #{districtcode} where id = #{id}")
	public void updateLastexhibitrealtime(Lastexhibitrealtime lastexhibitrealtime);
}
