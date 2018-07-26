package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sec.secwatchdog.model.Lastexhibitrealtime;
import sec.secwatchdog.model.Lastneckletrealtime;

@Mapper
public interface LastexhibitrealtimeDao {

    @Select("select neckletid, realtime,tableremain,nextexhibittime from lastexhibitrealtime where neckletid=#{neckletid}")
    public Lastexhibitrealtime getLastexhibitrealtime(String neckletid);
}
