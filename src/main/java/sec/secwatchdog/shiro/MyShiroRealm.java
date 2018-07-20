package sec.secwatchdog.shiro;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.UserService;

public class MyShiroRealm extends AuthorizingRealm {
	@Resource
	private UserService userService;
	
	/* 
     * ��Ȩ
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { 
        Set<String> roleNames = new HashSet<String>();  
        Set<String> permissions = new HashSet<String>();  
        roleNames.add("administrator");//��ӽ�ɫ
        permissions.add("newPage.jsp");  //���Ȩ��
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);  
        info.setStringPermissions(permissions);  
        return info;  
        
//        String username = (String) principals.getPrimaryPrincipal();
//        User user = new User();
//        user.setUsername(username);
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        //Ϊ�û���Ȩ,ֻ�轫�û���Ȩ����ӵ�info����
//        info.addStringPermission("delete");
//        List roleList = userService.getRole(user);
//        if(roleList != null){
//            for (Role role : roleList) {
//                authorizationInfo.addRole(role.getName());
//            }
//            return authorizationInfo;
//        }
//        return null;
    }
    
    /* 
	 * ��¼��֤
	 */
    
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(
//			AuthenticationToken authcToken) throws AuthenticationException {
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//		AESUtil aes = new AESUtil();
//		if(token.getUsername().equals(USER_NAME)){
//			try {
//				return new SimpleAuthenticationInfo(USER_NAME, aes.encryptData(PASSWORD), getName());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
//		}else{
//			throw new AuthenticationException();  
//		}
//		return null;
//	}
	
	    @Override
	    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
	        //��ȡ�����û��������������
	        //ʵ�������authcToken�Ǵ�LoginController����currentUser.login(token)��������
	        //����token�����ö���һ���ģ��������ǣ�org.apache.shiro.authc.UsernamePasswordToken@33799a1e
	        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
	        System.out.print("��֤��ǰSubjectʱ��ȡ��token��");
	        System.out.println(ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
	        Managers user = userService.checklogin(token.getUsername());
	//	        �˴�����ȶԣ��ȶԵ��߼�Shiro����������ֻ�践��һ����������ص���ȷ����֤��Ϣ
	//	        ˵���˾��ǵ�һ���������¼�û������ڶ�������s��Ϸ��ĵ�¼���루�����Ǵ����ݿ���ȡ���ģ�������Ϊ����ʾ��Ӳ�����ˣ�
	//	        ����һ���������ĵ�¼ҳ���Ͼ�ֻ������ָ�����û����������ͨ����֤
	        if(null != user){
	            String username = user.getUsername();
	            String password = user.getPassword();
	            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(username, password,this.getName());
	            return authcInfo;
	        }else{
	            return null;
	        }
	     
	    }
	  
	  /**
	     * ��һЩ���ݷŵ�ShiroSession�У��Ա��������ط�ʹ��
	     * ����Controller���棬ʹ��ʱֱ����HttpSession.getAttribute(key)�Ϳ���ȡ��
	     */
	    private void setAuthenticationSession(Object value){
	        Subject currentUser = SecurityUtils.getSubject();
	        if(null != currentUser){
	            Session session = currentUser.getSession();
	            System.out.println("��ǰSession��ʱʱ��Ϊ[" + session.getTimeout() + "]����");
	            session.setTimeout(1000 * 60 * 60 * 2);
	            System.out.println("�޸�Session��ʱʱ��Ϊ[" + session.getTimeout() + "]����");
	            session.setAttribute("currentUser", value);
	        }
	    }
	    
	    private SqlSession session;
	    public SqlSession UserDaoImpl(){
			//ʹ�������������mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���  
	        String resource = "mybatis-config.xml";      
	        Reader reader;
			try {
				reader = Resources.getResourceAsReader(resource);
				SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();      
		        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);  
		        session = sqlSessionFactory.openSession(); 
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return session;
		}

}
