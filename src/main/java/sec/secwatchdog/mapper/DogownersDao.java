package sec.secwatchdog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Dogowners;


@Mapper
public interface DogownersDao {
	@Select("select ownerid,ownername,districtcode from dogowners where districtcode like concat(#{districtcode},'%')")
	public List<Dogowners> getDogownersList(String districtcode);
	
	@Select("select * from dogowners where ownername =#{ownername}")
	public Dogowners getOwnerByName(String ownername);
	@Select("select * from dogowners where ownerid =#{dogownerid}")
	public Dogowners getOwnerById(int dogownerid);
	
	@Insert("insert into dogowners(ownername, ownertelphone, owneraddress, ownerstatus, ownerretiretime, owneridentity, ownersex, ownerage, ownerjob, districtcode) values "
			+ "(#{ownername}, #{ownertelphone}, #{owneraddress}, #{ownerstatus}, #{ownerretiretime}, #{owneridentity}, #{ownersex}, #{ownerage}, #{ownerjob}, #{districtcode})")
	public void addOwner(Dogowners owner);
}
