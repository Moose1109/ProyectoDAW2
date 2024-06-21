package com.Ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Ventas.model.Cliente;
import com.Ventas.model.Empleados;
import com.Ventas.model.Producto;
import com.Ventas.repository.IClienteRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClientesController {

	@Autowired
	private IClienteRepository repoCli;
	
	@GetMapping("/cargarCliente")
	public String cargarCliente(Model model, HttpSession session,@RequestParam(value = "estado", defaultValue = "1") int estado) {
		
		List<Cliente> estadoCli = repoCli.findByEstado(estado);
		if(estado == 0) {
			
			Empleados usuario = (Empleados) session.getAttribute("usuario");

		     if (usuario != null) {
		         model.addAttribute("usuario", usuario);
		     }
		     
			model.addAttribute("lstCliente",estadoCli);
			model.addAttribute("clientes",new Producto());
			
			return "recuperarCliente";
		}	
		Empleados usuario = (Empleados) session.getAttribute("usuario");
	     if (usuario != null) {
	         model.addAttribute("usuario", usuario);
	     }
	     
		model.addAttribute("lstCliente",estadoCli);
		model.addAttribute("clientes",new Producto());
		
		return "ClienteListar";
		
	}
	
	@GetMapping("/regisCliente")
	public String registrarCliente(Model model) {

		model.addAttribute("clientes",new Cliente());

	    return "ClienteModulo";
	}

	
	@GetMapping("/editarCliente/{idcliente}")
	public String editar(@PathVariable String idcliente, Model model ) {

		Cliente cli = repoCli.findById(idcliente).get();

		
		model.addAttribute("clientes",cli);
		
		model.addAttribute("lstCliente",repoCli.findAll());
		
		return "ClienteModulo";

	}

	@GetMapping("/eliminarCliente/{idcliente}")
	public String delete(Model model,@ModelAttribute Cliente cliente ) {
			
		Cliente cli = repoCli.findById(cliente.getIdcliente()).get();
		
		if(cli !=null) {
			
			if(cli.getEstado() ==1 ) {
				cli.setEstado(0);
				
			}else if (cli.getEstado() ==0) {
				cli.setEstado(1);
			}
			
			repoCli.save(cli);
		}
			
	return "redirect:/cargarCliente";
	}

	

	@PostMapping("/grabarCli")
	public String grabar(@ModelAttribute Cliente  cliente , Model model) {
		
		try {
			repoCli.save(cliente);
			
			model.addAttribute("mensaje", "Grabacion OK: ");
			model.addAttribute("cssmensaje", "alert alert-success");
			
		}catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("mensaje", "Error al Grabar");
			model.addAttribute("cssmensaje", "alert alert-danger");
			
		}
		
	return "redirect:/cargarCliente";
	}


	}