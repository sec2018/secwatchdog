package sec.secwatchdog.api;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class SysDeviceConfApi {
	
	@Autowired
	private SysDeviceconfMapper sysDeviceconfMapper;
	
	@Autowired
    private RedisService redisService;

	@ApiOperation(value = "通过项圈编号查询项圈配置信息", notes = "通过项圈编号查询项圈配置信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "mid", value = "项圈标识", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value="getdeviceconfigbynecid",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JsonResult> GetLayConfigByNeckletId(@RequestParam(value = "mid")String mid){
        JsonResult r = new JsonResult();
        try {
        	SysDeviceconf deviceconfig  = sysDeviceconfMapper.selectDeviceConfigByMid(mid);
        	if(deviceconfig!=null) {
        		r.setCode(200);
                r.setMsg("获取项圈配置信息成功！");
                r.setData(deviceconfig);
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
	
	
	
	@ApiOperation(value = "通过项圈编号配置项圈信息", notes = "通过项圈编号配置项圈信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "mid", value = "项圈标识", required = true, dataType = "String",paramType = "query"),
//        @ApiImplicitParam(name = "status", value = "投药状态", required = true, dataType = "Byte",paramType = "query"),
        @ApiImplicitParam(name = "simccid", value = "SIM_CCID", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "swver", value = "版本号", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "ip", value = "ip地址", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "port", value = "端口号", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "infoupdatecycle", value = "信息上传周期", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "tickcycle", value = "心跳周期", required = true, dataType = "Integer",paramType = "query"),
        @ApiImplicitParam(name = "ledenable", value = "led使能", required = true, dataType = "Byte",paramType = "query"),
        @ApiImplicitParam(name = "tempflag", value = "临时投药标志", required = true, dataType = "Byte",paramType = "query"),
        @ApiImplicitParam(name = "tempgmt", value = "临时投药时间", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "clearerr", value = "清除故障标志", required = true, dataType = "Byte",paramType = "query"),
        @ApiImplicitParam(name = "factory", value = "恢复出厂设置", required = true, dataType = "Byte",paramType = "query")
	})
	@RequestMapping(value="setdeviceconfigbynecid",method = RequestMethod.POST)
	@Transactional
    @ResponseBody
    public ResponseEntity<JsonResult> InsertLayConfigByNeckletId(@RequestParam(value = "mid")String mid,
    		@RequestParam(value = "status")Byte status,@RequestParam(value = "simccid")String simccid,@RequestParam(value = "swver")String swver,
    		@RequestParam(value = "ip")String ip,@RequestParam(value = "port")Integer port,
    		@RequestParam(value = "infoupdatecycle")Integer infoupdatecycle,@RequestParam(value = "tickcycle")Integer tickcycle,
    		@RequestParam(value = "ledenable")Byte ledenable,@RequestParam(value = "tempflag")Byte tempflag,
    		@RequestParam(value = "tempgmt")String tempgmt,@RequestParam(value = "clearerr")Byte clearerr,
    		@RequestParam(value = "factory")Byte factory){
        JsonResult r = new JsonResult();
        try {
        	SysDeviceconf sysDeviceconf = sysDeviceconfMapper.selectDeviceConfigByMid(mid);
        	sysDeviceconf.setSimccid(simccid);
        	sysDeviceconf.setSwver(swver);
        	sysDeviceconf.setIp(ip);
        	sysDeviceconf.setPort(port);
        	sysDeviceconf.setInfoupdatecycle(infoupdatecycle);
        	sysDeviceconf.setTickcycle(tickcycle);
        	sysDeviceconf.setLedenable(ledenable);
        	sysDeviceconf.setTemporaryflag(tempflag);
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        	sysDeviceconf.setTemporarygmt(format.parse(tempgmt));
        	byte uimodifyflag = 1;
        	sysDeviceconf.setUimodifyflag(uimodifyflag);
        	byte hardmodifyflag = 0;
        	sysDeviceconf.setHardmodifyflag(hardmodifyflag);
        	sysDeviceconf.setUpdatetime(new Date());
        	sysDeviceconf.setClearerr(clearerr);
        	sysDeviceconf.setFactory(factory);
        	
        	boolean flag  = sysDeviceconfMapper.updateByPrimaryKey(sysDeviceconf)==1?true:false;
        	boolean flag2  = false;
        	if(flag) {
        		String command03 = Analyse.Command_03_Send(sysDeviceconf);
        		flag2  = redisService.setpersist("device_"+mid, command03);
        	}
        	if(flag2) {
        		r.setCode(200);
                r.setMsg("项圈信息入库成功！");
                r.setData(null);
                r.setSuccess(true);
        	}else {
        		r.setCode(500);
                r.setData(null);
                r.setMsg("服务器忙，配置项圈信息失败");
                r.setSuccess(false);
        	}
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("项圈信息入库失败");
            r.setSuccess(false);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
