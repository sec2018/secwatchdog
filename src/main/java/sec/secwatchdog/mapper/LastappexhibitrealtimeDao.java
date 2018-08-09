package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Lastappexhibitrealtime;
import sec.secwatchdog.model.Lastexhibitrealtime;

@Mapper
public interface LastappexhibitrealtimeDao {

    @Select("select * from lastappexhibitrealtime where apparatusid = #{apparatusid}")
    public Lastappexhibitrealtime getLastappexhibitrealtime(String apparatusid);
    
    @Insert("insert into lastappexhibitrealtime(realtime,apparatusid, exhibitlongitude, exhibitvdoing, nextexhibittime,districtcode,tableremain,apparatuspower) values "
			+ "(#{realtime},#{apparatusid},#{exhibitlongitude}, #{exhibitvdoing}, #{nextexhibittime}, #{districtcode}, #{tableremain},#{apparatuspower})")
	public void addLastappexhibitrealtime(Lastappexhibitrealtime lastappexhibitrealtime);
    
    @Update("update lastappexhibitrealtime set districtcode = #{districtcode} where id = #{id}")
   	public void updateLastappexhibitrealtime(Lastappexhibitrealtime lastappexhibitrealtime);
}
