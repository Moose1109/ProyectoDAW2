package com.Ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Ventas.model.Empleados;
import com.Ventas.repository.IEmpleadosRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmpleadoController {

	@Autowired
	private IEmpleadosRepository repoEmp; 
	
	
	@GetMapping("/cargaLogin")
	public String abrirPagLogin() {
		return "login";   
	}
	
	@GetMapping("cargarEmpleado")
	public String listarEmpleado(Model model , HttpSession session,@RequestParam(value = "estado", defaultValue = "1") int estado) {
		
		List<Empleados> estadoEmp = repoEmp.findByEstado(estado);
		
		if(estado == 0) {
			Empleados usuario = (Empleados) session.getAttribute("usuario");

		     if (usuario != null) {
		         model.addAttribute("usuario", usuario);
		     }
			model.addAttribute("lstEmpleado",estadoEmp);
			model.addAttribute("empleados", new Empleados());

			
			return "recuperarEmpleado";
		}
		
			 Empleados usuario = (Empleados) session.getAttribute("usuario");
	
		     if (usuario != null) {
		         model.addAttribute("usuario", usuario);
		     }
			model.addAttribute("lstEmpleado",estadoEmp);
			model.addAttribute("empleados", new Empleados());

		return "EmpleadoListar";
	}
	
	@GetMapping("regisEmpleado")
	public String registrarEmpleado(Model model) {
		
		model.addAttribute("empleados", new Empleados());
		
		return "EmpleadoModulo";
	}
	
	@GetMapping("/editarEmpleado/{idempleado}")
	public String editar(@PathVariable String idempleado, Model model ) {

		Empleados emp = repoEmp.findById(idempleado).get();

		
		model.addAttribute("empleados",emp);

		return "EmpleadoModulo";
	
	}

	@GetMapping("/eliminarEmpleado/{idempleado}")
	public String delete(Model model,@ModelAttribute Empleados empleado ) {
			
		Empleados emp = repoEmp.findById(empleado.getIdempleado()).get();
		
		if(emp != null) {
			
			if(emp.getEstado() == 1) {
				emp.setEstado(0);
				
			} else if (emp.getEstado() == 0) {
				emp.setEstado(1);
			}
			
			repoEmp.save(emp);
		}
			
	return "redirect:/cargarEmpleado";
	}
	
	@PostMapping("/grabarEmp")
	public String grabar(@ModelAttribute Empleados empleados , Model model) {
		
		try {
			repoEmp.save(empleados);
			
			model.addAttribute("mensaje", "Grabacion OK: ");
			model.addAttribute("cssmensaje", "alert alert-success");
			
		}catch (Exception e) {
			model.addAttribute("mensaje", "Error al Grabar");
			model.addAttribute("cssmensaje", "alert alert-danger");
			
		}
		
	return "redirect:/cargarEmpleado";
	}
	
	
	@PostMapping("/loginMenuPrincipal")
	public String validarAcceso(
			@RequestParam("txt_Usuario") String usuario ,
			@RequestParam("txt_pass") String contraseña ,
			Model model, HttpSession session) {
		
		Empleados usu = repoEmp.findByUsuarioAndPass(usuario, contraseña);

		if (usu !=null) {

			 session.setAttribute("usuario", usu);  
			model.addAttribute("usuario",usu);
			model.addAttribute("mensaje", "Bienvenido: " + usu.getUsuario());
			model.addAttribute("cssmensaje", "alert alert-success");

			return "MenuPrincipalCliente";
		} else {
			model.addAttribute("mensaje", "Usuario o clave incorrecto");
			model.addAttribute("cssmensaje", "alert alert-danger");}
			return "login";
		}
		

	}
	