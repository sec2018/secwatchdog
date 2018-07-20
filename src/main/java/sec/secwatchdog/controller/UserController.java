﻿package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@Resource
	private UserService userService;
	
	@RequestMapping(value="/login", produces="text/html;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public String login(Managers manager,HttpServletRequest request){
//		Managers resultUser=userService.login(manager);
//		if(resultUser==null){
//			request.setAttribute("manager", manager);
//			request.setAttribute("errorMsg", "用户名或密码错误！");
//			return "";
//		}else{
//			HttpSession session=request.getSession();
//			session.setAttribute("currentUser", resultUser);
//			Cookie cookie = new Cookie("rememberuser",manager.getUsername()+"+"+manager.getPassword());
//			cookie.setMaxAge(10000);
//			JSONObject jsStr = JSONObject.fromObject(resultUser);
//			String res = jsStr.toString();
//			return res;
//		}
		
/*        AESUtil aes = new AESUtil();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(manager.getUsername(), aes.encryptData(manager.getPassword()));  
            Subject currentUser = SecurityUtils.getSubject();  
            
                //使用shiro来验证  
            token.setRememberMe(true);  
            currentUser.login(token);//验证角色和权限
           
          //验证是否登录成功
            if(currentUser.isAuthenticated()){*/
                System.out.println("用户[" + manager.getUsername() + "]登录认证通过（这里可进行一些认证通过后的系统参数初始化操作）");
                Managers resultUser=userService.login(manager);
                HttpSession session=request.getSession();
    			session.setAttribute("currentUser", resultUser);
    			Cookie cookie = new Cookie("rememberuser",manager.getUsername()+"+"+manager.getPassword());
    			cookie.setMaxAge(10000);
    			JSONObject jsStr = JSONObject.fromObject(resultUser);
    			String res = jsStr.toString();
    			return res;
/*               // return "redirect:/getUI";//重定向至HT-UI
            }else{
                token.clear();
//                return InternalResourceViewResolver.FORWARD_URL_PREFIX + "/login.jsp";
                return "";
            }
        }catch(UnknownAccountException uae){
            System.out.println("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，未知账户");
            request.setAttribute("message_login", "未知账户");
        }catch(IncorrectCredentialsException ice){
            System.out.println("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，错误的凭证");
            request.setAttribute("message_login", "密码不正确");
        }catch(LockedAccountException lae){
            System.out.println("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，账户已锁定");
            request.setAttribute("message_login", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            System.out.println("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，错误次数过多");
            request.setAttribute("message_login", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            System.out.println("对用户[" + manager.getUsername() + "]进行登录验证...验证未通过，堆栈轨迹如下");
            ae.printStackTrace();
            request.setAttribute("message_login", "用户名或密码不正确");
        }catch(Exception ex){
            throw new BusinessException(LuoErrorCode.LOGIN_VERIFY_FAILURE);
        }
        return "";*/
	}
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,ModelMap model){
		
		HttpSession session=request.getSession();
		if(session.getAttribute("currentUser")==null){;
			return "redirect:/login.jsp";
		}
		Managers resultUser= (Managers) session.getAttribute("currentUser");
		System.out.println(resultUser.getAddress());
		System.out.println(resultUser.getPrivilegelevel());
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
		switch(resultUser.getPrivilegelevel()) {
			case 1:
				StopWatch stopWatch = new StopWatch();  
				stopWatch.start();
//				url.append("index");
//				Map<String,Object> data = new HashMap<String,Object>();
//				data.put("data1",resultUser);
//				Map<String,Integer> LogoInfo = new HashMap<String,Integer>();
//				LogoInfo = userService.GetIndexLogoInfo(resultUser);
//				data.put("data2",LogoInfo);
//				Map<String,Object> countrymap = new HashMap<String,Object>();
//				countrymap = userService.GetCountryMap();
//				data.put("data3",countrymap);
//				Map<String,Object> xinjiangarmycountrymap = new HashMap<String,Object>();
//				xinjiangarmycountrymap = userService.GetXinJiangArmyCountryMap();
//				data.put("data4",xinjiangarmycountrymap);
//				jsStr = JSONObject.fromObject(data);
//				model.addAttribute("model",jsStr.toString());
				
				
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
				
				
//				ExecutorService executor = Executors.newFixedThreadPool(3);
//		        //创建一个Callable，
//		        Callable myCallable1 = new Callable() {
//		            @Override
//		            public Object call() throws Exception {
//		                System.out.println("1方法");
//		                Map<String,Integer> temp = userService.GetIndexLogoInfo(resultUser);
//		                System.out.println("1结束。。。");
//		                return temp;
//		            }
//		        };
//		        Callable myCallable2 = new Callable() {
//		            @Override
//		            public Object call() throws Exception {
//		                System.out.println("2方法");
//		                Map<String,Object> temp = userService.GetCountryMap();
//		                System.out.println("2结束。。。");
//		                return temp;
//		            }
//		        };
//		        Callable myCallable3 = new Callable() {
//		            @Override
//		            public Object call() throws Exception {
//		                System.out.println("3方法");
//		                Map<String,Object> temp  = userService.GetXinJiangArmyCountryMap();
//						System.out.println("3结束。。。");
//		                return temp;
//		            }
//		        };
//		        Future LogoInfotemp = executor.submit(myCallable1);
//		        Future countrymaptemp = executor.submit(myCallable2);
//		        Future xinjiangarmycountrymaptemp = executor.submit(myCallable3);
//		        try {
//					LogoInfo = (Map<String, Integer>) LogoInfotemp.get();
//					countrymap = (Map<String, Object>) countrymaptemp.get();
//					xinjiangarmycountrymap = (Map<String, Object>) xinjiangarmycountrymaptemp.get();
//				}catch(InterruptedException | ExecutionException ex) {
//					ex.printStackTrace();
//				}
//		        data.put("data2",LogoInfo);
//				data.put("data3",countrymap);
//				data.put("data4",xinjiangarmycountrymap);
//				jsStr = JSONObject.fromObject(data);
//				model.addAttribute("model",jsStr.toString());
		        
				stopWatch.stop();
				System.out.println(stopWatch.getTotalTimeMillis());
				break;
			case 2:
                if (resultUser.getProvince().equals("建设兵团"))
                {
                	url.append("page_corps");
                }
                else
                {
                	
                    url.append("page_province");
                }
                break;
            case 3:
                if (resultUser.getProvince().equals("建设兵团"))
                {
                    url.append("page_division");
                }
                else
                {
                    url.append("page_city");
                }
                break;
            case 4:
                url.append("page_county");
                break;
            case 5:
                url.append("page_village");
                
                break;
            case 6:
                url.append("page_hamlet");
                break;
		}
		return url.toString();
	} 
	
	
