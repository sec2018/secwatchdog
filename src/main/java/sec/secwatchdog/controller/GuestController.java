package sec.secwatchdog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;
import sec.secwatchdog.redis.service.RedisService;
import sec.secwatchdog.service.GuestService;
import sec.secwatchdog.util.CommonThreadPool;

@Controller
@RequestMapping("/guest")
public class GuestController {
	
	@Autowired
	private GuestService guestService;
	
	@Autowired
	private RedisService redisService;

	@RequestMapping(value="/api", produces="text/html;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestParam(value="lng")Double lng,
			@RequestParam(value="lat")Double lat) {
		String json = "";
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String ip = request.getRemoteAddr();
		
		json = redisService.get("guest_"+ip);
		if(json == null) {
			Map<String,Object> data = new HashMap<String,Object>();
			List<Map<String,Object>> neckletlist = new ArrayList<Map<String,Object>>();
			Future<Object> neckletlisttemp = CommonThreadPool.submit(()->{
				List<Map<String,Object>> temp = guestService.GetNeckletDogNear(lng, lat);
				return temp;
			});
			List<Map<String,Object>> feederlist = new ArrayList<Map<String,Object>>();
			Future<Object> feederlisttemp = CommonThreadPool.submit(()->{
				List<Map<String,Object>> temp = guestService.GetFeederDogNear(lng, lat);
				return temp;
			});
			try {
				neckletlist = (List<Map<String, Object>>) neckletlisttemp.get();
				feederlist = (List<Map<String, Object>>) feederlisttemp.get();
			}catch(InterruptedException | ExecutionException ex) {
				ex.printStackTrace();
			}
			data.put("data1",neckletlist);
			data.put("data2",feederlist);
			json = JSONObject.fromObject(data).toString();
			redisService.set("guest_"+ip, json);
		}
		return json;
	}
	
}
