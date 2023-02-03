package com.crisalis.bootcamp.model.entities;

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
    private Float costoSoporte;

    private Integer cantidadPrestacion;
    private Integer yearsGarantia;
    private Float cargoAdicionalGarantia;
    private Date fechaLinea;
    private Float descuento;
    private Float costoLinea;

    @OneToMany(
            mappedBy = "linea",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<DetalleLineaPedido> detalleImpuestos = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestacion_id")
    @ToString.Exclude
    private Prestacion prestacion;

    public static class LineaPedidoBuilder{
        public LineaPedidoBuilder prestacion(Prestacion prestacion){
            this.prestacion= prestacion;
            this.costoUnitarioPrestacion = prestacion.getCosto();
            if(prestacion.getClass() == Servicio.class){
                this.costoSoporte = ((Servicio) prestacion).getCargoAdicionalSoporte();
            }else {
                this.costoSoporte = null;
            }
            return this;
        }
    }

    public LineaPedidoDto toDto(){
        return LineaPedidoDto
                .builder()
                .id(this.id)
                .idPrestacion(this.prestacion.getId())
                .tipoPrestacion(this.getTipoPrestacion())
                .nombrePrestacion(this.prestacion.getNombre())
                .tipoPrestacion(this.prestacion.getClass().getSimpleName())
                .cantidadPrestacion(this.cantidadPrestacion)
                .costoUnitarioPrestacion(this.costoUnitarioPrestacion)
                .costoSoporte(this.costoSoporte)
                .añosGarantia(this.yearsGarantia)
                .fecha(this.fechaLinea)
                .descuento(this.descuento)
                .cosotoTotalLinea(this.costoLinea)
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
