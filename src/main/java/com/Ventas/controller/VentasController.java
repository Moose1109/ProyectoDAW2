package com.Ventas.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Ventas.model.Cliente;
import com.Ventas.model.DetalleVentas;
import com.Ventas.model.Empleados;
import com.Ventas.model.Producto;
import com.Ventas.model.Venta;

import com.Ventas.otros.ItemVenta;
import com.Ventas.repository.IClienteRepository;
import com.Ventas.repository.IDetalleVentasRepository;
import com.Ventas.repository.IEmpleadosRepository;
import com.Ventas.repository.IProductosRepository;
import com.Ventas.repository.IVentasRepository;
import com.Ventas.service.VentaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;



@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:4200/api/ventas")
public class VentasController {

	@Autowired
	private VentaService service;;
	
  
    
	@DeleteMapping("/eliminarItem/{index}")
	public ResponseEntity<Void> eliminarItem(@PathVariable int index) {
        try {
            service.eliminarItem(index);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/generarVenta")
    public ResponseEntity<?> generarVenta(@RequestBody String idcliente) {
        if (service.getTotalPagar() == 0) {
            return ResponseEntity.badRequest().body("No se puede generar una venta con total cero.");
        }

        Venta venta = service.generarVenta(idcliente);
        service.ActualizarStock();
        service.limpiarVenta();
        service.generarSerieVenta();

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

       

    @PostMapping("/guardarDetalleProducto")
    public ResponseEntity<?> guardarDetalle(@RequestBody ItemVenta itemVenta) {
    	try {
            service.agregarCarritoCompra(itemVenta);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
   
   

    }

    
    // ----------------------------------- REPORTES ----------------------------------
    
    
//   	@Autowired
//   	private DataSource dataSource; 
//   	
//   	@Autowired
//   	private ResourceLoader resourceLoader; 
//   	  	
//   	@GetMapping("/cargarDetalles/{idventa}")
//   	public void reportesDetalles(HttpServletResponse response , @PathVariable int idventa) {
//
//   		DetalleVentas detalleVentas = new DetalleVentas();
//           detalleVentas.setIdventas(idventa);
//
//           System.out.println(detalleVentas.getIdventas());
//   		try {
//   			
//   			HashMap<String,Object> parametros = new HashMap<>();
//   			
//   			parametros.put("idventa",  detalleVentas.getIdventas());
//   			
//   			String ru = resourceLoader.getResource("classpath:reporteDetalle.jasper").getURI().getPath();
//   	
//   			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
//   			
//   			response.setHeader("Content-Disposition", "inline;");
//   			response.setContentType("application/pdf");
//   			
//   			
//   			OutputStream outStream = response.getOutputStream();
//   			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
//   			
//   		} catch (Exception e) {
//   			
//   			e.printStackTrace();
//   			
//   		}
//   	}
//       
//   	@GetMapping("/reporteCuatro")
//	public void reporteCuatro(HttpServletResponse response,@ModelAttribute Producto productos) {
//
//		response.setHeader("Content-Disposition", "inline;");
//		response.setContentType("application/pdf");
//		try {
//
//		String ru = resourceLoader.getResource("classpath:ReporteCuatro.jasper").getURI().getPath();
//		JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
//		OutputStream outStream = response.getOutputStream();
//		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
//		} catch (Exception e) {
//		e.printStackTrace();
//		}
//	}
//	
//	@GetMapping("/reporteUno")
//	public void reporteUno(HttpServletResponse response,@ModelAttribute Producto productos) {
//		response.setHeader("Content-Disposition", "inline;"); //vista en linea
//		response.setContentType("application/pdf");
//		try {
//			String ru = resourceLoader.getResource("classpath:ReporteUno.jasper").getURI().getPath();
//			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
//			OutputStream outStream = response.getOutputStream();
//			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@GetMapping("/reporteTres")
//	public void reporteTres(HttpServletResponse response,@ModelAttribute Producto productos) {
//
//		response.setHeader("Content-Disposition", "inline;");
//		response.setContentType("application/pdf");
//		try {
//			String ru = resourceLoader.getResource("classpath:ReporteTres.jasper").getURI().getPath();
//			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
//			OutputStream outStream = response.getOutputStream();
//			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
	

