package sec.secwatchdog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Feeder {
	@Id
    @GeneratedValue
	public long id;

	public String apparatusid;

	public int apparatushealthy;

	public String apparatus;

	public int dogid;

	public String logintime;

	public String retiretime;

	public int medtotal;

	public String category;

	public String username;

	public long districtcode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApparatusid() {
		return apparatusid;
	}

	public void setApparatusid(String apparatusid) {
		this.apparatusid = apparatusid;
	}

	public int getApparatushealthy() {
		return apparatushealthy;
	}

	public void setApparatushealthy(int apparatushealthy) {
		this.apparatushealthy = apparatushealthy;
	}

	public String getApparatus() {
		return apparatus;
	}

	public void setApparatus(String apparatus) {
		this.apparatus = apparatus;
	}

	public int getDogid() {
		return dogid;
	}

	public void setDogid(int dogid) {
		this.dogid = dogid;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getRetiretime() {
		return retiretime;
	}

	public void setRetiretime(String retiretime) {
		this.retiretime = retiretime;
	}

	public int getMedtotal() {
		return medtotal;
	}

	public void setMedtotal(int medtotal) {
		this.medtotal = medtotal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(long districtcode) {
		this.districtcode = districtcode;
	}
}
