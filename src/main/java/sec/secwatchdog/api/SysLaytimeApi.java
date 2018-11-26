package sec.secwatchdog.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import sec.secwatchdog.mapper.SysLayconfigMapper;
import sec.secwatchdog.mapper.SysLaytimeMapper;
import sec.secwatchdog.model.SysLayconfig;
import sec.secwatchdog.model.SysLaytime;
import sec.secwatchdog.service.impl.SysLaytimeExample;
import sec.secwatchdog.util.JsonResult;

@RequestMapping("api")
@Controller
public class SysLaytimeApi {
	
	@Autowired
	private SysLaytimeMapper sysLaytimeMapper;

	@ApiOperation(value = "通过项圈编号查询项圈反馈信息", notes = "通过项圈编号查询项圈反馈信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "mid", value = "项圈标识", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value="getlaytimebynecid",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<JsonResult> GetLayTimeByNeckletId(@RequestParam(value = "mid")String mid){
        JsonResult r = new JsonResult();
        try {
        	SysLaytimeExample example = new SysLaytimeExample();
        	SysLaytimeExample.Criteria criteria = example.createCriteria();
        	criteria.andMidEqualTo(mid);
        	List<SysLaytime> layconfig  = sysLaytimeMapper.selectByExample(example);
            r.setCode(200);
            r.setMsg("获取项圈反馈成功！");
            r.setData(layconfig);
            r.setSuccess(true);
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("获取项圈反馈失败");
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
	
	
	@ApiOperation(value = "获取所有项圈当前位置", notes = "获取所有项圈当前位置")
	@RequestMapping(value="getdeviceposition",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JsonResult> GetDevicePosition(){
        JsonResult r = new JsonResult();
        try {
        	List<SysLaytime> allDevicePosition  = sysLaytimeMapper.getAllDevicePosition();
            r.setCode(200);
            r.setMsg("获取所有项圈位置成功！");
            r.setData(allDevicePosition);
            r.setSuccess(true);
        } catch (Exception e) {
            r.setCode(500);
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("获取所有项圈位置失败");
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
