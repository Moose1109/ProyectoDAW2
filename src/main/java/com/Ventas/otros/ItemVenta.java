package com.Ventas.otros;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class ItemVenta {

	
	private int item;
	private String codproducto;
    private String nomproducto; 
    private double precio;
    private int cantidad;
    private double precioventa;
    
    
	public ItemVenta(int item, String codproducto, String nomproducto, double precio, int cantidad,
			double precioventa) {
		super();
		this.item = item;
		this.codproducto = codproducto;
		this.nomproducto = nomproducto;
		this.precio = precio;
		this.cantidad = cantidad;
		this.precioventa = precioventa;
	}
	
	public ItemVenta () {
		
	}

    
    
 
}
