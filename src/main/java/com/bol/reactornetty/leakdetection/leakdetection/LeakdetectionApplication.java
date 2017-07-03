package com.bol.reactornetty.leakdetection.leakdetection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeakdetectionApplication {

	static {
		System.setProperty("io.netty.leakDetection.level", "PARANOID");
	}

	public static void main(String[] args) {
		SpringApplication.run(LeakdetectionApplication.class, args);
	}
}
