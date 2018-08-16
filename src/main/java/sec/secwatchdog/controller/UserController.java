package sec.secwatchdog.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.UserService;
import sec.secwatchdog.shiro.BusinessException;
import sec.secwatchdog.shiro.LuoErrorCode;
import sec.secwatchdog.util.AESUtil;
import sec.secwatchdog.util.CommonThreadPool;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Resource
	private UserService userService;

	@RequestMapping(value="/login", produces="text/html;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String login(Managers manager,HttpServletRequest request){
		
       AESUtil aes = new AESUtil();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(manager.getUsername(), aes.encryptData(manager.getPassword()));  
            Subject currentUser = SecurityUtils.getSubject();        
                //使用shiro来验证  
            //token.setRememberMe(true);  
            currentUser.login(token);//验证角色和权限
            
          //验证是否登录成功
            if(currentUser.isAuthenticated()){
            	logger.info("用户[" + manager.getUsername() + "]登录认证通过(这里可进行一些认证通过后的系统参数初始化操作)");
		       /*	if(currentUser.hasRole("admin")){
		             System.out.println("有角色admin");
		        }*/
				Managers resultUser=userService.login(manager);
				HttpSession session=request.getSession();
				session.setAttribute("currentUser", resultUser);
				Cookie cookie = new Cookie("rememberuser",manager.getUsername()+"+"+manager.getPassword());
				cookie.setMaxAge(10000);
				JSONObject jsStr = JSONObject.fromObject(resultUser);
				String res = jsStr.toString();
				return res;
		               // return "redirect:/getUI";//重定向至HT-UI
                }else{
                token.clear();
 
                return "";
            }
        }catch(UnknownAccountException uae){
        	logger.warn("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，未知账户");
            request.setAttribute("message_login", "未知账户");
        }catch(IncorrectCredentialsException ice){
        	logger.warn("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，错误的凭证");
            request.setAttribute("message_login", "密码不正确");
        }catch(LockedAccountException lae){
        	logger.warn("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，错误的凭证");
            request.setAttribute("message_login", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
        	logger.warn("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，错误的凭证");
            request.setAttribute("message_login", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
        	logger.warn("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，错误的凭证");
            request.setAttribute("message_login", "用户名或密码不正确");
        }catch(Exception ex){
            throw new BusinessException(LuoErrorCode.LOGIN_VERIFY_FAILURE);
        }
        return "";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request,ModelMap model,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{

		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		Managers resultUser= (Managers) session.getAttribute("currentUser");
	
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;

		switch(resultUser.getPrivilegelevel()) {
			case 1:
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				
				url.append("index");
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("data1",resultUser);
				Map<String,Integer> LogoInfo = new HashMap<String,Integer>();
				Map<String,Object> countrymap = new HashMap<String,Object>();
				Map<String,Object> xinjiangarmycountrymap = new HashMap<String,Object>();
				ExecutorService e = Executors.newCachedThreadPool();
				Future<Object> LogoInfotemp = CommonThreadPool.submit(()->{
					System.out.println("1开始。。。");
					Map<String,Integer> temp = userService.GetIndexLogoInfo(resultUser);
					System.out.println("1结束。。。");
					return temp;
				});
				Future<Object> countrymaptemp = CommonThreadPool.submit(()->{
					System.out.println("2开始。。。");
					Map<String,Object> temp = userService.GetCountryMap();
					System.out.println("2结束。。。");
					return temp;
				});
				Future<Object> xinjiangarmycountrymaptemp = CommonThreadPool.submit(()->{
					System.out.println("3开始。。。");
					Map<String,Object> temp  = userService.GetXinJiangArmyCountryMap();
					System.out.println("3结束。。。");
					return temp;
				});
				try {
					LogoInfo = (Map<String, Integer>) LogoInfotemp.get();
					countrymap = (Map<String, Object>) countrymaptemp.get();
					xinjiangarmycountrymap = (Map<String, Object>) xinjiangarmycountrymaptemp.get();
				}catch(InterruptedException | ExecutionException ex) {
					ex.printStackTrace();
				}
				data.put("data2",LogoInfo);
				data.put("data3",countrymap);
				data.put("data4",xinjiangarmycountrymap);
				jsStr = JSONObject.fromObject(data);
				model.addAttribute("model",jsStr.toString());

				stopWatch.stop();
				System.out.println(stopWatch.getTotalTimeMillis());
				break;
			case 2:
				redirectAttributes.addAttribute("province", resultUser.getProvince());
				return "redirect:/province/province.do";

			case 3:
				redirectAttributes.addAttribute("city", resultUser.getCity());
				redirectAttributes.addAttribute("province", resultUser.getProvince());
				return "redirect:/city/city.do";

			case 4:
				redirectAttributes.addAttribute("county", resultUser.getCounty());
				redirectAttributes.addAttribute("city", resultUser.getCity());
				redirectAttributes.addAttribute("province", resultUser.getProvince());
				return "redirect:/county/county.do";

			case 5:
				redirectAttributes.addAttribute("village", resultUser.getVillage());
				redirectAttributes.addAttribute("county", resultUser.getCounty());
				redirectAttributes.addAttribute("city", resultUser.getCity());
				redirectAttributes.addAttribute("province", resultUser.getProvince());
				return "redirect:/village/village.do";
			case 6:
				url.append("page_hamlet");
				break;
		}
		return url.toString();
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session){
//		session.invalidate();
		session.removeAttribute("currentUser");
		return "redirect:/login.jsp";
	}

	@RequestMapping("/main")
	public String main(HttpServletRequest request,Model model){
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		return "main";
	}
}
