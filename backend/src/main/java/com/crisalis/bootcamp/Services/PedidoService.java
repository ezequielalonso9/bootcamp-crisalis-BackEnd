package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.PedidoException;
import com.crisalis.bootcamp.helper.CalculatorPedido;
import com.crisalis.bootcamp.helper.CuentasPedido;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.entities.*;
import com.crisalis.bootcamp.repositories.DetalleLineaPedidoRepository;
import com.crisalis.bootcamp.repositories.ImpuestoRepository;
import com.crisalis.bootcamp.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteService clienteService;
    @Autowired
    LineaPedidoService lineaPedidoService;
    @Autowired
    CalculatorPedido calculator;
    @Autowired
    private ImpuestoRepository impuestoRepository;
    @Autowired
    private DetalleLineaPedidoRepository detalleLineaPedidoRepository;


    public PedidoDto save(PedidoDto pedidoDto) {

        validatePedidoDto(pedidoDto);
        LineaPedido lineaPedido = lineaPedidoService
                .createLineaPedido(pedidoDto.getLinea());

        Cliente cliente = getClienteByTipoClienteAndIdCliente(
                pedidoDto.getTipoCliente(),
                pedidoDto.getIdCliente()
        );

        CuentasPedido resumenPedido = calculator
                .calculateResumenPedidoByLineaPedido(lineaPedido);

        Pedido pedido = Pedido
                .builder()
                .fechaPedido(new Date())
                .subTotalPedido(lineaPedido.getCostoLinea())
                .totalImpuestoIva(resumenPedido.getImpuestosIva())
                .totalImpuestoIbb(resumenPedido.getImpuestosIbb())
                .totalOtrosImpuestos(resumenPedido.getOtrosImpuestos())
                .descuentoTotal(lineaPedido.getDescuento())
                .estado(null)
                .totalPedido(resumenPedido.getTotalPedido())
                .cliente(cliente)
                .build();
        pedido.addLineaPedido(lineaPedido);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        Long idPedidoGuardado = pedidoGuardado.getId();
        lineaPedido.setPedido(pedidoGuardado);


        LineaPedido newLineaPedido = lineaPedidoService.save(lineaPedido);
        lineaPedido.getDetalleImpuestos().forEach(detalle -> {
                    detalle.setIdPedido(idPedidoGuardado);
                    detalle.setLinea(newLineaPedido);
                });
        detalleLineaPedidoRepository.saveAll(lineaPedido.getDetalleImpuestos());

        PedidoDto newPedidoDto = pedidoGuardado.toDto();
        newPedidoDto.setLinea(newLineaPedido.toDto());
        return newPedidoDto;
    }

    public PedidoDto saveLineByIdPedido(Long id, PedidoDto pedidoDto) {

        Pedido pedido = findPedidoById(id);
        Long idPedidoGuardado = pedido.getId();

        LineaPedido lineaPedido = lineaPedidoService
                .createLineaPedido(pedidoDto.getLinea());
        lineaPedido.setPedido(pedido);

        LineaPedido lineaPedidoSaved = lineaPedidoService.save(lineaPedido);
        lineaPedido.getDetalleImpuestos().forEach(detalle -> {
            detalle.setIdPedido(idPedidoGuardado);
            detalle.setLinea(lineaPedidoSaved);
        });
        detalleLineaPedidoRepository.saveAll(lineaPedido.getDetalleImpuestos());


        CuentasPedido resumenPedido = calculator
                .calculateResumenByPedido(pedido);

        pedido.setSubTotalPedido(resumenPedido.getSubTotal());
        pedido.setTotalImpuestoIva(resumenPedido.getImpuestosIva());
        pedido.setTotalImpuestoIbb(resumenPedido.getImpuestosIbb());
        pedido.setTotalOtrosImpuestos(resumenPedido.getOtrosImpuestos());
        pedido.setTotalPedido(resumenPedido.getTotalPedido());
        pedido.addLineaPedido(lineaPedidoSaved);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return pedidoGuardado.toDto();
    }

    public Pedido findPedidoById(Long id){

        if (id == null ){
            throw new PedidoException("Id es requerido");
        }

        return pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new PedidoException("Pedido con Id: " +
                                id + " no se cuentra.")
                );

    }





    public void deleteAll() {
        pedidoRepository.deleteAll();
    }


    public void validatePedidoDto(PedidoDto pedidoDto) {

        if (pedidoDto == null) throw new PedidoException("Pedido is requerido");

        if (pedidoDto.getTipoCliente() == null) {
            throw new PedidoException("Tipo de cliente es requerido");
        }

        validateTipoCliente(pedidoDto.getTipoCliente());

        if (pedidoDto.getIdCliente() == null) {
            throw new PedidoException("Id cliente es requerido");
        }

        if (pedidoDto.getLinea() == null) {
            throw new PedidoException("Linea de pedido es requerida");
        }
    }




    // MOVER A CLIENTE SERVICE
    public void validateTipoCliente(String tipoCliente) {
        String upperTipoCliente = tipoCliente.toUpperCase();
        TipoCliente[] tipoClientes = TipoCliente.values();
        boolean isValidTipoCliente = false;

        long validTipoCliente = Arrays.stream(tipoClientes).filter(
                enumTipoCliente -> Objects.equals(enumTipoCliente.toString(), upperTipoCliente)).count();
        if (validTipoCliente == 1L) {
            isValidTipoCliente = true;
        }

        if (!isValidTipoCliente) {
            throw new PedidoException("Tipo de cliente no valido");
        }
    }


    //MOVER A CLIENTE SERVICE
    public Cliente getClienteByTipoClienteAndIdCliente(String tipoCliente, Long idCliente){

        if (Objects.equals(tipoCliente.toUpperCase(), TipoCliente.EMPRESA.name())) {
            return  clienteService
                    .findClienteByCuitEmpresa(idCliente);
        }

        return clienteService
                .findClienteByDniPersona(Math.toIntExact(idCliente));

    }


}
