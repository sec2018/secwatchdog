package sec.secwatchdog.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import sec.secwatchdog.mapper.SysDeviceconfMapper;
import sec.secwatchdog.mapper.SysLayconfigMapper;
import sec.secwatchdog.model.SysDeviceconf;
import sec.secwatchdog.model.SysLayconfig;
import sec.secwatchdog.redis.service.RedisService;
import sec.secwatchdog.service.impl.SysLayconfigExample;
import sec.secwatchdog.util.JsonResult;

@RequestMapping("api")
@Controller
public class SysLayconfigApi {
	
	@Autowired
	private SysLayconfigMapper sysLayconfigMapper;
	
	@Autowired
	private SysDeviceconfMapper sysDeviceconfMapper;
	
	@Autowired
    private RedisService redisService;
	
	@ApiOperation(value = "查询所有项圈配置列表", notes = "查询所有项圈配置列表")
	@RequestMapping(value="getlayconfiglist",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JsonResult> GetLayConfigList(){
        JsonResult r = new JsonResult();
        try {
        	SysLayconfigExample example = new SysLayconfigExample();
        	List<SysLayconfig> layconfiglist  = sysLayconfigMapper.selectByExample(example);
            r.setCode(200);
            r.setMsg("获取所有项圈配置列表成功！");
            r.setData(layconfiglist);
            r.setSuccess(true);
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("该项圈不存在，请先添加！");
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
	
	@ApiOperation(value = "通过项圈编号查询项圈配置时间", notes = "通过项圈编号查询项圈配置时间")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "mid", value = "项圈标识", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value="getlayconfigbynecid",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JsonResult> GetLayConfigByNeckletId(@RequestParam(value = "mid")String mid){
        JsonResult r = new JsonResult();
        try {
        	SysLayconfig layconfig  = sysLayconfigMapper.selectLayConfigByMid(mid);
        	if(layconfig != null) {
        		r.setCode(200);
                r.setMsg("获取项圈时间配置成功！");
                r.setData(layconfig);
                r.setSuccess(true);
        	}else {
        		 r.setCode(500);
                 r.setData(null);
                 r.setMsg("该项圈不存在，请先添加！");
                 r.setSuccess(false);
        	}
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("该项圈不存在，请先添加！");
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
	
	
	@ApiOperation(value = "通过项圈编号配置项圈时间", notes = "通过项圈编号配置项圈时间")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "mid", value = "项圈标识", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "one", value = "第1次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "two", value = "第2次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "three", value = "第3次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "four", value = "第4次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "five", value = "第5次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "six", value = "第6次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "seven", value = "第7次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "eight", value = "第8次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "nine", value = "第9次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "ten", value = "第10次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "eleven", value = "第11次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "twelve", value = "第12次投药时间", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value="setlayconfigbynecid",method = RequestMethod.POST)
	@Transactional
    @ResponseBody
    public ResponseEntity<JsonResult> InsertLayConfigByNeckletId(@RequestParam(value = "mid")String mid,
    		@RequestParam(value = "one")String one,@RequestParam(value = "two")String two,
    		@RequestParam(value = "three")String three,@RequestParam(value = "four")String four,
    		@RequestParam(value = "five")String five,@RequestParam(value = "six")String six,
    		@RequestParam(value = "seven")String seven,@RequestParam(value = "eight")String eight,
    		@RequestParam(value = "nine")String nine,@RequestParam(value = "ten")String ten,
    		@RequestParam(value = "eleven")String eleven,@RequestParam(value = "twelve")String twelve){
        JsonResult r = new JsonResult();
        try {
        	SysLayconfig layconfig = new SysLayconfig();
        	layconfig.setId(0);
        	layconfig.setMid(mid);
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        	layconfig.setOne(format.parse(one));
        	layconfig.setTwo(format.parse(two));
        	layconfig.setThree(format.parse(three));
        	layconfig.setFour(format.parse(four));
        	layconfig.setFive(format.parse(five));
        	layconfig.setSix(format.parse(six));
        	layconfig.setSeven(format.parse(seven));
        	layconfig.setEight(format.parse(eight));
        	layconfig.setNine(format.parse(nine));
        	layconfig.setTen(format.parse(ten));
        	layconfig.setEleven(format.parse(eleven));
        	layconfig.setTwelve(format.parse(twelve));
        	layconfig.setUimodifyflag(Byte.valueOf("1"));
        	layconfig.setHardmodifyflag(Byte.valueOf("0"));
        	layconfig.setUpdatetime(new Date());
        	sysLayconfigMapper.updateOtherLayConfigflag(mid);
        	boolean flag  = sysLayconfigMapper.insert(layconfig)==1?true:false;
        	boolean flag2  = false;
        	if(flag) {
        		String command02 = Analyse.Command_02_Send(layconfig);
        		redisService.remove("time_"+mid);
        		flag2  = redisService.setpersist("time_"+mid, command02);
        	}
        	if(flag2) {
        		//删除最老的一条记录
        		SysLayconfigExample example = new SysLayconfigExample();
        		SysLayconfigExample.Criteria criteria = example.createCriteria();
        		criteria.andMidEqualTo(mid);
                int num = sysLayconfigMapper.countByExample(example);
                if(num == 4) {
            		sysLayconfigMapper.deleteOldestLayConfigByMid(mid);
                }
        		r.setCode(200);
                r.setMsg("项圈时间配置入库成功！");
                r.setData(layconfig);
                r.setSuccess(true);
        	}else {
        		r.setCode(500);
                r.setData(null);
                r.setMsg("项圈时间配置入库失败");
                r.setSuccess(false);
        	}
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("项圈时间配置入库失败");
            r.setSuccess(false);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
	
	
	@ApiOperation(value = "添加设备（项圈）", notes = "添加设备（项圈）")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "mid", value = "项圈标识", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "one", value = "第1次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "two", value = "第2次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "three", value = "第3次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "four", value = "第4次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "five", value = "第5次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "six", value = "第6次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "seven", value = "第7次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "eight", value = "第8次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "nine", value = "第9次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "ten", value = "第10次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "eleven", value = "第11次投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "twelve", value = "第12次投药时间", required = true, dataType = "String",paramType = "query"),
//        @ApiImplicitParam(name = "status", value = "投药状态", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "simccid", value = "SIM_CCID", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "swver", value = "版本号", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "ip", value = "ip地址", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "port", value = "端口号", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "infoupdatecycle", value = "信息上传周期", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "tickcycle", value = "心跳周期", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "ledenable", value = "led使能", required = true, dataType = "Byte",paramType = "query"),
        @ApiImplicitParam(name = "tempflag", value = "临时投药标志", required = true, dataType = "Byte",paramType = "query"),
        @ApiImplicitParam(name = "tempgmt", value = "临时投药时间", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value="adddevice",method = RequestMethod.POST)
	@Transactional
    @ResponseBody
    public ResponseEntity<JsonResult> AddDevice(@RequestParam(value = "mid")String mid,
    		@RequestParam(value = "one")String one,@RequestParam(value = "two")String two,
    		@RequestParam(value = "three")String three,@RequestParam(value = "four")String four,
    		@RequestParam(value = "five")String five,@RequestParam(value = "six")String six,
    		@RequestParam(value = "seven")String seven,@RequestParam(value = "eight")String eight,
    		@RequestParam(value = "nine")String nine,@RequestParam(value = "ten")String ten,
    		@RequestParam(value = "eleven")String eleven,@RequestParam(value = "twelve")String twelve,
    		@RequestParam(value = "simccid")String simccid,@RequestParam(value = "swver")String swver,
    		@RequestParam(value = "ip")String ip,@RequestParam(value = "port")Integer port,
    		@RequestParam(value = "infoupdatecycle")Integer infoupdatecycle,@RequestParam(value = "tickcycle")Integer tickcycle,
    		@RequestParam(value = "ledenable")Byte ledenable,@RequestParam(value = "tempflag")Byte tempflag,
    		@RequestParam(value = "tempgmt")String tempgmt){
        JsonResult r = new JsonResult();
        try {
        	SysLayconfig layconfig = new SysLayconfig();
        	layconfig.setId(0);
        	layconfig.setMid(mid);
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        	layconfig.setOne(format.parse(one));
        	layconfig.setTwo(format.parse(two));
        	layconfig.setThree(format.parse(three));
        	layconfig.setFour(format.parse(four));
        	layconfig.setFive(format.parse(five));
        	layconfig.setSix(format.parse(six));
        	layconfig.setSeven(format.parse(seven));
        	layconfig.setEight(format.parse(eight));
        	layconfig.setNine(format.parse(nine));
        	layconfig.setTen(format.parse(ten));
        	layconfig.setEleven(format.parse(eleven));
        	layconfig.setTwelve(format.parse(twelve));
        	layconfig.setUimodifyflag(Byte.valueOf("1"));
        	layconfig.setHardmodifyflag(Byte.valueOf("0"));
        	layconfig.setUpdatetime(new Date());
        	boolean flag01  = sysLayconfigMapper.insert(layconfig)==1?true:false;
        	
        	SysDeviceconf sysDeviceconf = new SysDeviceconf();
        	sysDeviceconf.setId(0);
        	sysDeviceconf.setMid(mid);
        	sysDeviceconf.setStatus(0);
        	sysDeviceconf.setSimccid(simccid);
        	sysDeviceconf.setSwver(swver);
        	sysDeviceconf.setIp(ip);
        	sysDeviceconf.setPort(port);
        	sysDeviceconf.setInfoupdatecycle(infoupdatecycle);
        	sysDeviceconf.setTickcycle(tickcycle);
        	sysDeviceconf.setLedenable(ledenable);
        	sysDeviceconf.setTemporaryflag(tempflag);
//        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        	sysDeviceconf.setTemporarygmt(format.parse(tempgmt));
        	sysDeviceconf.setClearerr(Byte.valueOf("0"));
        	sysDeviceconf.setFactory(Byte.valueOf("0"));
        	byte uimodifyflag = 1;
        	sysDeviceconf.setUimodifyflag(uimodifyflag);
        	byte hardmodifyflag = 0;
        	sysDeviceconf.setHardmodifyflag(hardmodifyflag);
        	sysDeviceconf.setUpdatetime(new Date());
        	
        	boolean flag02  = sysDeviceconfMapper.insert(sysDeviceconf)==1?true:false;
        	boolean flag2  = false;
        	boolean flag3 = false;
        	if(flag01 && flag02) {
        		String command03 = Analyse.Command_03_Send(sysDeviceconf);
        		redisService.remove("device_"+mid);
        		flag2  = redisService.setpersist("device_"+mid, command03);
        		
        		String command02 = Analyse.Command_02_Send(layconfig);
        		redisService.remove("time_"+mid);
        		flag3  = redisService.setpersist("time_"+mid, command02);
        	}
        	if(flag2 && flag3) {
        		r.setCode(200);
                r.setMsg("添加项圈成功！");
                r.setData(null);
                r.setSuccess(true);
        	}else {
        		r.setCode(500);
                r.setData(null);
                r.setMsg("添加项圈失败！");
                r.setSuccess(false);
        	}
        	
        	
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("添加项圈失败");
            r.setSuccess(false);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
