package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class ProductoDto extends PrestacionDto{

    public ProductoDto(Producto producto){
        super(producto);
    }
}
