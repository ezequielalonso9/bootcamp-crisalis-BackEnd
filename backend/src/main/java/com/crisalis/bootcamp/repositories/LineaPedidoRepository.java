package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaPedidoRepository extends JpaRepository<LineaPedido,Long> {
}
