package com.Ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class ProyectoDawiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoDawiApplication.class, args);
	}
 
	@GetMapping("/")
	public String redirectToInicio() {
		return "redirect:/MenuPrincipal.html";
	}
}
