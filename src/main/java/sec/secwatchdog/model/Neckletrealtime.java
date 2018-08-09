package sec.secwatchdog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Neckletrealtime {
	@Id
    @GeneratedValue
	public long id;

	public String realtime;

	public String neckletid;

	public String neckletpower;

	public String neckletlongitude;

	public String neckletvdoing;

	public int necklethealthy;

	public String neckletbug;

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

	public String getNeckletid() {
		return neckletid;
	}

	public void setNeckletid(String neckletid) {
		this.neckletid = neckletid;
	}

	public String getNeckletpower() {
		return neckletpower;
	}

	public void setNeckletpower(String neckletpower) {
		this.neckletpower = neckletpower;
	}

	public String getNeckletlongitude() {
		return neckletlongitude;
	}

	public void setNeckletlongitude(String neckletlongitude) {
		this.neckletlongitude = neckletlongitude;
	}

	public String getNeckletvdoing() {
		return neckletvdoing;
	}

	public void setNeckletvdoing(String neckletvdoing) {
		this.neckletvdoing = neckletvdoing;
	}

	public int getNecklethealthy() {
		return necklethealthy;
	}

	public void setNecklethealthy(int necklethealthy) {
		this.necklethealthy = necklethealthy;
	}

	public String getNeckletbug() {
		return neckletbug;
	}

	public void setNeckletbug(String neckletbug) {
		this.neckletbug = neckletbug;
	}
	
	
}
