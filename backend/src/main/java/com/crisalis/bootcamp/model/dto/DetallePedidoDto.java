package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Impuesto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class DetallePedidoDto {

    private Long id;
    private Long idProducto;
    private Integer cantidadProducto;
    private Integer yearsGarantia;
    private Set<Impuesto> impuestos;
}
