package sec.secwatchdog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Apparatusrealtime {
	@Id
	@GeneratedValue
	public long id;

	public String feederrealtime;

	public String apparatusid;

	public String apparatuspower;

	public String apparatuslongitude;

	public String apparatusvdoing;

	public int apparatushealthy;

	public String apparatusbug;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFeederrealtime() {
		return feederrealtime;
	}

	public void setFeederrealtime(String feederrealtime) {
		this.feederrealtime = feederrealtime;
	}

	public String getApparatusid() {
		return apparatusid;
	}

	public void setApparatusid(String apparatusid) {
		this.apparatusid = apparatusid;
	}

	public String getApparatuspower() {
		return apparatuspower;
	}

	public void setApparatuspower(String apparatuspower) {
		this.apparatuspower = apparatuspower;
	}

	public String getApparatuslongitude() {
		return apparatuslongitude;
	}

	public void setApparatuslongitude(String apparatuslongitude) {
		this.apparatuslongitude = apparatuslongitude;
	}

	public String getApparatusvdoing() {
		return apparatusvdoing;
	}

	public void setApparatusvdoing(String apparatusvdoing) {
		this.apparatusvdoing = apparatusvdoing;
	}

	public int getApparatushealthy() {
		return apparatushealthy;
	}

	public void setApparatushealthy(int apparatushealthy) {
		this.apparatushealthy = apparatushealthy;
	}

	public String getApparatusbug() {
		return apparatusbug;
	}

	public void setApparatusbug(String apparatusbug) {
		this.apparatusbug = apparatusbug;
	}

}
