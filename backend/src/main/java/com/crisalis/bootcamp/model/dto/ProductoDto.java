package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Producto;
import com.crisalis.bootcamp.model.entities.TipoProducto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductoDto {
    private Long id;
    private String nombre;
    private Float costo;
    private Float cargoAdicional;
    private Long idTipoProducto;

    public Producto toProducto(){
        TipoProducto tipoProducto = new TipoProducto(idTipoProducto, null);
        return Producto
                .builder()
                .nombre(this.nombre)
                .costo(this.costo)
                .cargoAdicional(this.cargoAdicional)
                .tipoProducto(tipoProducto)
                .build();
    }
}
