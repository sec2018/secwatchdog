package sec.secwatchdog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.SysLayConfigDao;
import sec.secwatchdog.model.SysLayConfig;
import sec.secwatchdog.service.SysLayConfigService;

@Service("sysLayConfigService")
public class SysLayConfigServiceImpl implements SysLayConfigService{

	@Resource
	private SysLayConfigDao sysLayConfigDao;
	
	@Override
	public SysLayConfig getLayConfByMid(String mid) {
		// TODO Auto-generated method stub
		return sysLayConfigDao.getLayConfByMid(mid);
	}

	
}
