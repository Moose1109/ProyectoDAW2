package com.Ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Ventas.model.Producto;

@Repository
public interface IProductosRepository extends JpaRepository<Producto, String>{

@Query("Select p from Producto p where p.nomproducto LIKE %?1%"
		+ " OR p.idcategoria LIKE %?1%")
public List<Producto> findAll(String filtro);

 List<Producto> findByEstado(int estado);

}
