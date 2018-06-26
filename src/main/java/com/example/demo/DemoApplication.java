package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@EnableDiscoveryClient
@Configuration
@EnableAutoConfiguration
@RefreshScope
@SpringBootApplication
public class DemoApplication {
	@Autowired
	org.springframework.cloud.client.discovery.DiscoveryClient discoveryClient;
	
	//@Value("${test}")
	//private String testString;
	

	@Value("${interservice-bus.data-categorizer.username}")	
    private String user;
    
    @Value("${interservice-bus.data-categorizer.password}")
    private String password;

	
	@PostConstruct
	public void getBus() {
    		if (discoveryClient == null) {
			System.out.println("******************************ERROR: Discovery Client is null******************************");
			return;
		}
    		System.out.println("****************************** Discovery Client is ACTIVE ******************************");

	    org.springframework.cloud.client.ServiceInstance serviceInstance =
	    		discoveryClient.getInstances("interservice-bus")
	    		        .stream()
	    		        .findFirst()
	    		        .orElseThrow(() -> new RuntimeException("ERROR: " + "interservice-bus" + " not found"));
	    System.out.println("****************************** ISB is " + serviceInstance.getHost() + ":" + serviceInstance.getPort() + " ******************************");
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
