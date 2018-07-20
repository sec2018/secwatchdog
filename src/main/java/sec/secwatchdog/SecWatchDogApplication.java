package sec.secwatchdog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("sec.secwatchdog.*")
@SpringBootApplication
public class SecWatchDogApplication{
    public static void main( String[] args ){
    	SpringApplication.run(SecWatchDogApplication.class, args);
    }
}
