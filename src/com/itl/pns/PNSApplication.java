package com.itl.pns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
@ComponentScan(basePackages="com.itl.*")
@Configuration
//@EnableJpaRepositories(basePackages = {"com.itl.api.user.*"})
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableTransactionManagement
@EntityScan
@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.itl.*")
@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })*/
//@Configuration
@SpringBootApplication
//@ComponentScan(basePackages = "com.itl.pns.*")
//@EntityScan(basePackages = { "com.itl.pns.entity", "com.itl.pns.corp.entity" })
//@EnableJpaRepositories(basePackages = { "com.itl.pns.repository" })
public class PNSApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PNSApplication.class);

	}

}