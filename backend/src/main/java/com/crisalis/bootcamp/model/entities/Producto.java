package com.crisalis.bootcamp.model.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private Float costo;
    @Column(name = "cargo_adicional_soporte")
    private Float cargoAdicionalSoporte;

    @OneToOne
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @NotNull
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto")
    @ToString.Exclude
    private Set<DetallePedido> detallePedidos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Producto producto = (Producto) o;
        return id != null && Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
