package sec.secwatchdog.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Feederback {
	@Id
    @GeneratedValue
	public long id;

    public String apparatusid;

    public String feedercycle;

    public String exhibitcycle;

    public String updatetime;

    public Integer medtotal;

    public String firstmedtime;

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

	public String getFeedercycle() {
		return feedercycle;
	}

	public void setFeedercycle(String feedercycle) {
		this.feedercycle = feedercycle;
	}

	public String getExhibitcycle() {
		return exhibitcycle;
	}

	public void setExhibitcycle(String exhibitcycle) {
		this.exhibitcycle = exhibitcycle;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getMedtotal() {
		return medtotal;
	}

	public void setMedtotal(Integer medtotal) {
		this.medtotal = medtotal;
	}

	public String getFirstmedtime() {
		return firstmedtime;
	}

	public void setFirstmedtime(String firstmedtime) {
		this.firstmedtime = firstmedtime;
	}

	public String getEndmedtime() {
		return endmedtime;
	}

	public void setEndmedtime(String endmedtime) {
		this.endmedtime = endmedtime;
	}

	public String endmedtime;
}
