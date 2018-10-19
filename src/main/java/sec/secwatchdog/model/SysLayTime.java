package sec.secwatchdog.model;

import java.util.Date;

public class SysLayTime {
	
	private int id;
	private String mid;
	private String latitude;
	private String longitude;
	private Date grantgmt;
	private String err;
	private double voltage;
	private int temperature;
	private int typ;
	private Date timegmt;
	private Date updatetime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Date getGrantgmt() {
		return grantgmt;
	}
	public void setGrantgmt(Date grantgmt) {
		this.grantgmt = grantgmt;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public double getVoltage() {
		return voltage;
	}
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getTyp() {
		return typ;
	}
	public void setTyp(int typ) {
		this.typ = typ;
	}
	public Date getTimegmt() {
		return timegmt;
	}
	public void setTimegmt(Date timegmt) {
		this.timegmt = timegmt;
	}
	public int getIslay() {
		return islay;
	}
	public void setIslay(int islay) {
		this.islay = islay;
	}
	private int islay;
}
