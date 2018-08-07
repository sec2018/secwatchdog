package sec.secwatchdog.model;

import javax.persistence.Entity;

@Entity
public class Necklet {

	public long id;

	public String neckletid;

	public int dogid;

	public String logintime;

	public String retiretime;

	public int medtotal;

	public String category;

	public String username;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNeckletid() {
		return neckletid;
	}

	public void setNeckletid(String neckletid) {
		this.neckletid = neckletid;
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
}
