package com.ias.apirest.springbootbackend.models.services;

import com.ias.apirest.springbootbackend.models.dao.IClienteDao;
import com.ias.apirest.springbootbackend.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService{
    @Autowired
    private IClienteDao clienteDao;
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    public Cliente findById(long id) {
        return clienteDao.findById(id).orElse(null);
//        return clienteDao.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    public void deleteById(long id) {
        clienteDao.deleteById(id);
    }
}
