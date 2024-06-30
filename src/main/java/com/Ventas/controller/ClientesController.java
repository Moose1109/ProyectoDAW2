package com.Ventas.controller;

import com.Ventas.model.Cliente;
import com.Ventas.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientesController {

	@Autowired
	private ClienteService service;

	// Listar todos los clientes
	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes() {
		List<Cliente> clientes = service.listarClientes();
		return ResponseEntity.ok(clientes);
	}

	// Registrar un nuevo cliente
	@PostMapping
	public ResponseEntity<Cliente> registrarCliente(
			@RequestBody Cliente cliente) {
		Cliente nuevoCliente = service.registrarCliente(cliente);
		return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarCliente(
			@PathVariable String id) {
		return ResponseEntity.ok(service.buscarCliente(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> actualizar(
			@PathVariable String id,
			@RequestBody Cliente cliente) {
			Cliente c = service.buscarCliente(id);
			c.setNomcliente(cliente.getNomcliente());
			c.setApecliente(cliente.getApecliente());
			c.setDni(cliente.getDni());
			c.setDireccion(cliente.getDireccion());
			c.setCelular(cliente.getCelular());
			c.setEstado(cliente.getEstado());
			c.setPuesto(cliente.getPuesto());
			service.actualizarCliente(c);

			return new ResponseEntity<>(c, HttpStatus.CREATED);
	}

	// Eliminar un cliente por su ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCliente(@PathVariable String id) {
		service.eliminarCliente(id);
		return ResponseEntity.noContent().build();
	}
}
