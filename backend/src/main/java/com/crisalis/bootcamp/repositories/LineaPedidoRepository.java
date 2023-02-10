package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaPedidoRepository extends JpaRepository<LineaPedido,Long> {

    List<LineaPedido> findByPedidoIdAndTipoPrestacion(Long pedidoId,
                                                      String tipoPrestacion);

    List<LineaPedido> findByPrestacionId(Long idPrestacion);
}
