package com.Ventas.controller;

import java.util.List;

import com.Ventas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.Ventas.model.Cliente;


@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientesController {

	@Autowired
	private ClienteService service;

	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes() {
		return ResponseEntity.ok(service.listarClientes());
	}

	@PostMapping
	public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
		Cliente nuevo = service.registrarCliente(cliente);
		return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable String id) {
		Cliente cliente = service.buscarCliente(id);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}

	@PutMapping
	public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente) {
		Cliente actualizado = service.actualizarCliente(cliente);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCliente(@PathVariable String id) {
		service.eliminarCliente(id);
		return ResponseEntity.noContent().build();
	}

	/*
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

	*/
	}