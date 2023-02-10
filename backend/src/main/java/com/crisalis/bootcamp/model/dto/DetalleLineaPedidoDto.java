package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.DetalleLineaPedido;
import lombok.Data;

@Data
public class DetalleLineaPedidoDto {

    private String nombreImpuesto;
    private Float valorImpuesto;
    private Float valorAdicional;


    public DetalleLineaPedidoDto(DetalleLineaPedido detalleLineaPedido){
        this.nombreImpuesto = detalleLineaPedido.getNombreImpuesto();
        this.valorImpuesto = detalleLineaPedido.getValorImpuesto();
        this.valorAdicional = detalleLineaPedido.getValorAdicional();
    }
}
