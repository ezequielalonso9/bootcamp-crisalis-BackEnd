package com.crisalis.bootcamp.model.entities;


import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_dni", referencedColumnName = "dni")
    @NotNull
    private Persona persona;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_cuit", referencedColumnName = "cuit")
    private Empresa empresa;

    private Boolean estado;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    @JsonIgnore
    private Set<Pedido> pedidos;


    public Cliente( ClienteDto clienteDto){
        this.persona = new Persona(clienteDto.getPersona());
        if( clienteDto.getEmpresa() != null){
            this.empresa = new Empresa(clienteDto.getEmpresa());
        }else {
            this.empresa = null;
        }
        this.estado = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return id != null && Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
