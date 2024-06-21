package com.Ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ventas.model.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria,Integer> {

}
