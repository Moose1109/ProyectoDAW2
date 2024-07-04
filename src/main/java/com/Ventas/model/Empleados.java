package com.Ventas.model;



import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_empleados")
@Data
public class Empleados {
	
	@Id
	private String idempleado;
	private String nomempleado;
	private String apeempleado;
	private String dni ;
	private int estado;
	
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechanacimiento ;
	
	private String usuario;
	
	@Column(name ="contraseña")
	private String pass ;

}
