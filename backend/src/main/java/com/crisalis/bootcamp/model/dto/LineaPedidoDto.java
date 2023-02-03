package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.LineaPedido;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LineaPedidoDto {

    private Long id;
    private Long idPrestacion;
    private String nombrePrestacion;
    private String tipoPrestacion;
    private Integer cantidadPrestacion;
    private Float costoUnitarioPrestacion;
    private Float costoSoporte;
    private Integer añosGarantia;
    private Date fecha;
    private Float descuento;
    private Float cosotoTotalLinea;

    public LineaPedido toDetallePedido(){
        return LineaPedido
                .builder()
                .cantidadPrestacion(this.cantidadPrestacion)
                .yearsGarantia(this.añosGarantia)
                .build();
    }
}
