package com.Ventas.service;

import java.util.List;

import com.Ventas.model.Venta;
import com.Ventas.otros.ItemVenta;

public interface VentaService {
	
	void calcularTotal();
	Venta generarVenta(String idcliente);
	void DetalleVenta(int idVenta);
	void ActualizarStock();
	void generarSerieVenta();
	void agregarCarritoCompra(ItemVenta itemVenta);
	void limpiarVenta();
	String getNumeroSerie();
	List<ItemVenta> getListado();
	double getTotalPagar();
	int getNumeroItem();
	void eliminarItem(int index);
	
}
