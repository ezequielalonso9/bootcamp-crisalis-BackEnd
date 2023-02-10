package com.crisalis.bootcamp.model.dto.requestDto;

import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPedido {

    private Long idCliente;
    private String tipoCliente;
    private Boolean estado;
    private LineaPedidoDto linea;

}
