package com.Ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ventas.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,String >{

}
