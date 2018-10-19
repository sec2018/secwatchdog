package sec.secwatchdog.api;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import sec.secwatchdog.model.SysLayconfig;
import sec.secwatchdog.service.impl.SysLayconfigExample;
import sec.secwatchdog.util.JsonResult;

@RequestMapping("api")
@Controller
public class SysLayconfigApi {
	
	@Autowired
	private SysLayconfigMapper sysLayconfigMapper;
	
	@ApiOperation(value = "查询所有项圈配置列表", notes = "查询所有项圈配置列表")
	@RequestMapping(value="getlayconfiglist",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<JsonResult> GetLayConfigList(){
        JsonResult r = new JsonResult();
        try {
        	SysLayconfigExample example = new SysLayconfigExample();
        	List<SysLayconfig> layconfiglist  = sysLayconfigMapper.selectByExample(example);
            r.setCode("200");
            r.setMsg("获取所有项圈配置列表成功！");
            r.setData(layconfiglist);
            r.setSuccess(true);
        } catch (Exception e) {
            r.setCode(500+"");
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("获取所有项圈配置列表失败");
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
            r.setCode("200");
            r.setMsg("获取项圈时间配置成功！");
            r.setData(layconfig);
            r.setSuccess(true);
        } catch (Exception e) {
            r.setCode(500+"");
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("获取项圈时间配置失败");
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
        	layconfig.setUpdatetime(new Date());
        	boolean flag  = sysLayconfigMapper.insert(layconfig)==1?true:false;
        	if(flag) {
        		r.setCode("200");
                r.setMsg("配置项圈时间成功！");
                r.setData(layconfig);
                r.setSuccess(true);
        	}else {
        		r.setCode(500+"");
                r.setData(null);
                r.setMsg("配置项圈时间失败");
                r.setSuccess(false);
        	}
        } catch (Exception e) {
            r.setCode(500+"");
            r.setData(e.getClass().getName() + ":" + e.getMessage());
            r.setMsg("配置项圈时间失败");
            r.setSuccess(false);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
