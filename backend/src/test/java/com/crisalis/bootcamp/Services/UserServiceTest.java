package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.RolException;
import com.crisalis.bootcamp.model.dto.UserDto;
import com.crisalis.bootcamp.model.entities.Rol;
import com.crisalis.bootcamp.model.entities.User;
import com.crisalis.bootcamp.repositories.RolRepository;
import com.crisalis.bootcamp.repositories.UserRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    UserRepository userRepository;

    @Order(0)
    @Test
    public void createUser(){
        List<User> userListBefore = userRepository.findAll();

        Rol adminRol = rolRepository
                .findByNombre("ADMIN")
                .orElseThrow( () -> new RolException("Not found rol: ADMIN"));

        Set<Rol> rolSet = new HashSet<>();
        rolSet.add(adminRol);
        UserDto userDto = UserDto
                .builder()
                .username("userTest")
                .password("123456")
                .rol(rolSet)
                .build();

        userService.createUser(userDto);

        Optional<User> userOpt = userRepository.findByUsername("userTest");
        if (userOpt.isEmpty()){
            throw new UsernameNotFoundException("user1");
        }
        List<User> userListAfter = userRepository.findAll();

        assertThat(userOpt.get().getId()).isNotNull();
        assertThat( userListAfter.size() ).isEqualTo(userListBefore.size() + 1 );


    }

    /**
     * Debe arrojar una excepción
     * ValidationException
     */
    @Test
    @Order(1)
    public void createUserWhitOutField(){
        UserDto userDto = UserDto
                .builder()
                .username("userTest")
                .build();

        Exception exception = assertThrows( ValidationException.class,
                () ->  userService.createUser(userDto) );
       assertThat(exception.getMessage()).isEqualTo("Password is required");

    }

    //update user

    /**
     * Actualizamos el usuario creado en el test 0
     *  UserDto userDto = UserDto
     *                 .builder()
     *                 .username("userTest")
     *                 .password("123456")
     *                 .rol(rolSet) con rol ADMIN
     *                 .build();
     *         userService.createUser(userDto);
     */
    @Test
    @Order(2)
    public void updateUser(){
        Optional<User> userOptional = userRepository.findByUsername("userTest");
        Long id = null;
        if ( userOptional.isPresent() ){
            id = userOptional.get().getId();
        }
        Rol adminRol = rolRepository
                .findByNombre("USER")
                .orElseThrow( () -> new RolException("Not found rol: USER"));

        Set<Rol> rolSet = new HashSet<>();
        rolSet.add(adminRol);
        UserDto userToUpdate = UserDto
                .builder()
                .id(id)
                .username("user update")
                .password("abc123")
                .rol(rolSet)
                .build();

        User userUpdate = userService.updateUser(id, userToUpdate);

        assertThat(userUpdate.getUsername()).isEqualTo(userToUpdate.getUsername());
        assertThat(userUpdate.getPassword()).isEqualTo(userToUpdate.getPassword());
        assertThat(userUpdate.getRoles()).isEqualTo(userToUpdate.getRol());
    }

    /**
     * Delete user
     * userUpdate
     */
    @Test

    @Order(3)
    public void deleteUser(){
        Optional<User> userOptional = userRepository.findByUsername("user update");
        Long id = null;
        if ( userOptional.isPresent() ){
            id = userOptional.get().getId();
        }

        Boolean deleteOk = userService.deleteUserById(id);

        assertThat(deleteOk).isEqualTo(true);
        Optional<User> userDelete = userRepository.findById(id);
        assertThat(userDelete.isEmpty()).isEqualTo(true);

    }
}