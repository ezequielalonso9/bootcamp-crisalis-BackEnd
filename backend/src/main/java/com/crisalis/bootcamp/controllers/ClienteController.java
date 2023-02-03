package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.ClienteService;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDto>> findAll(){

        List<ClienteDto> clientes = clienteService.findAll();

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/persona/{dni}")
    public ResponseEntity<ClienteDto> findPersonaByDni(@PathVariable(value = "dni") Integer dni){

        ClienteDto clienteDto = clienteService.findPersonaByDni(dni);

        return new ResponseEntity<>(clienteDto, HttpStatus.OK);
    }

    @GetMapping("/cliente/empresa/{cuit}")
    public ResponseEntity<ClienteDto> findEmpresaByCuit(@PathVariable(value = "cuit") Long cuit){

        ClienteDto clienteDto = clienteService.findClienteByCuit(cuit);

        return new ResponseEntity<>(clienteDto, HttpStatus.OK);
    }

    @PostMapping("/cliente/persona")
    public ResponseEntity<PersonaDto> createPersona(@RequestBody PersonaDto persona){

        PersonaDto personaDto = clienteService.createPersona(persona);

        return new ResponseEntity<>(personaDto, HttpStatus.CREATED);
    }

    @PostMapping("/cliente/empresa")
    public ResponseEntity<ClienteDto> createEmpresa(@RequestBody ClienteDto clienteDto){

        ClienteDto clienteDto1 = clienteService.createEmpresa(clienteDto);

        return new ResponseEntity<>(clienteDto1, HttpStatus.CREATED);
    }

    @PutMapping("/cliente/persona/{dni}")
    public ResponseEntity<ClienteDto> updatePersona(@PathVariable(value = "dni") Integer dni,
                                                @RequestBody ClienteDto clienteDto ){

        ClienteDto clienteUpdate = clienteService.updateClienteByDni(dni,
                clienteDto);

        return new ResponseEntity<>(clienteUpdate, HttpStatus.OK);
    }

    @PutMapping("/cliente/empresa/{cuit}")
    public ResponseEntity<ClienteDto> updateEmpresa(@PathVariable(value = "cuit") Long cuit,
                                                    @RequestBody ClienteDto clienteDto ){

        ClienteDto clienteUpdate = clienteService.updateClienteByCuit(cuit,
                clienteDto);

        return new ResponseEntity<>(clienteUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/cliente/persona/{dni}")
    public ResponseEntity<Object> deletePersona(@PathVariable(value = "dni") Integer dni ){
        clienteService.deletePersona(dni);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cliente/empresa/{cuit}")
    public ResponseEntity<Object> deleteEmpresa(@PathVariable(value = "cuit") Long cuit ){
        clienteService.deleteEmpresa(cuit);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
