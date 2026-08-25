package com.sixpack.dorundorun.global.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.sixpack.dorundorun.global.exception.GlobalExceptionHandler;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class,
	HibernateJpaAutoConfiguration.class,
	RedisAutoConfiguration.class,
	RedisReactiveAutoConfiguration.class,
	RedisRepositoriesAutoConfiguration.class,
	SecurityAutoConfiguration.class,
	UserDetailsServiceAutoConfiguration.class,
	ManagementWebSecurityAutoConfiguration.class,
})
@ComponentScan(basePackageClasses = {
	AgentDemoIncidentController.class,
	AgentDemoIncidentService.class,
})
@Import(GlobalExceptionHandler.class)
public class AgentDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentDemoApplication.class, args);
	}
}
