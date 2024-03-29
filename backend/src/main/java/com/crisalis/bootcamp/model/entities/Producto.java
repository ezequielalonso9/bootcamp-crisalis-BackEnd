package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.model.dto.PrestacionDto;
import com.crisalis.bootcamp.model.dto.ProductoDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
@DiscriminatorValue(value = "Producto")
public class Producto extends Prestacion{

//    @OneToMany(mappedBy = "prestacion")
//    @ToString.Exclude
//    private Set<DetallePedido> detallePedidos;

    public Producto(ProductoDto productoDto){
        super(productoDto.getNombre(),
                productoDto.getCosto(),
                productoDto.getDetalle(),
                productoDto.getEstado());
    }

    public PrestacionDto toDto(){
        return ProductoDto
                .builder()
                .tipoPrestacion(getClass().getSimpleName())
                .id(getId())
                .nombre(getNombre())
                .costo(getCosto())
                .detalle(getDetalle())
                .estado(getEstado())
                .impuestosId(getIdImpuestos())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Producto producto = (Producto) o;
        return getId() != null && Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
