package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sec.secwatchdog.model.Lastappexhibitrealtime;

@Mapper
public interface LastappexhibitrealtimeDao {

    @Select("select realtime,tableremain,nextexhibittime from lastappexhibitrealtime where apparatusid = #{apparatusid}")
    public Lastappexhibitrealtime getLastappexhibitrealtime(String apparatusid);
}
