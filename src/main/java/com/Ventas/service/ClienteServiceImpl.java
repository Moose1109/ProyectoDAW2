package com.Ventas.service;

import com.Ventas.model.Cliente;
import com.Ventas.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private IClienteRepository repoCli;
    @Override
    public List<Cliente> listarClientes() {
        return repoCli.findAll();
    }

    @Override
    public Cliente registrarCliente(Cliente c) {
        return repoCli.save(c);
    }

    @Override
    public Cliente buscarCliente(String id) {
        return repoCli.findById(id).orElse(null);
    }

    @Override
    public Cliente actualizarCliente(Cliente c) {
        return repoCli.save(c);
    }

    @Override
    public void eliminarCliente(String id) {
        repoCli.deleteById(id);
    }
}
