package com.Ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ventas.model.Venta;


@Repository
public interface IVentasRepository extends JpaRepository<Venta, Integer>{



	

}
