package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.entities.Persona;
import com.crisalis.bootcamp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
