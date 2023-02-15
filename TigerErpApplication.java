package com.tigerslab.tigererp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TigerErpApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	public static void main(String [] args) {
		SpringApplication.run(TigerErpApplication.class, args);
		System.out.println("Application running ...");
	}
	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(TigerErpApplication.class);
	 }
	
	@Bean
	InitializingBean sendDatabase() {
	    return () -> {
	        System.out.println("deafult sql initialize bean created.");
	      };
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	


}
