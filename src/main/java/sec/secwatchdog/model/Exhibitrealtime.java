package sec.secwatchdog.model;

import javax.persistence.Entity;

@Entity
public class Exhibitrealtime {
	public int id;
	
	public String realtime;

	public String neckletid;

	public String exhibitlongitude;

	public String exhibitvdoing;

	public String nextexhibittime;

	public long districtcode;

	public int tableremain;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealtime() {
		return realtime;
	}

	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	public String getNeckletid() {
		return neckletid;
	}

	public void setNeckletid(String neckletid) {
		this.neckletid = neckletid;
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

	public int getTableremain() {
		return tableremain;
	}

	public void setTableremain(int tableremain) {
		this.tableremain = tableremain;
	}
 
}
