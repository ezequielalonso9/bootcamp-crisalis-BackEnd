package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Prestacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PrestacionDto {
    private Long id;
    private String nombre;
    private Float costo;
    private String detalle;
    private String tipoPrestacion;
    private Boolean estado;
    private Set<Long> impuestosId;

    public PrestacionDto(Prestacion prestacion){
        this.id = prestacion.getId();
        this.nombre = prestacion.getNombre();
        this.costo = prestacion.getCosto();
        this.detalle = prestacion.getDetalle();
        this.tipoPrestacion = prestacion.getClass().getSimpleName();
        this.estado = prestacion.getEstado();
        this.impuestosId=prestacion.getIdImpuestos();
    }


}