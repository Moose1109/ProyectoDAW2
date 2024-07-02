package com.Ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ventas.model.Producto;
import com.Ventas.repository.IProductosRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
    private IProductosRepository repoProd;
	
	@Override
    public List<Producto> listarProductos() {
        return repoProd.findAll();
    }

	@Override
	public Producto registrarProducto(Producto p) {
		return repoProd.save(p);
	}

	@Override
	public Producto buscarProducto(String id) {
		return repoProd.findById(id).orElse(null);
	}

	@Override
	public Producto actualizarProducto(Producto p) {
		return repoProd.save(p);
	}

	@Override
	public boolean eliminarProducto(String id) {
		repoProd.deleteById(id);
        return false;
	}
	

}
