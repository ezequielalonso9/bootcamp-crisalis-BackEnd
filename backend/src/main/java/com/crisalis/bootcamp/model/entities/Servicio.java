package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.model.dto.PrestacionDto;
import com.crisalis.bootcamp.model.dto.ServicioDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue(value = "Servicio")
public class Servicio extends Prestacion{

    private Float cargoAdicionalSoporte;


//    @OneToMany(mappedBy = "prestacion")
//    @ToString.Exclude
//    private Set<DetallePedido> detallePedidos;

//    @Builder
//    public Service(Long id, Float costo, String detalle, String nombre ,Float cargo_adicional_soporte) {
//        super(id, costo, detalle, nombre);
//        this.cargo_adicional_soporte = cargo_adicional_soporte;
//    }
    public Servicio(ServicioDto servicioDto){
        super(servicioDto.getNombre(),
                servicioDto.getCosto(),
                servicioDto.getDetalle(),
                servicioDto.getEstado());
        this.cargoAdicionalSoporte = servicioDto.getCargoAdicionalSoporte();
    }

    public PrestacionDto toDto(){
        return ServicioDto
                .builder()
                .tipoPrestacion(getClass().getSimpleName())
                .id(getId())
                .nombre(getNombre())
                .costo(getCosto())
                .detalle(getDetalle())
                .estado(getEstado())
                .cargoAdicionalSoporte(getCargoAdicionalSoporte())
                .impuestosId(getIdImpuestos())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Servicio servicio = (Servicio) o;
        return getId() != null && Objects.equals(getId(), servicio.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
