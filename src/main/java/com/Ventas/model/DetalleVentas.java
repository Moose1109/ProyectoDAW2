 package com.Ventas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_detalle_venta")
@Data
public class DetalleVentas {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iddetalleventa;
	private int idventas;
	private String codproducto;
	private int cantidad;
	private double precioventa;
	
		
	@ManyToOne
	@JoinColumn(name = "idventas", insertable = false,  updatable = false)
	private Venta objVenta;
	
		
	@ManyToOne
	@JoinColumn(name = "codproducto", insertable = false,  updatable = false)
	private Producto objProducto;
	
}
