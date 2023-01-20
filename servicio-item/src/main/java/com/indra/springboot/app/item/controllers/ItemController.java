package com.indra.springboot.app.item.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.indra.springboot.app.item.model.Item;
import com.indra.springboot.app.item.model.Producto;
import com.indra.springboot.app.item.model.service.IItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
	
    private final Logger log =  LoggerFactory.getLogger(ItemController.class);
    
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
    @Autowired
    @Qualifier("serviceRestTemplate")
    private IItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(){
        return itemService.findAll();
    }

  
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
        return cbFactory.create("items")
        		.run(() -> itemService.findById(id, cantidad), e-> metodoAlternativo(id, cantidad, e));
    }
    
    
    @CircuitBreaker(name="cbitems",fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad){
        return itemService.findById(id, cantidad);
    }
    
    @TimeLimiter(name="tlitems")
    @CircuitBreaker(name="cbitems",fallbackMethod = "metodoAlternativo2")
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad){
        return CompletableFuture.supplyAsync(()->itemService.findById(id, cantidad));
    }
    
    public Item metodoAlternativo( Long id,  Integer cantidad, Throwable e) {
    	log.info(e.getMessage());
    	Item item = new Item();
    	Producto producto = new Producto();
    	producto.setId(id);
    	item.setCantidad(cantidad);
    	producto.setNombre("PRODUCTO ALTERNATIVO");
    	producto.setPrecio(1500.00);
    	item.setProducto(producto);
    	return item;
    }

    public CompletableFuture<Item> metodoAlternativo2( Long id,  Integer cantidad, Throwable e) {
    	log.info(e.getMessage());
    	Item item = new Item();
    	Producto producto = new Producto();
    	producto.setId(id);
    	item.setCantidad(cantidad);
    	producto.setNombre("PRODUCTO ALTERNATIVO");
    	producto.setPrecio(1500.00);
    	item.setProducto(producto);
    	return CompletableFuture.supplyAsync(()->item);
    }
}
