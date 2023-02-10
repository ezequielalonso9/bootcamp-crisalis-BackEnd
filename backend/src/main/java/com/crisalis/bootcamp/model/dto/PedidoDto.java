package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Pedido;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

    private Long id;
    private Long idCliente;
    private String tipoCliente;
    private Boolean estado;
    private Date fechaPedido;
    private Date fechaUltimaModificacion;
    private LineaPedidoDto linea;
    private Set<LineaPedidoDto> lineas;
    private Float subTotalPedido;
    private Float totalImpuestoIva;
    private Float totalImpuestoIbb;
    private Float totalOtrosImpuestos;
    private Float descuentoTotal;
    private Float totalPedido;
    private ClienteDto cliente;


    public Pedido toPedido(){
        return Pedido
                .builder()
                .estado(this.getEstado())
                .fechaPedido(new Date())
                .build();
    };

}
