package com.ias.apirest.springbootbackend.controller;

import com.ias.apirest.springbootbackend.models.entity.Cliente;
import com.ias.apirest.springbootbackend.models.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://crud-spring-frontend.vercel.app/"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public List<?> index(){
        try{
            return clienteService.findAll();
        }
        catch (Exception e){
            return Collections.EMPTY_LIST;
        }
    }

    @GetMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value="id") long id){
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try{
            cliente = clienteService.findById(id);
        }
        catch (Exception e){
            response.put("Error", e.getMessage().concat(": ").concat(e.getLocalizedMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null){
            response.put("mensaje", "El usuario consultado, no existe en el sistema");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> createUser(@Validated @RequestBody Cliente cliente){
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente.setCreateDate(new Date());
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            response.put("mensaje", "Error al realizar la insercción a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente se ha creado con exito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/clientes")
    public ResponseEntity<?> updateUser(@Validated @RequestBody Cliente cliente){
        Cliente clienteActual = clienteService.findById(cliente.getId());
        Cliente clienteUpdate = null;
        Map<String, Object> response = new HashMap<>();
        if (clienteActual == null){
            response.put("mensaje", "El usuario a actualizar no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteUpdate = clienteService.save(clienteActual);
        }catch (DataAccessException e){
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            response.put("mensaje", "Error al realizar la insercción a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido actualizado con exito");
        response.put("cliente", clienteUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteUser(@PathVariable(value="id") long id){
        Map<String, Object> response = new HashMap<>();
        try{
            clienteService.deleteById(id);
        }catch (DataAccessException e){
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido eliminado con exito");
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
