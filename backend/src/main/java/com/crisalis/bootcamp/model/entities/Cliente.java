package com.crisalis.bootcamp.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @NotNull
    private Persona persona;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    private Boolean estado;

    public Cliente() {
    }

    public Cliente(Long id, Persona persona, Empresa empresa, Boolean estado) {
        this.id = id;
        this.persona = persona;
        this.empresa = empresa;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                "estado=" + estado +
                '}';
    }
}
