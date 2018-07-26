package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sec.secwatchdog.model.Feederback;

@Mapper
public interface FeederbackDao {

    @Select("select firstmedtime,medtotal from feederback where apparatusid = #{apparatusid}")
    public Feederback getFeederback(String apparatusid);
}
