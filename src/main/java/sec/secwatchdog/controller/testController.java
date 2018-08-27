package sec.secwatchdog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sec.secwatchdog.config.DuplicateSubmitToken ;
//@Controller
@RestController
@RequestMapping("/test")
public class testController {

	//@ResponseBody
	@RequestMapping("/1")
	@DuplicateSubmitToken (save = true)
	public String test1(HttpServletRequest request) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "test1";
	}
	
	//@ResponseBody
	@PostMapping("/2")
	public String test2() {

		return "test2";
	}
}
