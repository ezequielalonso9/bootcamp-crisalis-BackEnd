package com.crisalis.bootcamp.model.dto;

import com.crisalis.bootcamp.model.entities.Rol;
import com.crisalis.bootcamp.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Set<Rol> rol;

    public void addRol(Rol rol){
        if( this.rol == null ){
            this.rol = new HashSet<>();
        }
        this.rol.add(rol);
    }

    public User toUser(){
        if ( this.rol == null ){
            this.rol = new HashSet<>();
        }
        return User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .roles(this.rol)
                .build();
    }
}
