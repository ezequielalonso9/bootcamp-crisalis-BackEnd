package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.ClienteNotFoundException;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.entities.Cliente;
import com.crisalis.bootcamp.model.entities.DetallePedido;
import com.crisalis.bootcamp.model.entities.Pedido;
import com.crisalis.bootcamp.repositories.ClienteRepository;
import com.crisalis.bootcamp.repositories.DetallePedidoRepository;
import com.crisalis.bootcamp.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    DetallePedidoService detallePedidoService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;


    public Pedido createAndSave(PedidoDto pedidoDto) {

        Set<DetallePedido> detallePedidos = detallePedidoService
                .createDetallePedido(pedidoDto.getDetalles());

        Pedido pedido = createPedido(pedidoDto);

        Float importeTotal = 0F;
        for (DetallePedido detalle : detallePedidos){
            importeTotal += detalle.getCostoLinea();
        }
        pedido.setCostoTotalPedido(importeTotal);

        pedido = pedidoRepository.save(pedido);

        Pedido finalPedido = pedido;
        detallePedidos.forEach(detalle -> detalle.setPedido(finalPedido));
        detallePedidoRepository.saveAll(detallePedidos);

        return finalPedido;
    }

    public void deleteAll() {
        pedidoRepository.deleteAll();
    }

    public Cliente getClienteById(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(
                        () -> new ClienteNotFoundException("Cliente with id: " + id + " not found.")
                );
    }

    public Pedido createPedido(PedidoDto pedidoDto){

        Cliente cliente = getClienteById(pedidoDto.getIdCliente());

        Pedido pedido = Pedido
                .builder()
                .build();

        pedido.setCliente(cliente);
        pedido.setEstado(true);
        pedido.setFechaPedido(new Date());

        return pedido;
    }
}
