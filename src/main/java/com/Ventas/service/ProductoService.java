package com.Ventas.service;

import java.util.List;

import com.Ventas.model.Producto;

public interface ProductoService {

	public List<Producto> listarProductos();
	public Producto registrarProducto(Producto p);
	public Producto buscarProducto(String id);
	public Producto actualizarProducto(Producto p);
	public boolean eliminarProducto(String id);
}
