package sec.secwatchdog.model;

import java.util.Date;

public class SysDeviceConf {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getSwver() {
		return swver;
	}
	public void setSwver(String swver) {
		this.swver = swver;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getInfoupdatecycle() {
		return infoupdatecycle;
	}
	public void setInfoupdatecycle(int infoupdatecycle) {
		this.infoupdatecycle = infoupdatecycle;
	}
	public int getTickcycle() {
		return tickcycle;
	}
	public void setTickcycle(int tickcycle) {
		this.tickcycle = tickcycle;
	}
	public int getLedenable() {
		return ledenable;
	}
	public void setLedenable(int ledenable) {
		this.ledenable = ledenable;
	}
	public int getTemporaryflag() {
		return temporaryflag;
	}
	public void setTemporaryflag(int temporaryflag) {
		this.temporaryflag = temporaryflag;
	}
	public Date getTemporarygmt() {
		return temporarygmt;
	}
	public void setTemporarygmt(Date temporarygmt) {
		this.temporarygmt = temporarygmt;
	}
	private int status;
	private String mid;
	private String swver;
	private String ip;
	private int port;
	private int infoupdatecycle;
	private int tickcycle;
	private int ledenable;
	private int temporaryflag;
	private Date temporarygmt;
	private Date updatetime;
}
