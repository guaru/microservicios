package com.indra.springboot.app.item.model;

public class Item {
  
	private Producto producto;
	private Integer cantidad;
	
	
	public Item(Producto producto, Integer cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	
	
	public Item() {

	}



	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}
	
	
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getTotal() {
		return producto.getPrecio() * cantidad;
	}
	
}
