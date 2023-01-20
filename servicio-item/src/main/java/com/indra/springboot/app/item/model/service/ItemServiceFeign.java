package com.indra.springboot.app.item.model.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.indra.springboot.app.item.clientes.ProductoClienteRest;
import com.indra.springboot.app.item.model.Item;
import com.indra.springboot.app.item.model.Producto;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements IItemService {

	@Autowired ProductoClienteRest clienteProducto;
	
	@Override
	public List<Item> findAll() {
		  List<Producto> productos  =  clienteProducto.listar();
	      return productos.stream().map(x -> new Item(x, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
	      Producto producto = clienteProducto.ver(id);
	      return new Item(producto, cantidad);
	}

}
