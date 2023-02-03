package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.PrestacionService;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.PrestacionDto;
import com.crisalis.bootcamp.model.dto.ProductoDto;
import com.crisalis.bootcamp.model.dto.ServicioDto;
import com.crisalis.bootcamp.model.entities.Prestacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PrestacionController {

    @Autowired
    PrestacionService prestacionService;

    @GetMapping("/prestaciones")
    public ResponseEntity<List<PrestacionDto>> findAll(){
        List<PrestacionDto> prestaciones = prestacionService.findAll();

        return new ResponseEntity<>(prestaciones, HttpStatus.OK);
    }

    @GetMapping("/prestacion/{id}")
    public ResponseEntity<PrestacionDto> findPrestacionById(@PathVariable(value = "id") Long id){

        PrestacionDto prestacionDto = prestacionService.findPrestacionById(id).toDto();

        return new ResponseEntity<>(prestacionDto, HttpStatus.OK);
    }

    @PostMapping("/prestacion/producto")
    public ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoDto productoDto){
        ProductoDto productoNuevo = (ProductoDto) prestacionService
                .createPrestacion(productoDto, false);

        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    @PostMapping("/prestacion/servicio")
    public ResponseEntity<ServicioDto> createServicio(@RequestBody ServicioDto servicioDto){
        ServicioDto servicioNuevo = (ServicioDto) prestacionService
                .createPrestacion(servicioDto, true);

        return new ResponseEntity<>(servicioNuevo, HttpStatus.CREATED);
    }

    @PutMapping("/prestacion/producto/{id}")
    public ResponseEntity<PrestacionDto> updateProducoto(@PathVariable(value = "id") Long id,
                                                    @RequestBody ProductoDto prestacionDto ){

        PrestacionDto prestacionUpdate = prestacionService.updateProductoById(id,
                prestacionDto);

        return new ResponseEntity<>(prestacionUpdate, HttpStatus.OK);
    }

    @PutMapping("/prestacion/servicio/{id}")
    public ResponseEntity<PrestacionDto> updateServicio(@PathVariable(value = "id") Long id,
                                                          @RequestBody ServicioDto servicioDto ){

        PrestacionDto prestacionUpdate = prestacionService.updateServicioById(id,
                servicioDto);

        return new ResponseEntity<>(prestacionUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/prestacion/{id}")
    public ResponseEntity<Object> deletePrestacion(@PathVariable(value = "id") Long id ){

        boolean isDelete = prestacionService.deletePrestacionById(id);

        return new ResponseEntity<>(isDelete,HttpStatus.NO_CONTENT);
    }
}
