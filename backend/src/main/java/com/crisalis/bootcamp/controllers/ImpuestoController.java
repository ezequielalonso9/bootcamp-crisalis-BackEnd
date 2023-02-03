package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.ImpuestoService;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.ImpuestoDto;
import com.crisalis.bootcamp.model.dto.PersonaDto;
import com.crisalis.bootcamp.model.entities.Impuesto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ImpuestoController {

    @Autowired
    ImpuestoService impuestoService;

     @GetMapping("/impuestos")
    public ResponseEntity<List<ImpuestoDto>> findAll(){

        List<ImpuestoDto> impuestos = impuestoService.findAll();

        return new ResponseEntity<>(impuestos, HttpStatus.OK);
    }

    @PostMapping("/impuesto")
    public ResponseEntity<ImpuestoDto> createImpuesto(@RequestBody ImpuestoDto impuesto){

        ImpuestoDto impuestoDto = impuestoService.createImpuesto(impuesto);

        return new ResponseEntity<>(impuestoDto, HttpStatus.OK);
    }

    @PutMapping("/impuesto/{id}")
    public ResponseEntity<ImpuestoDto> updateImpuesto(@PathVariable(value = "id") Long id,
                                                      @RequestBody ImpuestoDto impuesto){

        ImpuestoDto impuestoDto = impuestoService.updateImpuestoById(impuesto, id);

        return new ResponseEntity<>(impuestoDto, HttpStatus.OK);
    }

    @DeleteMapping("/impuesto/{id}")
    public ResponseEntity<Object> deleteImpuesto(@PathVariable(value = "id") Long id){

        boolean isDelete = impuestoService.delteImpuestoById(id);

        if( isDelete ){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
