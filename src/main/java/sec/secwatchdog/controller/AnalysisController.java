package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sec.secwatchdog.model.Managers;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	
	@RequestMapping("/anaindex")
	public String GoToAnalysisPage(HttpServletRequest request,ModelMap model) throws Exception {
		HttpSession session=request.getSession();
		//session失效，退出登录页面
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		
		Managers manager= (Managers) session.getAttribute("currentUser");
		StringBuilder url = new StringBuilder("index/analysis");
		return url.toString();
	}
	
}
