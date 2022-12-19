package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Cliente;
import com.crisalis.bootcamp.model.entities.Pedido;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@Data
@Builder
public class PedidoDto {

    private Long id;
    private Long idCliente;
    private Set<DetallePedidoDto> detalles;

    public Pedido toPedido(){
        Cliente cliente = Cliente
                .builder()
                .id(this.idCliente)
                .build();

        return Pedido
                .builder()
                .id(this.id)
//                .fechaPedido(this.fechaPedido)
//                .fehcaUltimaModificacion(this.fehcaUltimaModificacion)
//                .costoTotalPedido(this.costoTotalPedido)
//                .estado(this.estado)
                .cliente( cliente )
                .build();
    };

}
