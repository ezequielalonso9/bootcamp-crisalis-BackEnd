package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.model.entities.DetalleLineaPedido;
import com.crisalis.bootcamp.repositories.DetalleLineaPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DetalleLineaService {

    @Autowired
    DetalleLineaPedidoRepository detalleLineaPedidoRepository;

    public void saveAll(Set<DetalleLineaPedido> detalleLineasPedido ){
        detalleLineaPedidoRepository.saveAll(detalleLineasPedido);
    }
}
