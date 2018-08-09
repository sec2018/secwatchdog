package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Necklet;

@Mapper
public interface NeckletDao {
	@Select("select id,neckletid from necklet where dogid = -1 and username = #{username}")
	public List<Necklet> getNeckletsList(String username);
	@Select("select * from necklet where neckletid = #{neckletid}")
	public Necklet getNeckletByNeckletid(String neckletid);
	@Insert("insert into necklet(neckletid, dogid, logintime, retiretime, medtotal, category, username) values "
			+ "(#{neckletid}, #{dogid}, #{logintime}, #{retiretime}, #{medtotal}, #{category}, #{username})")
	public void addNecklet(Necklet necklet);
	@Update("update necklet set dogid = #{dogid} where id = #{id}")
	public void updateNecklet(Necklet necklet);
}
