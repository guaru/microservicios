package com.indra.springboot.app.item.model.service;

import java.util.List;

import com.indra.springboot.app.item.model.Item;

public interface IItemService {
   
	public List<Item> findAll();
	
	public Item findById(Long id, Integer cantidad);
	
}
