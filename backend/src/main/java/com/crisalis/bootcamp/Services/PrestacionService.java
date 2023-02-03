package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.PrestacionException;
import com.crisalis.bootcamp.exceptions.custom.PrestacionNotFoundException;
import com.crisalis.bootcamp.model.dto.PrestacionDto;
import com.crisalis.bootcamp.model.dto.ProductoDto;
import com.crisalis.bootcamp.model.dto.ServicioDto;
import com.crisalis.bootcamp.model.entities.Impuesto;
import com.crisalis.bootcamp.model.entities.Prestacion;
import com.crisalis.bootcamp.model.entities.Producto;
import com.crisalis.bootcamp.model.entities.Servicio;
import com.crisalis.bootcamp.repositories.PrestacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PrestacionService {
    @Autowired
    PrestacionRepository prestacionRepository;
    @Autowired
    ImpuestoService impuestoService;


    public List<PrestacionDto> findAll(){
        List<Prestacion> prestaciones = prestacionRepository.findAll();
        List<PrestacionDto> prestacionesDto = new ArrayList<>();
        prestaciones.forEach(prestacion ->
                prestacionesDto.add( prestacion.toDto())
        );
        return prestacionesDto;
    }

    public Object createPrestacion(PrestacionDto prestacionDto, boolean esServicio){
        validatePrestacion(prestacionDto);
        prestacionDto.setEstado(true);
        Set<Impuesto> impuestos =  impuestoService.findSetImpuestoByIds(prestacionDto.getImpuestosId());
        if (esServicio){
            Servicio servicio = new Servicio((ServicioDto) prestacionDto);
            servicio.setImpuestos(impuestos);
            Servicio servicioNuevo = prestacionRepository.save(servicio);
            return new ServicioDto(servicioNuevo);
        }else {
            Producto producto = new Producto((ProductoDto) prestacionDto);
            producto.setImpuestos(impuestos);
            Producto productoNuevo = prestacionRepository.save(producto);
            return new ProductoDto(productoNuevo);
        }

    }

//    public ProductoDto createProducto(ProductoDto productoDto){
//        Set<Impuesto> impuestos = createPrestacion(productoDto);
//        Producto producto = new Producto(productoDto);
//        producto.setImpuestos(impuestos);
//
//        Producto productoNuevo = prestacionRepository.save(producto);
//
//        return new ProductoDto(productoNuevo);
//    }

//    public ServicioDto createServicio(ServicioDto servicioDto){
//        Set<Impuesto> impuestos = createPrestacion(servicioDto);
//        Servicio servicio = new Servicio(servicioDto);
//        servicio.setImpuestos(impuestos);
//        Servicio servicioNuevo = prestacionRepository.save( servicio );
//
//        return new ServicioDto(servicioNuevo);
//    }

    public Prestacion updatePrestacion(Long id, PrestacionDto prestacionDto){
        Prestacion prestacion = findPrestacionById(id);
        validatePrestacion(prestacionDto);
        Set<Long> idImpuestosNuevos = prestacionDto.getImpuestosId();
        prestacion.setCosto(prestacionDto.getCosto());
        Set<Impuesto>impuestosNuevos = impuestoService.findSetImpuestoByIds(idImpuestosNuevos);
        prestacion.setImpuestos(impuestosNuevos);

        return prestacion;
    }

    public PrestacionDto updateProductoById(Long id, PrestacionDto prestacionDto) {
        Prestacion prestacion = updatePrestacion(id, prestacionDto);

        Producto productoNew = new Producto((ProductoDto) prestacionDto);
        productoNew.setId(prestacion.getId());
        productoNew.setImpuestos(prestacion.getImpuestos());
        Producto productoUpdate = prestacionRepository.save(productoNew);
        return productoUpdate.toDto();
    }

    public PrestacionDto updateServicioById(Long id, ServicioDto servicioDto) {
        Prestacion prestacion = updatePrestacion(id, servicioDto);

        Servicio servicioNew = new Servicio(servicioDto);
        servicioNew.setId(prestacion.getId());
        servicioNew.setImpuestos(prestacion.getImpuestos());
        Servicio servicioUpdate = prestacionRepository.save(servicioNew);
        return servicioUpdate.toDto();
    }

/*    public Prestacion updatePrestacionById(Long id, PrestacionDto newProduct){
        if( id == null ) throw new PrestacionException("Product Id is required");

        validatePrestacion(newProduct);
        if ( !prestacionRepository.existsById(id) ){
            throw new PrestacionNotFoundException("Product/Service with id " + id + " not found");
        }
        newProduct.setId(id);
        return prestacionRepository.save(newProduct);
    }*/

    public Boolean deletePrestacionById(Long id){
        if( id == null ){
            throw new PrestacionException("ProductId is required");
        }
        prestacionRepository.deleteById(id);
        return !prestacionRepository.existsById(id);
    }

    public Prestacion findPrestacionById(Long id){
        if( id == null ){
            throw new PrestacionException("PrestacionId is required");
        }

        Prestacion prestacion=  prestacionRepository.findById(id)
                .orElseThrow(
                        () -> new PrestacionNotFoundException("Product whit id: " + id + " not found.")
                );
        return prestacion;
    }

    public void validatePrestacion(PrestacionDto prestacionDto) {

        if (prestacionDto == null) throw new PrestacionException("Product/Service is required");

        if(prestacionDto.getImpuestosId() == null || prestacionDto.getImpuestosId().isEmpty())
            throw new PrestacionException("Impuestos are required");

        if (prestacionDto.getNombre() == null || prestacionDto.getNombre().isEmpty()) {
            throw new PrestacionException("Nombre is required");
        }

        if (prestacionDto.getCosto() == null) throw new PrestacionException("Costo is required");

    }


}
