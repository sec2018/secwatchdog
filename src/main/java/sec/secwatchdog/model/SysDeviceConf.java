package sec.secwatchdog.model;

import java.util.Date;

public class SysDeviceconf extends SysDeviceconfKey {
    private Byte status;

    private String swver;

    private String ip;

    private Integer port;

    private Integer infoupdatecycle;

    private Integer tickcycle;

    private Byte ledenable;

    private Byte temporaryflag;

    private Date temporarygmt;

    private Date updatetime;

    public SysDeviceconf(Integer id, String mid, Byte status, String swver, String ip, Integer port, Integer infoupdatecycle, Integer tickcycle, Byte ledenable, Byte temporaryflag, Date temporarygmt, Date updatetime) {
        super(id, mid);
        this.status = status;
        this.swver = swver;
        this.ip = ip;
        this.port = port;
        this.infoupdatecycle = infoupdatecycle;
        this.tickcycle = tickcycle;
        this.ledenable = ledenable;
        this.temporaryflag = temporaryflag;
        this.temporarygmt = temporarygmt;
        this.updatetime = updatetime;
    }

    public SysDeviceconf() {
        super();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSwver() {
        return swver;
    }

    public void setSwver(String swver) {
        this.swver = swver == null ? null : swver.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getInfoupdatecycle() {
        return infoupdatecycle;
    }

    public void setInfoupdatecycle(Integer infoupdatecycle) {
        this.infoupdatecycle = infoupdatecycle;
    }

    public Integer getTickcycle() {
        return tickcycle;
    }

    public void setTickcycle(Integer tickcycle) {
        this.tickcycle = tickcycle;
    }

    public Byte getLedenable() {
        return ledenable;
    }

    public void setLedenable(Byte ledenable) {
        this.ledenable = ledenable;
    }

    public Byte getTemporaryflag() {
        return temporaryflag;
    }

    public void setTemporaryflag(Byte temporaryflag) {
        this.temporaryflag = temporaryflag;
    }

    public Date getTemporarygmt() {
        return temporarygmt;
    }

    public void setTemporarygmt(Date temporarygmt) {
        this.temporarygmt = temporarygmt;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}