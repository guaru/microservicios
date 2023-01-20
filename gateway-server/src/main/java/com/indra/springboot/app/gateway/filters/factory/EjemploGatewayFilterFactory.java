package com.indra.springboot.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory <EjemploGatewayFilterFactory.Configuracion>{

	private final Logger logger =  LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);
	
    
	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}


	@Override
	public GatewayFilter apply(Configuracion config) {
		return  (exchange, chain) -> {
			logger.info("ejecutando pre gateway filter factory" + config.mensaje);
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, config.cookieValor).build());
				});
				
				logger.info("ejecutando post gateway filter factory" +  config.mensaje);
			}));
		};
	}
	
	
	
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "EjemploCookie";
	}


	@Override
	public List<String> shortcutFieldOrder() {
		// TODO Auto-generated method stub
		return Arrays.asList("mensaje","cookieNombre","cookieValor");
	}





	public static class Configuracion {
		
		private String mensaje;
		private String cookieValor;
		private String cookieNombre;
		/**
		 * @return the mensaje
		 */
		public String getMensaje() {
			return mensaje;
		}
		/**
		 * @param mensaje the mensaje to set
		 */
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		/**
		 * @return the cookieValor
		 */
		public String getCookieValor() {
			return cookieValor;
		}
		/**
		 * @param cookieValor the cookieValor to set
		 */
		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}
		/**
		 * @return the cookieNombre
		 */
		public String getCookieNombre() {
			return cookieNombre;
		}
		/**
		 * @param cookieNombre the cookieNombre to set
		 */
		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}
		
	}

	
	
	
     
}
