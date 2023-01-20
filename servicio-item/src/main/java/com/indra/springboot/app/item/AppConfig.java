package com.indra.springboot.app.item;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
    
    @Bean("clienteRest")
    @LoadBalanced
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }

    //TIENE MAYOR PRIORIDAD EL .YML O PROPERTIES
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory>  defaultCustomizer(){
    	return factory -> factory.configureDefault(id -> {
    		 return new Resilience4JConfigBuilder(id)
    				 .circuitBreakerConfig(CircuitBreakerConfig.custom()
    						 .slidingWindowSize(10) // TAMAÑO DE LA VENTANA DE ERROR 10 PETICIONES
    						 .failureRateThreshold(50) // SI EL 50% DE LAS PETICIONES GENERA ERROR SE ABRE EL CORTO CIRCUITO
    						 .waitDurationInOpenState(Duration.ofSeconds(10L)) //EL CORTO CIRCUITO SOLO DURA 10 SEGUNDO ABIERTO
    						 .permittedNumberOfCallsInHalfOpenState(5) // NÚMERO DE PETICIONES PERMITIDAS EN ESTADO SEMI ABIERTO
    						 .slowCallRateThreshold(50) // 50% DE PETICIONES LENTAS PARA ABRIR EL CORTO CIRCUITO
    						 .slowCallDurationThreshold(Duration.ofSeconds(2L)) // LAS PETICIONES MAYORES A 2 SEGUNDOS SE CONCIDERAN LENTAS
    						 .build())
    				 .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build()) // 6 SEGUNDOS MAXIMO PARA TIMEOUT
    				 .build();
    	});
    	
    }
   
}
