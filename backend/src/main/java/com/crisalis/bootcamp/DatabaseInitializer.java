package com.crisalis.bootcamp;

import com.crisalis.bootcamp.Services.UserService;
import com.crisalis.bootcamp.model.dto.UserDto;
import com.crisalis.bootcamp.model.entities.Rol;
import com.crisalis.bootcamp.model.entities.TipoProducto;
import com.crisalis.bootcamp.model.entities.User;
import com.crisalis.bootcamp.repositories.RolRepository;
import com.crisalis.bootcamp.repositories.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private TipoProductoRepository tipoProductoRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null ) {

            Rol ADMIN = Rol.builder().nombre("ADMIN").build();
            Rol USER = Rol.builder().nombre("USER").build();
            Rol VENTAS = Rol.builder().nombre("VENTAS").build();

            rolRepository.saveAll(List.of(ADMIN, USER, VENTAS));

            UserDto userAdmin = UserDto.builder()
                    .username("user1")
                    .password("admin")
                    .build();
            userAdmin.addRol(ADMIN);

            UserDto user = UserDto.builder()
                    .username("user2")
                    .password("user123")
                    .build();
            user.addRol(USER);

            userService.createUser(userAdmin);
            userService.createUser(user);


            TipoProducto tipoProducto = new TipoProducto(null, "producto");
            TipoProducto tipoServicio = new TipoProducto(null, "servicio");
            TipoProducto tipoServicioEspecial = new TipoProducto(null, "servicio especial");

            tipoProductoRepository.saveAll(List.of(tipoProducto,tipoServicio,tipoServicioEspecial));

        }
    }
}
