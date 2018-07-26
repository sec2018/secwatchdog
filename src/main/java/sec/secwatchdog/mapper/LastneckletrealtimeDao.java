package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sec.secwatchdog.model.Lastneckletrealtime;

@Mapper
public interface LastneckletrealtimeDao {
    @Select("select neckletlongitude,neckletvdoing from lastneckletrealtime where neckletid = #{neckletid}")
    public Lastneckletrealtime getLastneckletrealtime(String neckletid);
}
