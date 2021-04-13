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
    private Map<String, Object> response = new HashMap<>();

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
    public ResponseEntity<?> getById(@PathVariable(value="id") long id){
        Cliente cliente = null;
        try{
            cliente = clienteService.findById(id);
        }
        catch (DataAccessException e){
            response = SetResponseError(e, "Error al realizar la consulta a la base de datos");
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
        try {
            cliente.setCreateDate(new Date());
            clienteNew = clienteService.save(cliente);
        }catch (DataAccessException e){
            response = SetResponseError(e, "Error al realizar la insercción a la base de datos");
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
            response = SetResponseError(e, "Error al realizar la insercción a la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido actualizado con exito");
        response.put("cliente", clienteUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value="id") long id){
        try{
            clienteService.deleteById(id);
        }catch (DataAccessException e){
            response = SetResponseError(e, "Error al eliminar el cliente en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido eliminado con exito");
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Map<String, Object> SetResponseError(DataAccessException ex, String message){
        Map<String, Object> responseError = new HashMap<>();
        response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
        response.put("mensaje", message);
        return responseError;
    }
}
