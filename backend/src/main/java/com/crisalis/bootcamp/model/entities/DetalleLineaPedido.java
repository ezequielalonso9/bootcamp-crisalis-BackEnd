package com.crisalis.bootcamp.model.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleLineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idImpuesto;
    private Long idPedido;

    private String nombreImpuesto;
    private Float valorImpuesto;
    private Float valorAdicional;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linea_id")
    @ToString.Exclude
    private LineaPedido linea;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DetalleLineaPedido that = (DetalleLineaPedido) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
