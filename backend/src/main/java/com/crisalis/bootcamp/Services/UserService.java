package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.RolException;
import com.crisalis.bootcamp.model.dto.UserDto;
import com.crisalis.bootcamp.model.entities.User;
import com.crisalis.bootcamp.repositories.RolRepository;
import com.crisalis.bootcamp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty() ){
            throw new UsernameNotFoundException(username);
        }
        User user = userOpt.get();
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(),user.getPassword(), user.getAuthorities());
    }

    @Transactional
    public User createUser(UserDto userDto) {
        validateUser(userDto);
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = userDto.toUser();
        user = userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateUser(Long id,UserDto userDto){
        if( id == null ){
            throw new ValidationException("userId is required");
        }
        validateUser(userDto);
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) throw new UsernameNotFoundException("User whit id " + id + "not found");

        User user = userOpt.get();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRol());

        return userRepository.save(user);
    }

    @Transactional
    public Boolean deleteUserById(Long id){
        if( id == null ){
            throw new ValidationException("userId is required");
        }
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }



    public void validateUser(UserDto user){
        if ( user == null ){
           throw new ValidationException("User is required");
        }
        if ( user.getUsername() == null || user.getUsername().isEmpty() ){
            throw new ValidationException("Username is required");
        }
        if ( user.getPassword() == null || user.getPassword().isEmpty() ){
            throw new ValidationException("Password is required");
        }

        user.getRol().stream()
                .map( rol -> rolRepository.findByNombre( rol.getNombre() )
                        .orElseThrow(
                                () -> new RolException("Rol " + rol.getNombre() + " not found."))
        ).close();
    }
}
