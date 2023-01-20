package com.indra.springboot.app.productos.model.service;

import java.util.List;

import com.indra.springboot.app.productos.model.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();

	public Producto findById(Long id);
}
