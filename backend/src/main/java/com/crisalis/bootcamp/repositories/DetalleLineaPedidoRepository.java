package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.DetalleLineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleLineaPedidoRepository extends JpaRepository<DetalleLineaPedido, Long> {

    List<DetalleLineaPedido> findByIdPedidoAndIdImpuesto(Long idPedido,
                                                         Long idImpuesto);

    List<DetalleLineaPedido> findByIdPedidoAndIdImpuestoNotAndIdImpuestoNot(Long idPedido,
                                                                            Long idImpuestoIva,
                                                                            Long idImpuestoIbb);
}