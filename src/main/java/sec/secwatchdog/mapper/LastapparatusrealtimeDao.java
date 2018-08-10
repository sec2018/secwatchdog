package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Lastapparatusrealtime;
import sec.secwatchdog.model.Lastneckletrealtime;

@Mapper
public interface LastapparatusrealtimeDao {

    @Select("select * from lastapparatusrealtime where apparatusid = #{apparatusid}")
    public Lastapparatusrealtime getLastapparatusrealtime(String apparatusid);
    
    @Insert("insert into lastapparatusrealtime(feederrealtime,apparatusid,apparatuspower, apparatuslongitude, apparatusvdoing, apparatushealthy, apparatusbug,districtcode) values "
			+ "(#{feederrealtime},#{apparatusid},#{apparatuspower}, #{apparatuslongitude}, #{apparatusvdoing}, #{apparatushealthy}, #{apparatusbug}, #{districtcode})")
	public void addLastapparatusrealtime(Lastapparatusrealtime lastapparatusrealtime);
    
    @Update("update lastapparatusrealtime set districtcode = #{districtcode} where id = #{id}")
 	public void updateLastapparatusrealtime(Lastapparatusrealtime lastapparatusrealtime);
}
