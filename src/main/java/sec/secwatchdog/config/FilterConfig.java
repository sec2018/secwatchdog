package sec.secwatchdog.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class FilterConfig{
 
	/**
	 * Encoding Filter
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		System.out.println("-----------"+"FilterConfig"+"------------");

		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	    characterEncodingFilter.setEncoding("UTF-8");
	    characterEncodingFilter.setForceEncoding(true);   
	    registrationBean.setFilter(characterEncodingFilter);
	    registrationBean.setName("myFilter");
	
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("*.do");
	    registrationBean.setUrlPatterns(urlPatterns);
	    return registrationBean;
		 
	}
	
	
}
