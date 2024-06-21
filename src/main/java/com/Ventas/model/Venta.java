package com.Ventas.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_venta")
@Data
public class Venta {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idventas;
	private String nroserie;
	private String idcliente;
	private String idempleado;
	private double monto;
	
	@DateTimeFormat(iso = ISO.DATE)
	private Date fecha ;
	
	
	@ManyToOne
	@JoinColumn(name = "idcliente", insertable = false,  updatable = false)
	private Cliente objCliente;
	
	@ManyToOne
	@JoinColumn(name = "idempleado", insertable = false,  updatable = false)
	private Empleados objEmpleados;
}
