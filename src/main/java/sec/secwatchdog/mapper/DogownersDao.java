package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sec.secwatchdog.model.Dogowners;


@Mapper
public interface DogownersDao {
	@Select("select ownerid,ownername,districtcode from dogowners where districtcode like concat(#{districtcode},'%')")
	public List<Dogowners> getDogownersList(String districtcode);
	
	@Select("select * from dogowners where ownername =#{ownername}")
	public Dogowners getOwnerByName(String ownername);
	@Select("select * from dogowners where ownerid =#{dogownerid}")
	public Dogowners getOwnerById(long dogownerid);
	
	@Insert("insert into dogowners(ownername, ownertelphone, owneraddress, ownerstatus, ownerretiretime, owneridentity, ownersex, ownerage, ownerjob, districtcode) values "
			+ "(#{ownername}, #{ownertelphone}, #{owneraddress}, #{ownerstatus}, #{ownerretiretime}, #{owneridentity}, #{ownersex}, #{ownerage}, #{ownerjob}, #{districtcode})")
	public void addOwner(Dogowners owner);
	
	@Update("update dogowners set ownername=#{ownername}, ownertelphone=#{ownertelphone}, owneraddress=#{owneraddress}, owneridentity=#{owneridentity}, ownersex=#{ownersex}, ownerage=#{ownerage}, ownerjob=#{ownerjob} where ownerid=#{ownerid}")
	public void updateOwner(Dogowners owner);
}
