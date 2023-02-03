package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    private Long id;
    private Long idCliente;
    private String tipoCliente;
    private Boolean estado;
    private LineaPedidoDto linea;
    private Float subTotalPedido;
    private Float totalImpuestoIva;
    private Float totalImpuestoIbb;
    private Float totalOtrosImpuestos;
    private Float descuentoTotal;
    private Float totalPedido;


    public Pedido toPedido(){
        return Pedido
                .builder()
                .estado(this.getEstado())
                .fechaPedido(new Date())
                .build();
    };

}
