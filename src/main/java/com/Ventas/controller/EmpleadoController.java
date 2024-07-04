package com.Ventas.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Ventas.model.Empleados;
import com.Ventas.repository.IEmpleadosRepository;
import com.Ventas.service.EmpleadoService;


import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

	@Autowired
	private IEmpleadosRepository repoEmp; 
	
	@Autowired
	private EmpleadoService service; 
	
	@GetMapping
	public ResponseEntity<List<Empleados>> listarEmpleado() {
		return ResponseEntity.ok(service.listarEmpleado());
	}

	@PostMapping
	public ResponseEntity<Empleados> agregarEmpleados(
			@RequestBody Empleados empleados) {
		Empleados nuevo = service.agregarEmpleados(empleados);		
		return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Empleados> buscarEmpleados(
			@PathVariable String id) {
		return ResponseEntity.ok(service.buscarEmpleados(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Empleados> editarEmpleados(
			@PathVariable String id, 
			@RequestBody Empleados empleados) {
		Empleados e = service.buscarEmpleados(id);
		e.setNomempleado(empleados.getNomempleado());
		e.setApeempleado(empleados.getApeempleado());
		e.setDni(empleados.getDni());
		e.setFechanacimiento(empleados.getFechanacimiento());
		e.setEstado(empleados.getEstado());
		e.setUsuario(empleados.getUsuario());
		e.setPass(empleados.getPass());
		service.editarEmpleados(e);

		return new ResponseEntity<>(e, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarFuncion(@PathVariable String id) {
        String mensaje = "Funcion eliminada correctamente";
        HashMap<String, Object> salida = new HashMap<>();
        try {
            service.eliminarEmpleados(id);
            mensaje = "Se elimino correctamente";
        } catch (Exception ex) {
            mensaje = "Error al eliminar la funcion: " + ex.getMessage();
        }
        salida.put("mensaje", mensaje);
        return ResponseEntity.ok(salida) ;
    }
	
	//===============LOGIN================
	
	@PostMapping("/guardar")
	public ResponseEntity<?> agregarEmp (@RequestBody Empleados e) {
		Empleados emp = service.agregarEmpleados(e);
		return ResponseEntity.ok(emp);
	}
	
	/*@PostMapping("/login")
	public ResponseEntity<?> login (@RequestParam String usuario,
			@RequestParam String pass) {
		Empleados emp = service.login(usuario, pass);
		return ResponseEntity.ok(emp);
	}*/
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String usuario, @RequestParam String pass) {
        Empleados emp = service.login(usuario, pass);
        if (emp != null) {
            return ResponseEntity.ok(emp);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o clave incorrecto(as)");
        }
    }
	
	//===============LOGIN-END================
	
	
	/*@GetMapping("/cargaLogin")
	public String abrirPagLogin() {
		return "login";   
	}
	//listar
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
	
	//registrar
	@GetMapping("regisEmpleado")
	public String registrarEmpleado(Model model) {
		
		model.addAttribute("empleados", new Empleados());
		
		return "EmpleadoModulo";
	}
	
	
	//actualizar
	@GetMapping("/editarEmpleado/{idempleado}")
	public String editar(@PathVariable String idempleado, Model model ) {

		Empleados emp = repoEmp.findById(idempleado).get();

		
		model.addAttribute("empleados",emp);

		return "EmpleadoModulo";
	
	}

	//eliminar
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
	}*/
	
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
	