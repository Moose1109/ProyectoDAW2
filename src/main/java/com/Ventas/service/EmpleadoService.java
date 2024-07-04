package com.Ventas.service;

import java.util.List;

import com.Ventas.model.Empleados;

public interface EmpleadoService {

	public List<Empleados>listarEmpleado();
	
	public Empleados agregarEmpleados (Empleados e);
	
	public Empleados buscarEmpleados (String id);
	
	public Empleados editarEmpleados (Empleados e);
	
	public void eliminarEmpleados(String id);
	
	public Empleados login(String usuario, String pass);
	
}
