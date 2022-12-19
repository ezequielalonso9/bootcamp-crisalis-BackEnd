package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpuestoRepository extends JpaRepository<Impuesto,Long> {
}