//	@RequestMapping("/index")
//	public ModelAndView index(HttpServletRequest request){
//		HttpSession session=request.getSession();
//		ModelAndView mv = new ModelAndView();
//		if(session.getAttribute("currentUser")==null){
//			mv.setViewName("login");
//			return mv;
//		}
//		Managers resultUser= (Managers) session.getAttribute("currentUser");
//		System.out.println(resultUser.getAddress());
//		StringBuilder url = new StringBuilder("index/");
//		JSONObject jsStr = null;
//		switch(resultUser.getPrivilegelevel()) {
//			case 1:
//				url = new StringBuilder("main");
//				Map<String,Object> data = new HashMap<String,Object>();
//				mv.addObject("data1",JSONObject.fromObject(resultUser).toString());
//				Map<String,Integer> LogoInfo = new HashMap<String,Integer>();
//				LogoInfo = userService.GetIndexLogoInfo(resultUser);
//				mv.addObject("data2",JSONObject.fromObject(LogoInfo).toString());
//				Map<String,Object> countrymap = new HashMap<String,Object>();
//				countrymap = userService.GetCountryMap();
//				mv.addObject("data3",JSONObject.fromObject(countrymap).toString());
//				Map<String,Object> xinjiangarmycountrymap = new HashMap<String,Object>();
//				xinjiangarmycountrymap = userService.GetXinJiangArmyCountryMap();
//				mv.addObject("data4",JSONObject.fromObject(xinjiangarmycountrymap).toString());
//				mv.addObject("rightlogo","/common/rightlogo.jsp");
//				break;
//			case 2:
//                if (resultUser.getProvince().equals("建设兵团"))
//                {
//                	url.append("page_corps");
//                }
//                else
//                {
//                    url.append("page_province");
//                }
//                break;
//            case 3:
//                if (resultUser.getProvince().equals("建设兵团"))
//                {
//                    url.append("page_division");
//                }
//                else
//                {
//                    url.append("page_city");
//                }
//                break;
//            case 4:
//                url.append("page_county");
//                break;
//            case 5:
//                url.append("page_village");
//                
//                break;
//            case 6:
//                url.append("page_hamlet");
//                break;
//		}
//		mv.setViewName(url.toString());
//		return mv;
//	} 
	
	
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
