package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.PedidoException;
import com.crisalis.bootcamp.exceptions.custom.ServicioClienteException;
import com.crisalis.bootcamp.helper.CalculatorPedido;
import com.crisalis.bootcamp.helper.CuentasPedido;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.dto.requestDto.RequestPedido;
import com.crisalis.bootcamp.model.entities.*;
import com.crisalis.bootcamp.repositories.DetalleLineaPedidoRepository;
import com.crisalis.bootcamp.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ServicioClienteService servicioClienteService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    LineaPedidoService lineaPedidoService;
    @Autowired
    CalculatorPedido calculator;
    @Autowired
    private DetalleLineaPedidoRepository detalleLineaPedidoRepository;


    public PedidoDto save(RequestPedido pedidoRequest) {

        validatePedidoDto(pedidoRequest);

        Cliente cliente = clienteService.getClienteByTipoClienteAndIdCliente(
                pedidoRequest.getTipoCliente(),
                pedidoRequest.getIdCliente()
        );

        LineaPedido lineaPedido = lineaPedidoService
                .createLineaPedido(pedidoRequest.getLinea(), cliente.getId());

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

        PedidoDto pedidoDto = pedidoGuardado.toDto();
        pedidoDto.setLinea(newLineaPedido.toDto());

        return pedidoDto;
    }

    public PedidoDto saveLineByIdPedido(Long id, RequestPedido pedidoRequest) {

        Pedido pedido = findPedidoById(id);

        Boolean estadoPedido = pedido.getEstado();
        if (estadoPedido != null && estadoPedido) {
            throw new PedidoException("no se puede agregar linea." +
                    "El pedido ya esta confirmado");
        }

        if (estadoPedido != null && !estadoPedido) {
            throw new PedidoException("no se puede agregar linea." +
                    "El pedido ya esta dado de baja");
        }

        Long idPedidoGuardado = pedido.getId();

        LineaPedido lineaPedido = lineaPedidoService
                .createLineaPedido(pedidoRequest.getLinea(), pedido.getCliente().getId());
        lineaPedido.setPedido(pedido);


        LineaPedido lineaPedidoSaved = lineaPedidoService.save(lineaPedido);
        lineaPedido.getDetalleImpuestos().forEach(detalle -> {
            detalle.setIdPedido(idPedidoGuardado);
            detalle.setLinea(lineaPedidoSaved);
        });
        detalleLineaPedidoRepository.saveAll(lineaPedido.getDetalleImpuestos());


        CuentasPedido resumenPedido = calculator
                .calculateResumenByPedido(pedido);

        pedido.setMontosPedido(resumenPedido);
        pedido.addLineaPedido(lineaPedidoSaved);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        PedidoDto pedidoDto = pedidoGuardado.toDto();
        pedidoDto.setLinea(lineaPedidoSaved.toDto());


        return pedidoDto;
    }

    public Pedido findPedidoById(Long id) {

        if (id == null) {
            throw new PedidoException("Id es requerido");
        }

        return pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new PedidoException("Pedido con Id: " +
                                id + " no se cuentra.")
                );

    }


    public void validatePedidoDto(RequestPedido pedidoRequest) {

        if (pedidoRequest == null) throw new PedidoException("Pedido is requerido");

        if (pedidoRequest.getTipoCliente() == null) {
            throw new PedidoException("Tipo de cliente es requerido");
        }

        clienteService.validateTipoCliente(pedidoRequest.getTipoCliente());

        if (pedidoRequest.getIdCliente() == null) {
            throw new PedidoException("Id cliente es requerido");
        }

        if (pedidoRequest.getLinea() == null) {
            throw new PedidoException("Linea de pedido es requerida");
        }
    }


    public boolean deleteById(Long id) {
        Pedido pedido = findPedidoById(id);
        Boolean confimado = pedido.getEstado();

        if (confimado == null) {
            pedidoRepository.delete(pedido);
            return true;
        }

        if (!confimado) {
            return false;
        }

        boolean areThereServiceActivo = servicioClienteService
                .areThereServiceActivceByIdPedido(pedido.getId());
        if (areThereServiceActivo) {
            throw new ServicioClienteException("El pedido tiene servicios activos, no se puede eleminar");
        }

        pedido.setEstado(false);
        pedidoRepository.save(pedido);

        return false;
    }

    public boolean deleteLineaByIdPedidoAndIdLinea(Long pedidoId, Long lineaId) {
        Pedido pedido = findPedidoById(pedidoId);
        Boolean confirmado = pedido.getEstado();

        if (confirmado == null || !confirmado) {
            lineaPedidoService.deleteById(lineaId);

            Pedido pedidoActualizado = findPedidoById(pedidoId);
            CuentasPedido resumenPedido = calculator
                    .calculateResumenByPedido(pedido);

            pedidoActualizado.setMontosPedido(resumenPedido);
            pedidoRepository.save(pedidoActualizado);
            return true;
        }

        return false;
    }

    public PedidoDto confirmationPedidoByIdPedido(Long id) {
        Pedido pedido = findPedidoById(id);
        Boolean estadoPedido = pedido.getEstado();
//        if( estadoPedido != null && !estadoPedido){
//            throw new PedidoException("Este pedido ya se dio de baja");
//        }
        if(!pedido.getCliente().getEstado()){
            throw new PedidoException("No se puede confirmar, el cliente esta dado de baja.");
        }

        pedido.setEstado(true);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        List<LineaPedido> lineas = lineaPedidoService.findServiciosByIdPedido(id);
        Set<ServiciosCliente> serviciosClientes = new HashSet<>();
        if (lineas.size() > 0) {
            lineas.forEach(linea -> serviciosClientes.add(new ServiciosCliente(linea)));
            servicioClienteService.saveAll(serviciosClientes);
        }

        return pedidoGuardado.toDto();
    }

    public PedidoDto updateLineaPedido(Long pedidoId,
                                       Long lineaId,
                                       LineaPedidoDto lineaPedidoDto) {

        Pedido pedido = findPedidoById(pedidoId);
        LineaPedido linea = lineaPedidoService.findById(lineaId);

        if (!Objects.equals(linea.getPedido().getId(), pedidoId)) {
            throw new PedidoException("PedidoId no corresponse con lineaId");
        }


        Boolean estadoPedido = pedido.getEstado();

        if (estadoPedido != null && estadoPedido) {
            throw new PedidoException("No se puede acutualizar linea." +
                    "El pedido ya esta confirmado");
        }

        if (estadoPedido != null && !estadoPedido) {
            throw new PedidoException("No se puede actualizar linea." +
                    "El pedido ya esta dado de baja");
        }

        validateLineaPedidoDto(lineaPedidoDto);

        linea.update(lineaPedidoDto);
        LineaPedido lineaActualizada = lineaPedidoService.createLineaPedido(
                linea.toDto(),
                pedido.getCliente().getId());
        lineaActualizada.setId(lineaId);
        lineaActualizada.setPedido(pedido);
        lineaActualizada.setFechaUltimaModificacion(new Date());
        lineaPedidoService.save(lineaActualizada);

        Pedido pedidoActualizado = findPedidoById(pedidoId);
        CuentasPedido resumenPedidoActualizado = calculator
                .calculateResumenByPedido(pedido);

        pedidoActualizado.setMontosPedido(resumenPedidoActualizado);
        pedidoActualizado.setFehcaUltimaModificacion(new Date());

        Pedido pedidoActualizadoGuardado = pedidoRepository.save(pedidoActualizado);
        PedidoDto pedidoResponse = pedidoActualizadoGuardado.toDto();
        pedidoResponse.setLinea(lineaActualizada.toDto());

        return pedidoResponse;
    }

    public void validateLineaPedidoDto(LineaPedidoDto lineaPedidoDto) {
        if (lineaPedidoDto == null) {
            throw new PedidoException("Linea pedido es requerida");
        }
        Integer cantidadPrestacion = lineaPedidoDto.getCantidadPrestacion();
        if (cantidadPrestacion == null || cantidadPrestacion <= 0) {
            throw new PedidoException("invalida cantidad de prestacion");
        }

    }


    public List<PedidoDto> getAll() {

        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDto> pedidosDto = new ArrayList<>();
        pedidos.forEach(pedido -> pedidosDto.add(pedido.toDto()));

        return pedidosDto;

    }
}
