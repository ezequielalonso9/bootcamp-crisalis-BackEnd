package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.model.dto.PrestacionDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorColumn(name = "TYPE",discriminatorType = DiscriminatorType.STRING)
public abstract class Prestacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Float costo;
    private String detalle;
    private Boolean estado;

    Prestacion(String nombre, Float costo, String detalle, Boolean estado){
        this.nombre = nombre;
        this.costo = costo;
        this.detalle = detalle;
        this.estado = estado;
    }


    @ManyToMany
    @JoinTable(
            name = "prestacion_impuesto",
            joinColumns = @JoinColumn(name = "prestacion_id"),
            inverseJoinColumns = @JoinColumn(name = "impuesto_id")
    )
    @ToString.Exclude
    private Set<Impuesto> impuestos = new HashSet<>();


    public Set<Long> getIdImpuestos(){
        Set<Long> ids = new HashSet<>();

        this.impuestos.forEach(impuesto -> {
            ids.add(impuesto.getId());
        });

        return ids;
    }


    public abstract PrestacionDto toDto();
}
