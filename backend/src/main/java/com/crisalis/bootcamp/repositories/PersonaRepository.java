package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByDni(Integer dni);
}
