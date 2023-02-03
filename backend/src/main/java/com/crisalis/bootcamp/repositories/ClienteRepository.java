package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByPersonaDni(Integer dni);
    Optional<Cliente> findByEmpresaCuit(Long cuit);

}
