package sec.secwatchdog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//for war
@EnableCaching
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
public class SecWatchDogApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SecWatchDogApplication.class);
    }
    public static void main( String[] args ){
    	SpringApplication.run(SecWatchDogApplication.class, args);
    }
}

//for jar
//@EnableCaching
//@SpringBootApplication
//@EnableTransactionManagement
//public class SecWatchDogApplication{
//   
//    public static void main( String[] args ){
//    	SpringApplication.run(SecWatchDogApplication.class, args);
//    }
//}
