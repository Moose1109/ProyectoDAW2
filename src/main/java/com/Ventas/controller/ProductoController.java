package com.Ventas.controller;

import java.io.OutputStream;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Ventas.model.Empleados;
import com.Ventas.model.Producto;
import com.Ventas.repository.ICategoriaRepository;
import com.Ventas.repository.IProductosRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;



@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
	
@Autowired 
private ICategoriaRepository repoCat;

@Autowired 
private IProductosRepository repoPro;



@GetMapping("/cargarProducto")
public String cargarProd(Model model,HttpSession session ,@RequestParam(value = "estado", defaultValue = "1") int estado) {
			
	if(estado ==0) {
		
		List<Producto> productoestado = repoPro.findByEstado(estado);

		Empleados usuario = (Empleados) session.getAttribute("usuario");
		
	     if (usuario != null) {
	         model.addAttribute("usuario", usuario);
	     }
	    model.addAttribute("lstProducto", productoestado);	
		model.addAttribute("lstCategorias",repoCat.findAll());
		model.addAttribute("producto",new Producto());
		
		return "recuperarProducto";
	}
			List<Producto> productoestado = repoPro.findByEstado(estado);

			Empleados usuario = (Empleados) session.getAttribute("usuario");
			
		     if (usuario != null) {
		         model.addAttribute("usuario", usuario);
		     }
		    model.addAttribute("lstProducto", productoestado);	
			model.addAttribute("lstCategorias",repoCat.findAll());
			model.addAttribute("producto",new Producto());
			
			return "ProductoListar";
			
}

@GetMapping("/regisProducto")
public String registrarProd(Model model) {

	model.addAttribute("lstProducto",repoPro.findAll());
    model.addAttribute("lstCategorias", repoCat.findAll());
    model.addAttribute("producto",new Producto());

    return "ProductoModulo";
}


@GetMapping("/editarProducto/{codproducto}")
public String editar(@PathVariable String codproducto, Model model,
                     @RequestParam("btnOpcion") String opcion) {
    Producto pro = repoPro.findById(codproducto).get();

    if (pro != null) {
        model.addAttribute("producto", pro);
        model.addAttribute("lstProducto", repoPro.findAll());
        model.addAttribute("lstCategorias", repoCat.findAll());

        if (opcion.equals("almacen")) {
            return "Almacen";
            
        } else if (opcion.equals("editar")) {
            return "ProductoModulo";
        }
    }

    return "redirect:/cargarProducto";
}


@GetMapping("/eliminarProducto/{codproducto}")
public String deleteProducto(@ModelAttribute Producto producto,	Model model) {
		
	
	Producto productoExistente = repoPro.findById(producto.getCodproducto()).get();
	
		if (productoExistente != null) {
			
            if (productoExistente.getEstado() == 1) {
                productoExistente.setEstado(0);
                
            } else if (productoExistente.getEstado() == 0) {
                productoExistente.setEstado(1);
            }

            repoPro.save(productoExistente);
        }

	 return "redirect:/cargarProducto";

	}

@PostMapping("/grabarPro")
public String grabar(@ModelAttribute Producto producto , Model model) {
	
	try {

		repoPro.save(producto);
		
		model.addAttribute("mensaje", "Grabacion OK: ");
		model.addAttribute("cssmensaje", "alert alert-success");
		
	}catch (Exception e) {
		// TODO: handle exception
		model.addAttribute("mensaje", "Error al Grabar");
		model.addAttribute("cssmensaje", "alert alert-danger");
		
	}
	
return "redirect:/cargarProducto";
}

@PostMapping("/actualizarStock")
public String actualizarStock(@ModelAttribute Producto producto,
                              @RequestParam("cantidad") int cantidad,
                              Model model) {
    try {
        Producto productoExistente = repoPro.findById(producto.getCodproducto()).orElse(new Producto());
       
        if (productoExistente != null && cantidad > 0) {
            productoExistente.setStock(productoExistente.getStock() + cantidad);
            repoPro.save(productoExistente);
        }


        model.addAttribute("mensaje", "Actualización de stock exitosa.");
        model.addAttribute("cssmensaje", "alert alert-success");
    } catch (Exception e) {
        model.addAttribute("mensaje", "Error al actualizar el stock.");
        model.addAttribute("cssmensaje", "alert alert-danger");
    }

    return "redirect:/cargarProducto";
}

@GetMapping("/buscarCodigo")
public String filtrarProducto( @RequestParam("filtro") String filtro ,Model model ) {
	
			List<Producto> filtrarProducto = repoPro.findAll(filtro);
			
			
			model.addAttribute("filtrarProducto",filtrarProducto);
			model.addAttribute("filtro",filtro);
			
		return "ProductoListar";
}


@Autowired
private DataSource dataSource;

@Autowired
private ResourceLoader resourceLoader;

@GetMapping("/reporteDos")
public void reporteDos(HttpServletResponse response,@ModelAttribute Producto producto) {

	response.setHeader("Content-Disposition", "inline;"); 
	response.setContentType("application/pdf");
	try {
		String ru = resourceLoader.getResource("classpath:ReporteDos.jasper").getURI().getPath();
		JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}


}



