package sec.secwatchdog.service;

import java.util.List;
import java.util.Map;

public interface GuestService {

	public List<Map<String,Object>> GetNeckletDogNear(double lng, double lat) throws Exception;
	
	public List<Map<String,Object>> GetFeederDogNear(double lng, double lat) throws Exception;
}
