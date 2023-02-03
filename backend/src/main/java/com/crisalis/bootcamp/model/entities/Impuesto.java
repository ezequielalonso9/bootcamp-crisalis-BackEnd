package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.model.dto.ImpuestoDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
//@Builder
//@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Impuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreImpuesto;
    private Float valorImpuesto;


    public Impuesto(ImpuestoDto impuestoDto){
        this.nombreImpuesto = impuestoDto.getNombreImpuesto();
        this.valorImpuesto = impuestoDto.getValorImpuesto();
    }

    @ManyToMany(mappedBy = "impuestos")
    Set<Prestacion> prestaciones;


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
