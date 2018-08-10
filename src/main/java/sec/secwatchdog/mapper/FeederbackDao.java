package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Feedback;
import sec.secwatchdog.model.Feederback;

@Mapper
public interface FeederbackDao {

    @Select("select * from feederback where apparatusid = #{apparatusid}")
    public Feederback getFeederback(String apparatusid);
    
    @Insert("insert into feederback(apparatusid,feedercycle, exhibitcycle,updatetime, medtotal, firstmedtime,endmedtime) values "
			+ "(#{apparatusid},#{feedercycle}, #{exhibitcycle},#{updatetime}, #{medtotal}, #{firstmedtime},#{endmedtime})")
	public void addFeederback(Feederback feederback);
    
    @Update("update feederback set feedercycle=#{feedercycle}, exhibitcycle=#{exhibitcycle},updatetime=#{updatetime}, medtotal=#{medtotal}, firstmedtime=#{firstmedtime},endmedtime=#{endmedtime} where apparatusid=#{apparatusid}")
    public void updateFeederback(Feederback feederback);
}
