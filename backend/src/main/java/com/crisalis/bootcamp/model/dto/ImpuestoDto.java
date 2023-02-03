package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Impuesto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImpuestoDto {

    private Long id;
    private String nombreImpuesto;
    private Float valorImpuesto;

    public ImpuestoDto(Impuesto impuesto){
        this.id = impuesto.getId();
        this.nombreImpuesto = impuesto.getNombreImpuesto();
        this.valorImpuesto = impuesto.getValorImpuesto();
    }
}
