package sec.secwatchdog.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by WangZJ on 2018/7/22.
 */
@Configuration
public class ShiroConfig {

    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

 /*   @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }*/
    @Bean(name = "myRealm")
    public AuthorizingRealm getShiroRealm() {
        AuthorizingRealm realm = new ShiroRealm();
        realm.setName("shiro_auth_cache");
     //   realm.setCredentialsMatcher(getHashedCredentialsMatcher());
        return realm;
    }
    @Bean(name = "rememberMeCookie")
	   public SimpleCookie getRememberMeCookie() {
		   SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		   //simpleCookie.setHttpOnly(true);
		   simpleCookie.setMaxAge(2592000);
		   return simpleCookie;
	   }
    @Bean
    public CookieRememberMeManager getRememberManager(){
	   CookieRememberMeManager meManager = new CookieRememberMeManager();
	   meManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
	   meManager.setCookie(getRememberMeCookie());
	   return meManager;
    }
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getShiroRealm());
        securityManager.setRememberMeManager(getRememberManager());
        return securityManager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
    	System.out.println("-----------------" + "ShiroFilterFactoryBean" + "--------------");
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(getSecurityManager());
        factoryBean.setLoginUrl("/login.jsp");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/font/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");

        filterChainDefinitionMap.put("/user/login.do", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
   
        return factoryBean;
    }
    
  
}
