package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonaDto {
    private String nombre;
    private String apellido;
    private Integer dni;

    public PersonaDto(Persona persona){
        this.apellido = persona.getApellido();
        this.nombre = persona.getNombre();
        this.dni = persona.getDni();
    }
}
