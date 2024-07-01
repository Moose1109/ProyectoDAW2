package com.Ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ventas.model.Empleados;
import com.Ventas.repository.IEmpleadosRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	private IEmpleadosRepository repoEmp;
	
	
	@Override
	public List<Empleados> listarEmpleado() {

		return repoEmp.findAll();
	}

	@Override
	public Empleados agregarEmpleados(Empleados e) {

		return repoEmp.save(e);
	}

	@Override
	public Empleados buscarEmpleados(String id) {

		return repoEmp.findById(id).get();
	}

	@Override
	public Empleados editarEmpleados(Empleados e) {

		return repoEmp.save(e)	;
	}

	@Override
	public void eliminarEmpleados(String id) {
		repoEmp.deleteById(id);
	}




}
