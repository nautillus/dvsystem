package com.ipdev.evote.grpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages="com.ipdev.evote.data.model")
@EnableJpaRepositories(basePackages="com.ipdev.evote.data")
@EnableTransactionManagement
@ComponentScan({"com.ipdev.evote.data.service", "com.ipdev.evote.grpc.server"})
public class GrpcServerApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(GrpcServerApplication.class, args);
	}
}
