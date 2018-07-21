package sec.secwatchdog.config;

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
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new CharacterEncodingFilter());
		bean.addInitParameter("encoding", "UTF-8");
		bean.addInitParameter("forceEncoding", "true");
		bean.addUrlPatterns("/*.do"); 
        return bean;
		 
	}
	
}
