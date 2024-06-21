package com.Ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ventas.model.Empleados;


@Repository
public interface IEmpleadosRepository extends JpaRepository<Empleados, String>{
	
	Empleados findByUsuarioAndPass(String usuario,String pass);
}
