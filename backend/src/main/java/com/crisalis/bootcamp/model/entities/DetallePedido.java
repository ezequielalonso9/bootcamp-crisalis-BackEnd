package com.crisalis.bootcamp.model.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float costoLinea;
    private Float cargoAdicionalGarantia;
    private Integer yearsGarantia;
    private Integer cantidadProducto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @ToString.Exclude
    private Producto producto;

    @ManyToMany
    @JoinTable(
            name = "detalle_impuesto",
            joinColumns = @JoinColumn(name = "detalle_pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "impuesto_id")
    )
    @ToString.Exclude
    private Set<Impuesto> impuestos;


    public void calculateAndSetCargoAdicionalGarantia(Float porcentajeGarantia){
        this.cargoAdicionalGarantia = this.yearsGarantia * porcentajeGarantia * this.producto.getCosto();
    }

    public void calculateAndSetCostoLinea() {
        this.costoLinea = this.producto.getCosto() + this.cargoAdicionalGarantia;
        Set<Float> impuestos  = this.impuestos.stream().map(Impuesto::getValorImpuesto).collect(Collectors.toSet());
        Float impuestoTotal = impuestos.stream().reduce(0F, Float::sum);
        this.costoLinea += costoLinea * (1 + impuestoTotal );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DetallePedido that = (DetallePedido) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
