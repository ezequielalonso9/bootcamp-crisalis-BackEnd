package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.entities.User;
import com.crisalis.bootcamp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con username: " + username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty() ){
            throw new UsernameNotFoundException(username);
        }
        User user = userOpt.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),new HashSet<>());
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);

        return user;
    }

}
