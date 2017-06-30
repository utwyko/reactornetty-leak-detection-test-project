package com.bol.reactornetty.leakdetection.leakdetection;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCircuitBreaker
public class HystrixConfiguration {
}
