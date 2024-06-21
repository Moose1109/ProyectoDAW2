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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;



@Controller
@Transactional
public class VentasController {

	@Autowired
	private IEmpleadosRepository repoEmp;
	
	@Autowired 
	private IDetalleVentasRepository repoDetaVen;
	
	@Autowired
	private IVentasRepository repoVenta;

	@Autowired
	private IProductosRepository repoPro;

	@Autowired
	private IClienteRepository repoCli;

   
	ArrayList<ItemVenta> listado = new  ArrayList(); 
	ItemVenta iv = new ItemVenta();
		
    int numeroItem = 0; 
    String numeroSerie;	   
    double totalPagar ;
  
    @GetMapping("/cargarVenta")
    public String abrirVenta(Model model, HttpSession session,@RequestParam(value = "estado", defaultValue = "1") int estado) {

    	List<Producto> productoestado = repoPro.findByEstado(estado);
    	List<Cliente> estadoCli = repoCli.findByEstado(estado);
    	
    	 Empleados usuario = (Empleados) session.getAttribute("usuario");

         if (usuario != null) {
             model.addAttribute("usuario", usuario);
         }
         
        generarSerieVenta();
        model.addAttribute("lstProducto",productoestado);
        model.addAttribute("lstCliente", estadoCli);

        model.addAttribute("nroserie", numeroSerie);
        model.addAttribute("listado", listado);
        model.addAttribute("totalPagar",totalPagar);
        model.addAttribute("numeroItem", numeroItem);
        
        


        return "ventas";
    }


    @GetMapping("/cargarListaVenta")
    public String abrirListaVenta(Model model) {
        model.addAttribute("lstProducto", repoPro.findAll());
        model.addAttribute("lstCliente", repoCli.findAll());
        model.addAttribute("lstVenta",repoVenta.findAll());

        return "VentasListar";
    }
    
    @GetMapping("/eliminarItem/{index}")
    public String eliminarItem(@PathVariable int index, Model model) {

        if (index >= 0 && index < listado.size()) {

            listado.remove(index);

            calcularTotal();
        }

        return "redirect:/cargarVenta";
    }
     
    @PostMapping("/generarVenta")
    public String generarVenta(@ModelAttribute("idcliente") String idcliente, Model model) {
		
    	if(totalPagar == 0) {
    		
    	} else {

    		 Venta(idcliente);
             ActualizarStock();
     	    listado.clear();
     	    totalPagar= 0;
     	    generarSerieVenta(); 
     	    
    	}
    	 	return "redirect:/cargarVenta";
    }
       


    @PostMapping("/guardarDetalleProducto")
    public String guardarDetalle(@ModelAttribute("codproducto") Producto pro , 
    							@RequestParam("cantidad") int cantidad,
    						
    							 Model model) {
		
		    	totalPagar = 0;
		        numeroItem = numeroItem + 1;
		        
		        double subtotal = pro.getPrecio() * cantidad;
		
		        if (pro.getStock()> 0) {
		        	
		        	iv = new ItemVenta();
		        	
		        	iv.setItem(numeroItem);
		        	iv.setCodproducto(pro.getCodproducto());
		        	iv.setNomproducto(pro.getNomproducto());
		        	iv.setPrecio(pro.getPrecio());
		        	iv.setCantidad(cantidad);
		        	iv.setPrecioventa(subtotal);
		                  
		           listado.add(iv);
		            
		              calcularTotal();
		              
		        }else {
		        	
		        	return "redirect:/cargarVenta";
		        }

	  	return "redirect:/cargarVenta";
	   
    }
   

    //--------------------METODOS SIN RETORNO -------------------------------------------------------------------
    
   
    void calcularTotal( ) {
    	totalPagar = 0;
    	
        for (int i = 0; i <listado.size(); i++) {
      	  
        	totalPagar = totalPagar + listado.get(i).getPrecioventa();
   	 
		}
        

    }
       
    void Venta(String idcliente) {
    	
    	Venta v = new Venta();
    	
		v.setNroserie(numeroSerie);
	    v.setIdcliente(idcliente);	
	    v.setIdempleado("EMP 001");  
	    v.setMonto(totalPagar);  
	    v.setFecha(new Date());  

	    Venta ventaGuardada =  repoVenta.save(v);
	    
	    int idVentaGenerado = ventaGuardada.getIdventas();
	    
	    DetalleVenta(idVentaGenerado);
    }
    
    void DetalleVenta(int idVenta) {
    	
    	for (int i = 0 ; i < listado.size(); i++) {
    		
    		DetalleVentas dv = new DetalleVentas() ;
	    	
            dv.setIdventas(idVenta);
            dv.setCodproducto(listado.get(i).getCodproducto());
            dv.setCantidad(listado.get(i).getCantidad());
            dv.setPrecioventa(listado.get(i).getPrecioventa());
            
            repoDetaVen.save(dv);
            
      
        }
    	
    }
  
    void ActualizarStock() {
    	
    	try {
            for (ItemVenta item : listado) {
                Producto producto = repoPro.findById(item.getCodproducto()).get();

                if (producto != null) {
                    int nuevoStock = producto.getStock() - item.getCantidad();
                    producto.setStock(nuevoStock);
                    repoPro.save(producto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }    
    	
    }
    
    void generarSerieVenta() {

        Long cantidadVentas = repoVenta.count();

        if (cantidadVentas != null) {

            int nuevoNumeroSerie = cantidadVentas.intValue() + 1;
            numeroSerie = String.format("%08d", nuevoNumeroSerie);
            
        } else {
			numeroSerie = "00000001";
    }


    }

    
    // ----------------------------------- REPORTES ----------------------------------
    
    
   	@Autowired
   	private DataSource dataSource; 
   	
   	@Autowired
   	private ResourceLoader resourceLoader; 
   	  	
   	@GetMapping("/cargarDetalles/{idventa}")
   	public void reportesDetalles(HttpServletResponse response , @PathVariable int idventa) {

   		DetalleVentas detalleVentas = new DetalleVentas();
           detalleVentas.setIdventas(idventa);

           System.out.println(detalleVentas.getIdventas());
   		try {
   			
   			HashMap<String,Object> parametros = new HashMap<>();
   			
   			parametros.put("idventa",  detalleVentas.getIdventas());
   			
   			String ru = resourceLoader.getResource("classpath:reporteDetalle.jasper").getURI().getPath();
   	
   			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
   			
   			response.setHeader("Content-Disposition", "inline;");
   			response.setContentType("application/pdf");
   			
   			
   			OutputStream outStream = response.getOutputStream();
   			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
   			
   		} catch (Exception e) {
   			
   			e.printStackTrace();
   			
   		}
   	}
       
   	@GetMapping("/reporteCuatro")
	public void reporteCuatro(HttpServletResponse response,@ModelAttribute Producto productos) {

		response.setHeader("Content-Disposition", "inline;");
		response.setContentType("application/pdf");
		try {

		String ru = resourceLoader.getResource("classpath:ReporteCuatro.jasper").getURI().getPath();
		JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	@GetMapping("/reporteUno")
	public void reporteUno(HttpServletResponse response,@ModelAttribute Producto productos) {
		response.setHeader("Content-Disposition", "inline;"); //vista en linea
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:ReporteUno.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/reporteTres")
	public void reporteTres(HttpServletResponse response,@ModelAttribute Producto productos) {

		response.setHeader("Content-Disposition", "inline;");
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:ReporteTres.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
