package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.LineaPedido;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class LineaPedidoDto {

    private Long id;
    private Long idPrestacion;
    private String nombrePrestacion;
    private String tipoPrestacion;
    private Integer cantidadPrestacion;
    private Float costoUnitarioPrestacion;
    private Float costoUnitarioGarantia;
    private Float costoUnitarioSoporte;

    private Float costoAdicionalGarantia;
    private Float cargoAdicionalSoporte;
    private Integer añosGarantia;
    private Date fecha;
    private Date fechaUltimaModificacion;
    private Float descuento;
    private Float costoTotalLinea;
    private Set<DetalleLineaPedidoDto> impuestos;

    public LineaPedido toDetallePedido(){
        return LineaPedido
                .builder()
                .cantidadPrestacion(this.cantidadPrestacion)
                .yearsGarantia(this.añosGarantia)
                .build();
    }
}
