package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.model.dto.PersonaDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private Integer dni;

    public Persona(PersonaDto personaDto){
        this.apellido = personaDto.getApellido();
        this.nombre = personaDto.getNombre();
        this.dni = personaDto.getDni();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Persona persona = (Persona) o;
        return id != null && Objects.equals(id, persona.id);
    }

    public void update(Persona persona) {
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
