package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Empresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpresaDto {

    private String razonSocial;
    private Long cuit;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fechaInicioActividad;

    public EmpresaDto(Empresa empresa){
        this.razonSocial = empresa.getRazonSocial();
        this.cuit = empresa.getCuit();
        this.fechaInicioActividad = empresa.getFechaInicioActividad();
    }
}
