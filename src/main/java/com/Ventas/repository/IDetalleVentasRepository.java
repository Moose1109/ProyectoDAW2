package com.Ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ventas.model.DetalleVentas;

@Repository
public interface IDetalleVentasRepository extends JpaRepository<DetalleVentas,Integer> {

DetalleVentas findByIdventas(int idventa);
}
