package com.crisalis.bootcamp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private Float costo;
    @Column(name = "cargo_adicional")
    private Float cargoAdicional;

    @OneToOne
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @NotNull
    private TipoProducto tipoProducto;

    public Producto() {
    }

    public Producto(Long id, String nombre, Float costo, Float cargoAdicional, TipoProducto tipoProducto) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
        this.cargoAdicional = cargoAdicional;
        this.tipoProducto = tipoProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Float getCargoAdicional() {
        return cargoAdicional;
    }

    public void setCargoAdicional(Float cargoAdicional) {
        this.cargoAdicional = cargoAdicional;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", costo=" + costo +
                ", cargoAdicional=" + cargoAdicional +
                '}';
    }
}
