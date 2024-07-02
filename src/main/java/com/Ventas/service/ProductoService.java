package com.Ventas.service;

import java.util.List;

import com.Ventas.model.Producto;

public interface ProductoService {

	List<Producto> listarProductos();
	Producto registrarProducto(Producto p);
	Producto buscarProducto(String id);
	Producto actualizarProducto(Producto p);
    boolean eliminarProducto(String id);
}
