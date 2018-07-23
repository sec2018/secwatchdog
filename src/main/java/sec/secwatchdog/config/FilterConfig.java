package sec.secwatchdog.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

 
/**
 * Filter 配置
 * @author ThinkGem
 * @version 2017年11月30日
 */
@Configuration
public class FilterConfig {

	/**
	 * Encoding Filter
	 */
	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new CharacterEncodingFilter());
		registrationBean.addInitParameter("encoding", "UTF-8");
		registrationBean.addInitParameter("forceEncoding", "true");
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*.do");
		registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
		 
	}
	
}
