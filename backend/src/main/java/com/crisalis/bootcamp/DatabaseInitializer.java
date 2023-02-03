package com.crisalis.bootcamp;

import com.crisalis.bootcamp.Services.UserService;
import com.crisalis.bootcamp.model.dto.UserDto;
import com.crisalis.bootcamp.model.entities.Rol;
import com.crisalis.bootcamp.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static Rol ADMIN,USER;
    @Autowired
    private UserService userService;
    @Autowired
    private RolRepository rolRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null ) {

            initilizeUserWithRol();
        }
    }


    public void initilizeUserWithRol(){
        Rol ADMIN = Rol.builder().nombre("ADMIN").build();
        Rol USER = Rol.builder().nombre("USER").build();

        rolRepository.saveAll(List.of(ADMIN,USER));

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
    }

}
