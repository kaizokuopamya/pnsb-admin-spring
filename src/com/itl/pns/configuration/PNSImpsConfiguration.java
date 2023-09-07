package com.itl.pns.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.itl.pns.*" })
@ConfigurationProperties("jdbcA")
public class PNSImpsConfiguration {

	@Autowired
	private Environment environment;

	@Autowired
	@Bean
	@Primary
	public LocalSessionFactoryBean impsSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceA());
		sessionFactory.setPackagesToScan( "com.itl.api.user.auth" ,"com.itl.pns.entity","com.itl.pns.corp.entity","com.itl.pns.impsEntity");
		sessionFactory.setHibernateProperties(hibernatePropertiesA());
		return sessionFactory;
	}

	@Autowired
	@Bean
	@Primary
	public DataSource dataSourceA() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("IMPS_DB_URL"));
		dataSource.setUsername(environment.getRequiredProperty("IMPS_USERNAME"));
		dataSource.setPassword(environment.getRequiredProperty("IMPS_PASSWORD"));
	
		return dataSource;
	}

	private Properties hibernatePropertiesA() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect",environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql",environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql",environment.getRequiredProperty("hibernate.format_sql"));
		return properties;
	}

	  
    @Autowired
	@Bean(name = "transactionManager")
	@Primary
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
	

	

}
