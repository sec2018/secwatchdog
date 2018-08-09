package sec.secwatchdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import sec.secwatchdog.model.Apparatusrealtime;
import sec.secwatchdog.model.Neckletrealtime;

@Mapper
public interface ApparatusrealtimeDao {
	@Insert("insert into apparatusrealtime(feederrealtime,apparatusid,apparatuspower, apparatuslongitude, apparatusvdoing, apparatushealthy, apparatusbug) values "
			+ "(#{feederrealtime},#{apparatusid},#{apparatuspower}, #{apparatuslongitude}, #{apparatusvdoing}, #{apparatushealthy}, #{apparatusbug})")
	public void addApparatusrealtime(Apparatusrealtime apparatusrealtime);

}
