package com.crisalis.bootcamp.model.entities;


import com.crisalis.bootcamp.model.dto.EmpresaDto;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue(value = "Empresa")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private Long cuit;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInicioActividad;

    public Empresa(EmpresaDto empresaDto){
        this.razonSocial = empresaDto.getRazonSocial();
        this.cuit = empresaDto.getCuit();
        this.fechaInicioActividad = empresaDto.getFechaInicioActividad();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Empresa empresa = (Empresa) o;
        return id != null && Objects.equals(id, empresa.id);
    }

    public void update(Empresa empresa) {
        this.razonSocial = empresa.getRazonSocial();
        this.fechaInicioActividad = empresa.getFechaInicioActividad();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
