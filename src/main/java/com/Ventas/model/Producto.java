package com.Ventas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_productos")
@Data
public class Producto {
	@Id
	private String codproducto;
	private String nomproducto;
	private int idcategoria;
	private double precio ;
	private int stock;
	private int estado;
	

	public Producto() {
		
	}
	
	public Producto(String codproducto, String nomproducto , int idcategoria , int stock) {
		this.codproducto = codproducto;
		this.nomproducto = nomproducto;
		this.idcategoria = idcategoria;
		this.stock = stock;
	}
	
	@ManyToOne
	@JoinColumn(name = "idcategoria", insertable = false,  updatable = false)
	private Categoria objCategoria;
}

	