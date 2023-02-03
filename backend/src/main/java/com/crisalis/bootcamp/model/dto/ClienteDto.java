package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.exceptions.custom.PersonaException;
import com.crisalis.bootcamp.exceptions.custom.PrestacionException;
import com.crisalis.bootcamp.model.entities.Cliente;
import com.crisalis.bootcamp.model.entities.Empresa;
import com.crisalis.bootcamp.model.entities.Persona;
import com.crisalis.bootcamp.model.entities.Prestacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {
    private PersonaDto persona;
    private EmpresaDto empresa;
    private Boolean estado;

    public ClienteDto(Cliente cliente){
        this.estado = cliente.getEstado();
        Empresa empresa = cliente.getEmpresa();
        if ( empresa != null ) {
            this.empresa = new EmpresaDto(cliente.getEmpresa());
        }
        Persona persona = cliente.getPersona();
        if ( persona != null ) {
            this.persona = new PersonaDto(cliente.getPersona());
        }
    }



}
