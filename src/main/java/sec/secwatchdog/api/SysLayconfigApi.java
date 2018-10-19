package sec.secwatchdog.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ApiOperation(value = "查询所有角色列表", notes = "查询所有角色列表")
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
}
