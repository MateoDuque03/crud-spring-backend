package com.ias.apirest.springbootbackend.models.dao;

import com.ias.apirest.springbootbackend.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long> {
}
