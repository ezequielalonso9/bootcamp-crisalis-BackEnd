package com.crisalis.bootcamp.model.dto.responseDto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class PedidoResponse {

    private Date fechaPedido;
    private Date fehcaUltimaModificacion;
    private Float costoTotalPedido;
    private Boolean estado;
//    private Cliente cliente;
}

