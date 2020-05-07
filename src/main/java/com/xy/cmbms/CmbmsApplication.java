package com.xy.cmbms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@AutoConfigurationPackage
@MapperScan(basePackages = "com.xy.cmbms.mapper")
@ComponentScan(basePackages = "com.xy.cmbms")
public class CmbmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmbmsApplication.class, args);
	}

}
