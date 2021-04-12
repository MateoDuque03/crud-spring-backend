package com.ias.apirest.springbootbackend.models.services;

import com.ias.apirest.springbootbackend.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    public List<Cliente> findAll();
    public Cliente findById(long id);
    public Cliente save(Cliente cliente);
    public void deleteById(long id);
}
