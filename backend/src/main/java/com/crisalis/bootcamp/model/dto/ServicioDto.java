package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Servicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDto extends PrestacionDto{

    private Float cargoAdicionalSoporte;


    public ServicioDto(Servicio servicio){
        super(servicio);
        this.cargoAdicionalSoporte = servicio.getCargoAdicionalSoporte();
    }
}
