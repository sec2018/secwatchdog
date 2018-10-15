package sec.secwatchdog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sec.secwatchdog.mapper.SysDeviceConfDao;
import sec.secwatchdog.model.SysDeviceConf;
import sec.secwatchdog.service.SysDeviceConfService;

@Service("sysDeviceConfService")
public class SysDeviceConfServiceImpl implements SysDeviceConfService{

	@Resource
	private SysDeviceConfDao sysDeviceConfDao;
	
	@Override
	public SysDeviceConf getDeviceConfByMid(String mid) {
		// TODO Auto-generated method stub
		return sysDeviceConfDao.getDeviceConfByMid(mid);
	}

}
