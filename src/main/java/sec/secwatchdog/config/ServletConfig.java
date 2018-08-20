package sec.secwatchdog.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
public class ServletConfig {
	 @Bean
	  public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet){
		 System.out.println("-----------"+"ServletConfig"+"------------");
	    ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
	    //registration.setServlet(dispatcherServlet());
	    //registration.setEnabled(true);
	   
	    registration.addUrlMappings("*.do");
	    registration.setName("myServlet");
	    return registration;
	  }

}
