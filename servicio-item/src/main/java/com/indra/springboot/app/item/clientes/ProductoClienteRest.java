package com.indra.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.indra.springboot.app.item.model.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {
  
	@GetMapping("/listar")
	public List<Producto> listar();
	
	@GetMapping("/ver/{id}")
	public Producto ver(@PathVariable Long id);
	
	
}
