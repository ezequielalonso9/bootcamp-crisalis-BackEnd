package com.crisalis.bootcamp.model.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Impuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreImpuesto;
    private Float valorImpuesto;

    @ManyToMany(mappedBy = "impuestos")
    @ToString.Exclude
    private Set<DetallePedido> detalles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Impuesto impuesto = (Impuesto) o;
        return id != null && Objects.equals(id, impuesto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
