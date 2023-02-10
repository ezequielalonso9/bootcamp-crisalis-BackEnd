package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.ServicioClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicioClienteController {

    @Autowired
    ServicioClienteService service;

    @PostMapping(value = "/servicioCliente/{id}")
    public ResponseEntity<Object> alta(@PathVariable(value = "id") Long id)                                       {

        service.altaById(id);

        return new ResponseEntity<>("alta de servicio", HttpStatus.OK);
    }

    @DeleteMapping(value = "/servicioCliente/{id}")
    public ResponseEntity<Object> baja(@PathVariable(value = "id") Long id)                                       {

        service.bajaById(id);

        return new ResponseEntity<>("baja de servicio", HttpStatus.OK);
    }
}
