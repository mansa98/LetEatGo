
package com.mbc.leteatgo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
// @Configuration
// Indicates that a class declares one or more @Bean methods 
// and may be processed by the Spring container to generate bean definitions 
// and service requests for those beans at runtime,
@Configuration
@Slf4j
public class MyBatisConfig {
	
	// DB 커넥션 설정
	// @Bean에 name 속성을 사용해서 Bean의 이름을 재설정함(default: 메소드 이름)
	@Bean(name="dataSource")
	@Primary
	// Application.properties 관련 설정 정보로 보임!
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource hikariDataSource() {
		
		log.info("♬ MyBatisConfig의 hikariDataSource가 호출되었습니다!");
		return DataSourceBuilder.create()
								.type(HikariDataSource.class)
								.build();
	}
	
	@Bean(name="hikariCP")
	// SqlSessionFactory란?
	// Creates an {@link SqlSession} out of a connection or a DataSource
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		log.info("♬ MyBatisConfig의 sqlSessionFactory가 호출되었습니다!");
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(hikariDataSource());
		
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				   .getResources("classpath:/mapper/**.xml"));
		
		return factoryBean.getObject();
	}
	// SqlSessionTemplate란?
	//Thread safe, Spring managed, SqlSession that works with Spring transaction management 
	//to ensure that the actual SqlSession used is the one associated with the current Spring transaction. 
	//In addition, it manages the session life-cycle, including closing, committing or rolling back the session 
	//as necessary based on the Spring transaction configuration. 
	
	@Bean(name="sqlSession")
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		
		log.info("♬ MyBatisConfig의 sqlSessionTemplate가 호출되었습니다!");
		
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
//	@Bean
//	@Qualifier(value="transactionManager")
//	public PlatformTransactionManager getTransactionManager() {
//		
//		log.info("♬ MyBatisConfig의 getTransactionManager가 호출되었습니다!");
//		
//		return new DataSourceTransactionManager(this.hikariDataSource());
//	}

}
