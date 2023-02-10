package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.ServiciosCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiciosClienteRepository extends JpaRepository<ServiciosCliente,Long> {

    List<ServiciosCliente> findByPedidoId(Long idPedido);
    List<ServiciosCliente> findByClienteId(Long idCliente);
}
