package sec.secwatchdog.controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.UserService;
import sec.secwatchdog.shiro.BusinessException;
import sec.secwatchdog.shiro.LuoErrorCode;
import sec.secwatchdog.util.AESUtil;

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
//			request.setAttribute("errorMsg", "�û������������");
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
            
                //ʹ��shiro����֤  
            token.setRememberMe(true);  
            currentUser.login(token);//��֤��ɫ��Ȩ��
           
          //��֤�Ƿ��¼�ɹ�
            if(currentUser.isAuthenticated()){*/
                System.out.println("�û�[" + manager.getUsername() + "]��¼��֤ͨ��������ɽ���һЩ��֤ͨ�����ϵͳ������ʼ��������");
                Managers resultUser=userService.login(manager);
                HttpSession session=request.getSession();
    			session.setAttribute("currentUser", resultUser);
    			Cookie cookie = new Cookie("rememberuser",manager.getUsername()+"+"+manager.getPassword());
    			cookie.setMaxAge(10000);
    			JSONObject jsStr = JSONObject.fromObject(resultUser);
    			String res = jsStr.toString();
    			return res;
          /*     // return "redirect:/getUI";//�ض�����HT-UI
            }else{
                token.clear();
//                return InternalResourceViewResolver.FORWARD_URL_PREFIX + "/login.jsp";
                return "";
            }
        }catch(UnknownAccountException uae){
            System.out.println("���û�[" + manager.getUsername() + "]���е�¼��֤...��֤δͨ����δ֪�˻�");
            request.setAttribute("message_login", "δ֪�˻�");
        }catch(IncorrectCredentialsException ice){
            System.out.println("���û�[" + manager.getUsername() + "]���е�¼��֤...��֤δͨ���������ƾ֤");
            request.setAttribute("message_login", "���벻��ȷ");
        }catch(LockedAccountException lae){
            System.out.println("���û�[" + manager.getUsername() + "]���е�¼��֤...��֤δͨ�����˻�������");
            request.setAttribute("message_login", "�˻�������");
        }catch(ExcessiveAttemptsException eae){
            System.out.println("���û�[" + manager.getUsername() + "]���е�¼��֤...��֤δͨ���������������");
            request.setAttribute("message_login", "�û�������������������");
        }catch(AuthenticationException ae){
            //ͨ������Shiro������ʱAuthenticationException�Ϳ��Կ����û���¼ʧ�ܻ��������ʱ���龰
            System.out.println("���û�[" + manager.getUsername() + "]���е�¼��֤...��֤δͨ������ջ�켣����");
            ae.printStackTrace();
            request.setAttribute("message_login", "�û��������벻��ȷ");
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
		StringBuilder url = new StringBuilder("index/");
		JSONObject jsStr = null;
		switch(resultUser.getPrivilegelevel()) {
			case 1:
				url.append("index");
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("data1",resultUser);
				Map<String,Integer> LogoInfo = new HashMap<String,Integer>();
				LogoInfo = userService.GetIndexLogoInfo(resultUser);
				data.put("data2",LogoInfo);
				Map<String,Object> countrymap = new HashMap<String,Object>();
				countrymap = userService.GetCountryMap();
				data.put("data3",countrymap);
				Map<String,Object> xinjiangarmycountrymap = new HashMap<String,Object>();
				xinjiangarmycountrymap = userService.GetXinJiangArmyCountryMap();
				data.put("data4",xinjiangarmycountrymap);
				System.out.println(xinjiangarmycountrymap);
				jsStr = JSONObject.fromObject(data);
				model.addAttribute("model",jsStr.toString());
				break;
			case 2:
                if (resultUser.getProvince().equals("�������"))
                {
                	url.append("page_corps");
                }
                else
                {
                    url.append("page_province");
                }
                break;
            case 3:
                if (resultUser.getProvince().equals("�������"))
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
//                if (resultUser.getProvince().equals("�������"))
//                {
//                	url.append("page_corps");
//                }
//                else
//                {
//                    url.append("page_province");
//                }
//                break;
//            case 3:
//                if (resultUser.getProvince().equals("�������"))
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
