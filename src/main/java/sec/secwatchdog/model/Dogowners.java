package sec.secwatchdog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dogowners {
	@Id
	@GeneratedValue
	public long ownerid;

	public String ownername;

	public String ownertelphone;

	public String owneraddress;

	public int ownerstatus;

	public String ownerretiretime;

	public String owneridentity;

	public String ownersex;

	public int ownerage;

	public String ownerjob;

	public String districtcode;

	public long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(long ownerid) {
		this.ownerid = ownerid;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getOwnertelphone() {
		return ownertelphone;
	}

	public void setOwnertelphone(String ownertelphone) {
		this.ownertelphone = ownertelphone;
	}

	public String getOwneraddress() {
		return owneraddress;
	}

	public void setOwneraddress(String owneraddress) {
		this.owneraddress = owneraddress;
	}

	public int getOwnerstatus() {
		return ownerstatus;
	}

	public void setOwnerstatus(int ownerstatus) {
		this.ownerstatus = ownerstatus;
	}

	public String getOwnerretiretime() {
		return ownerretiretime;
	}

	public void setOwnerretiretime(String ownerretiretime) {
		this.ownerretiretime = ownerretiretime;
	}

	public String getOwneridentity() {
		return owneridentity;
	}

	public void setOwneridentity(String owneridentity) {
		this.owneridentity = owneridentity;
	}

	public String getOwnersex() {
		return ownersex;
	}

	public void setOwnersex(String ownersex) {
		this.ownersex = ownersex;
	}

	public int getOwnerage() {
		return ownerage;
	}

	public void setOwnerage(int ownerage) {
		this.ownerage = ownerage;
	}

	public String getOwnerjob() {
		return ownerjob;
	}

	public void setOwnerjob(String ownerjob) {
		this.ownerjob = ownerjob;
	}

	public String getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}

}
