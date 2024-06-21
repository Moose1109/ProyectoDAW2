package com.Ventas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_cliente")
@Data
public class Cliente {

	@Id
	private String idcliente ;
	private String nomcliente;
	private String apecliente ;
	private String dni ;
	private String direccion ;
	private String celular ;
	private int estado;
	private String puesto;
	
}
