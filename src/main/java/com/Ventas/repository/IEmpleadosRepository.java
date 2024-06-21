package com.Ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ventas.model.Empleados;

@Repository
public interface IEmpleadosRepository extends JpaRepository<Empleados,String>{

	
	Empleados findByUsuarioAndPass(String usuario, String pass);

	List<Empleados> findByEstado(int estado);
}
