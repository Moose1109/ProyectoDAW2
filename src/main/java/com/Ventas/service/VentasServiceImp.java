package com.Ventas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ventas.model.DetalleVentas;
import com.Ventas.model.Producto;
import com.Ventas.model.Venta;
import com.Ventas.otros.ItemVenta;
import com.Ventas.repository.IClienteRepository;
import com.Ventas.repository.IDetalleVentasRepository;
import com.Ventas.repository.IProductosRepository;
import com.Ventas.repository.IVentasRepository;

@Service
public class VentasServiceImp implements VentaService {

	@Autowired
	private IProductosRepository repoPro;
	
	@Autowired
	private IClienteRepository repoCli;
	
	@Autowired
	private IVentasRepository repoVen;
	
	@Autowired
	private IDetalleVentasRepository repoDetaVen;
	
	
	private List<ItemVenta> listado = new ArrayList<>();
	private double totalPagar;
	private int numeroItem;
	private String numeroSerie;
	
	@Override
	public void calcularTotal() {
		totalPagar = listado.stream().mapToDouble(ItemVenta::getPrecioventa).sum();
		
	}

	@Override
	public Venta generarVenta(String idcliente) {
		 Venta v = new Venta();
	        v.setNroserie(numeroSerie);
	        v.setIdcliente(idcliente);
	        v.setIdempleado("EMP 001");
	        v.setMonto(totalPagar);
	        v.setFecha(new Date());

	        Venta ventaGuardada = repoVen.save(v);
	        int idVentaGenerado = ventaGuardada.getIdventas();
	        DetalleVenta(idVentaGenerado);

	        return ventaGuardada;
		
	}

	@Override
	public void DetalleVenta(int idVenta) {
		 for (ItemVenta item : listado) {
	            DetalleVentas dv = new DetalleVentas();
	            dv.setIdventas(idVenta);
	            dv.setCodproducto(item.getCodproducto());
	            dv.setCantidad(item.getCantidad());
	            dv.setPrecioventa(item.getPrecioventa());

	            repoDetaVen.save(dv);
	        }
		
	}
	
	@Override
	public void eliminarItem(int index) {
		 if (index >= 0 && index < listado.size()) {
	            listado.remove(index);
	            calcularTotal();
	        }
		
	}

	@Override
	public void ActualizarStock() {
		 for (ItemVenta item : listado) {
	            Producto producto = repoPro.findById(item.getCodproducto()).orElse(null);
	            if (producto != null) {
	                producto.setStock(producto.getStock() - item.getCantidad());
	                repoPro.save(producto);
	            }
	        }
		
	}

	@Override
	public void generarSerieVenta() {
		Long cantidadVentas = repoVen.count();
        if (cantidadVentas != null) {
            int nuevoNumeroSerie = cantidadVentas.intValue() + 1;
            numeroSerie = String.format("%08d", nuevoNumeroSerie);
        } else {
            numeroSerie = "00000001";
        }
		
	}

	@Override
	public void agregarCarritoCompra(ItemVenta itemVenta) {
		 totalPagar = 0;
	        numeroItem = numeroItem + 1;

	        double subtotal = itemVenta.getPrecio() * itemVenta.getCantidad();

	        Producto producto = repoPro.findById(itemVenta.getCodproducto()).orElse(null);
	        if (producto != null && producto.getStock() > 0) {
	            itemVenta.setItem(numeroItem);
	            itemVenta.setPrecioventa(subtotal);
	            listado.add(itemVenta);

	            calcularTotal();
	        } else {
	            throw new RuntimeException("Producto sin stock.");
	        }
		
	}

	@Override
	public void limpiarVenta() {
		listado.clear();
        totalPagar = 0;
        generarSerieVenta();
		
	}

	@Override
	public String getNumeroSerie() {
		  return numeroSerie;	}

	@Override
	public List<ItemVenta> getListado() {
		  return listado;
	}

	@Override
	public double getTotalPagar() {
		 return totalPagar;
	}

	@Override
	public int getNumeroItem() {
		  return numeroItem;
	}

	

}
