package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sec.secwatchdog.model.Lastapparatusrealtime;

@Mapper
public interface LastapparatusrealtimeDao {

    @Select("select apparatuslongitude,apparatusvdoing from lastapparatusrealtime where apparatusid = #{apparatusid}")
    public Lastapparatusrealtime getLastapparatusrealtime(String apparatusid);
}
