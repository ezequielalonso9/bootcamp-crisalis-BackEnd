package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.model.dto.DetalleLineaPedidoDto;
import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoPrestacion;
    private Float costoUnitarioPrestacion;
    private Float costoUnitarioSoporte;
    private Float costoUnitarioGarantia;

    private Integer cantidadPrestacion;
    private Float cargoAdicionalSoporte;
    private Integer yearsGarantia;
    private Float cargoAdicionalGarantia;
    private Date fechaLinea;
    private Date fechaUltimaModificacion;

    private Float descuento;
    private Float costoLinea;

    @OneToMany(
            mappedBy = "linea",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<DetalleLineaPedido> detalleImpuestos = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestacion_id")
    @ToString.Exclude
    private Prestacion prestacion;

    public void update(LineaPedidoDto lineaPedidoDto) {
        this.cantidadPrestacion = lineaPedidoDto.getCantidadPrestacion();
        if ( lineaPedidoDto.getAñosGarantia() != null ){
            this.yearsGarantia = lineaPedidoDto.getAñosGarantia();
        }
    }

    public static class LineaPedidoBuilder{
        public LineaPedidoBuilder prestacion(Prestacion prestacion){
            this.prestacion= prestacion;
            this.costoUnitarioPrestacion = prestacion.getCosto();
            if(prestacion.getClass() == Servicio.class){
                this.costoUnitarioSoporte = ((Servicio) prestacion).getCargoAdicionalSoporte();
            }else {
                this.costoUnitarioSoporte = null;
            }
            return this;
        }
    }

    public LineaPedidoDto toDto(){
        Set<DetalleLineaPedido> impuestosLinea = this.getDetalleImpuestos();
        Set<DetalleLineaPedidoDto> detallesDto = new HashSet<>();

        impuestosLinea.forEach( impuesto ->
                detallesDto.add( new DetalleLineaPedidoDto(impuesto) ) );

        return LineaPedidoDto
                .builder()
                .id(this.id)
                .idPrestacion(this.prestacion.getId())
                .tipoPrestacion(this.getTipoPrestacion())
                .nombrePrestacion(this.prestacion.getNombre())
                .cantidadPrestacion(this.cantidadPrestacion)
                .costoUnitarioPrestacion(this.costoUnitarioPrestacion)
                .costoUnitarioGarantia(this.costoUnitarioGarantia)
                .costoUnitarioSoporte(this.costoUnitarioSoporte)
                .cargoAdicionalSoporte(this.cargoAdicionalSoporte)
                .costoAdicionalGarantia(this.cargoAdicionalGarantia)
                .añosGarantia(this.yearsGarantia)
                .fecha(this.fechaLinea)
                .fechaUltimaModificacion(this.fechaUltimaModificacion)
                .descuento(this.descuento)
                .costoTotalLinea(this.costoLinea)
                .impuestos(detallesDto)
                .build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LineaPedido that = (LineaPedido) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
