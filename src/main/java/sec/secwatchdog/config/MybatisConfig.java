package sec.secwatchdog.config;



import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

@Configuration
//加上这个注解，使得支持事务
@EnableTransactionManagement
@ConfigurationProperties(prefix = "mybatis")
public class MybatisConfig implements TransactionManagementConfigurer {
	  private static Log logger = LogFactory.getLog(MybatisConfig.class);
	  
	  private String typeAliasesPackage;
	
	//  配置mapper的扫描，找到所有的mapper.xml映射文件
	  private String mapperLocations;
	  public String getTypeAliasesPackage() {
		return typeAliasesPackage;
	}
	
	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public String getMapperLocations() {
		return mapperLocations;
	}
	
	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	  @Autowired
	  private DataSource dataSource;
	
	  @Override
	  public PlatformTransactionManager annotationDrivenTransactionManager() {
	       return new DataSourceTransactionManager();
	  }
	
	  @Bean(name = "sqlSessionFactory")
	  public SqlSessionFactory sqlSessionFactoryBean() {    
	      try {
              SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
              sessionFactoryBean.setDataSource(dataSource);

              // 读取配置 
              sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

              Resource[] resources = new PathMatchingResourcePatternResolver()
                      .getResources(mapperLocations);
              sessionFactoryBean.setMapperLocations(resources);
              return sessionFactoryBean.getObject();
          } catch (IOException e) {
              logger.warn("mybatis resolver mapper*xml is error");
              return null;
          } catch (Exception e) {
              logger.warn("mybatis sqlSessionFactoryBean create error");
              return null;
          }
	  }
	
	  @Bean
	  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
	      return new SqlSessionTemplate(sqlSessionFactory);
	  }
}
