package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.*;
import com.crisalis.bootcamp.model.dto.ImpuestoDto;
import com.crisalis.bootcamp.model.entities.Impuesto;
import com.crisalis.bootcamp.repositories.ImpuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImpuestoService {

    @Autowired
    ImpuestoRepository impuestoRepository;

     public List<ImpuestoDto> findAll(){
         List<Impuesto> impuestos = impuestoRepository.findAll();
         List<ImpuestoDto> impuestosDto = new ArrayList<>();
         if ( impuestos.size() != 0 ){
             impuestos.forEach( impuesto -> impuestosDto.add( new ImpuestoDto(impuesto)));
         }

         return impuestosDto;
     }

    public Set<Impuesto> createSetImpuesto(Set<Long> impuestosId){

        Set<Impuesto> impuestos = new HashSet<>();

        impuestosId.forEach(id -> {
            impuestos.add( findImpuestoById(id) );
        });
        return impuestos;
    }

    public Impuesto findImpuestoById(Long id){
        return impuestoRepository.findById(id)
                .orElseThrow(
                        () -> new ImpuestoNotFoundException("Impuesto whit id: " + id + " not found.")
                );

    }

    public Set<Impuesto> findSetImpuestoByIds(Set<Long> impuestosId ){
         Set<Impuesto> impuestos = new HashSet<>();

         impuestosId.forEach( id -> {
             Impuesto impuesto = findImpuestoById(id);
             impuestos.add(impuesto);
         });

         return impuestos;
    }

    public ImpuestoDto createImpuesto(ImpuestoDto impuestoDto) {
         Impuesto impuesto = validateImpuesto(impuestoDto);

         Impuesto impuestoNew = impuestoRepository.save(impuesto);
         ImpuestoDto impuestoDtoNew = new ImpuestoDto( impuestoNew );
         impuestoDtoNew.setId(impuestoNew.getId());

        return impuestoDtoNew;
    }


    public Impuesto validateImpuesto(ImpuestoDto impuestoDto) {
        if (impuestoDto == null) throw new EmpresaException("Impuesto is required");

        if (impuestoDto.getNombreImpuesto() == null || impuestoDto.getNombreImpuesto().isEmpty())
            throw new ImpuestoException("Nombre impuesto is required");

        if (impuestoDto.getValorImpuesto() == null)
            throw new ImpuestoException("Valor de impuesto is required");

        return new Impuesto(impuestoDto);

    }

    public ImpuestoDto updateImpuestoById(ImpuestoDto impuestoDto, Long id) {

         Impuesto impuesto = findImpuestoById(id);
         Impuesto impuestoNew = validateImpuesto(impuestoDto);
         impuestoNew.setId(id);
         Impuesto impuestoUpdated = impuestoRepository.save(impuestoNew);

         return new ImpuestoDto(impuestoUpdated);
    }

    public boolean delteImpuestoById(Long id) {

        Optional<Impuesto> impuesto = impuestoRepository.findById(id);

        if (impuesto.isEmpty()) {
            throw new ImpuestoNotFoundException("Impuesto con id " + id + "no existe");
        }

        try{
            impuestoRepository.delete(impuesto.get());

        }catch (DataIntegrityViolationException exception){
            return false;
        }

        return true;
    }



}
