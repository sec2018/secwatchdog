package sec.secwatchdog.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import sec.secwatchdog.model.Managers;
import sec.secwatchdog.service.UserService;

/**
 * Created by WangZJ on 2018/7/22.
 */
public class ShiroRealm extends AuthorizingRealm {

/*    public ShiroRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }*/

    @Autowired
    private UserService userService;

   public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection paramPrincipalCollection) {
        /*SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Managers manager = (Managers) paramPrincipalCollection.getPrimaryPrincipal();
        if (manager != null) {
            // 当前用户角色编码集合
            List<Role> roles = userService.findLoginUserRoles(manager);
            List<String> roleIds = new ArrayList<>();
            for (Role role : roles) {
                roleIds.add(String.valueOf(role.getRname()));
            }
            authorizationInfo.addRoles(roleIds);
        }
        return authorizationInfo;*/
    	return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (StringUtils.isEmpty(token.getUsername())) {
            throw new IncorrectCredentialsException("username is null!");
        } else if (StringUtils.isEmpty(token.getPassword())) {
            throw new IncorrectCredentialsException("password is null!");
        }
        Managers manager = new Managers();
		try {
			manager = userService.findUserByName(token.getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (manager == null) { // 用户不存在
            throw new UnknownAccountException("The user does not exist");
        }
        return new SimpleAuthenticationInfo(manager, manager.getPassword(), this.getName());

    }
}
