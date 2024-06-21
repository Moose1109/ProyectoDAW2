package com.Ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.Ventas.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, String>{

	List<Cliente> findByEstado(int estado);

}
