package sec.secwatchdog.service;

import sec.secwatchdog.mapper.SysLayTimeDao;

public interface SysLayTimeService {
	
	public SysLayTimeDao getLastLayTimeByMid(String mid);
}
