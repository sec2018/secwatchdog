package sec.secwatchdog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.SysLayTimeDao;
import sec.secwatchdog.service.SysLayTimeService;

@Service("sysLayTimeService")
public class SysLayTimeServiceImpl implements SysLayTimeService{

	@Resource
	private SysLayTimeDao sysLayTimeDao;
	
	
	@Override
	public SysLayTimeDao getLastLayTimeByMid(String mid) {
		// TODO Auto-generated method stub
		return sysLayTimeDao.getLastLayTimeByMid(mid);
	}

}
