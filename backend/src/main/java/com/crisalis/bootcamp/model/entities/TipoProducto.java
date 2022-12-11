package com.crisalis.bootcamp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipo_producto")
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String tipoProducto;

    public TipoProducto() {
    }

    public TipoProducto(Long id, String tipoProducto) {
        this.id = id;
        this.tipoProducto = tipoProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return "TipoProducto{" +
                "id=" + id +
                ", tipoProducto='" + tipoProducto + '\'' +
                '}';
    }
}
