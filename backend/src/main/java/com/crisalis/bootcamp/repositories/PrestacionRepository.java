package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestacionRepository extends JpaRepository<Prestacion, Long> {
}
