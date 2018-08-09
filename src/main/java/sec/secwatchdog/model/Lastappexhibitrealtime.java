package sec.secwatchdog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Lastappexhibitrealtime {
	@Id
    @GeneratedValue
	public long id;

    public String realtime;

    public String apparatusid;

    public String exhibitlongitude;

    public String exhibitvdoing;

    public String nextexhibittime;

    public long districtcode;
    
	public Integer tableremain;

    public String apparatuspower;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRealtime() {
		return realtime;
	}

	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	public String getApparatusid() {
		return apparatusid;
	}

	public void setApparatusid(String apparatusid) {
		this.apparatusid = apparatusid;
	}

	public String getExhibitlongitude() {
		return exhibitlongitude;
	}

	public void setExhibitlongitude(String exhibitlongitude) {
		this.exhibitlongitude = exhibitlongitude;
	}

	public String getExhibitvdoing() {
		return exhibitvdoing;
	}

	public void setExhibitvdoing(String exhibitvdoing) {
		this.exhibitvdoing = exhibitvdoing;
	}

	public String getNextexhibittime() {
		return nextexhibittime;
	}

	public void setNextexhibittime(String nextexhibittime) {
		this.nextexhibittime = nextexhibittime;
	}

	public long getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(long districtcode) {
		this.districtcode = districtcode;
	}

	public Integer getTableremain() {
		return tableremain;
	}

	public void setTableremain(Integer tableremain) {
		this.tableremain = tableremain;
	}

	public String getApparatuspower() {
		return apparatuspower;
	}

	public void setApparatuspower(String apparatuspower) {
		this.apparatuspower = apparatuspower;
	}


}
