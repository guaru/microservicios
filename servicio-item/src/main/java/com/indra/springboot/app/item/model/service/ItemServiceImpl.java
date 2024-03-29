package com.indra.springboot.app.item.model.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.indra.springboot.app.item.model.Item;
import com.indra.springboot.app.item.model.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements IItemService {

     @Autowired
     private RestTemplate clienteRest;

    @Override
    public List<Item> findAll() {
        List<Producto> productos  =  Arrays.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
        return productos.stream().map(x -> new Item(x, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String, String> pathVariables =  new HashMap<String, String>();
        pathVariables.put("id", id.toString());
        Producto producto = clienteRest.getForObject("http://servicio-productos/ver/{id}",Producto.class, pathVariables);
        return new Item(producto, cantidad);
    }
    
}
