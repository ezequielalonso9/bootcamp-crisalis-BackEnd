package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.LineaPedidoException;
import com.crisalis.bootcamp.helper.CalculatorPedido;
import com.crisalis.bootcamp.helper.CuentasLinea;
import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.entities.*;
import com.crisalis.bootcamp.repositories.LineaPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class LineaPedidoService {

    @Autowired
    PrestacionService prestacionService;
    @Autowired
    ServicioClienteService servicioClienteService;

    @Autowired
    CalculatorPedido calculator;
    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;


    public LineaPedido createLineaPedido(LineaPedidoDto lineaPedidoDto, Long idCliente) {

        validateLineaPedido(lineaPedidoDto);

        Prestacion prestacion = prestacionService
                .findPrestacionById(lineaPedidoDto.getIdPrestacion());


        CuentasLinea cuentasLinea = calculator.calculateLinea(prestacion, lineaPedidoDto);

        Set<DetalleLineaPedido> detallesLinea = new HashSet<>();
        Set<Impuesto> impuestos = prestacion.getImpuestos();
        for (Impuesto impuesto : impuestos) {
            Float valorImpuesto = impuesto.getValorImpuesto();
            Float valorAdicional = valorImpuesto * cuentasLinea.getCostoTotalLiena() / 100;
            detallesLinea.add(
                    DetalleLineaPedido
                            .builder()
                            .idImpuesto(impuesto.getId())
                            .nombreImpuesto(impuesto.getNombreImpuesto())
                            .valorImpuesto(valorImpuesto)
                            .valorAdicional(valorAdicional)
                            .build()
            );
        }

        Integer garantia;
        String tipoPrestacion;

        if (prestacion.getClass() == Servicio.class) {
            garantia = null;
            tipoPrestacion = "Servicio";
        } else {
            garantia = lineaPedidoDto.getAñosGarantia();
            tipoPrestacion = "Producto";
        }

        Float descuentoLinea = 0F;
        boolean haveDiscount = servicioClienteService.haveDiscountByIdCliente(idCliente);
        boolean isProducto = Objects.equals(tipoPrestacion, "Producto");
        if (haveDiscount && isProducto){
            descuentoLinea = calculator.calculateDescuentoLinea(
                    prestacion.getCosto(),
                    lineaPedidoDto.getCantidadPrestacion());
        }


        return LineaPedido
                .builder()
                .prestacion(prestacion)
                .tipoPrestacion(tipoPrestacion)
                .costoUnitarioGarantia(cuentasLinea.getCostoUnitarioGarantia())
                .costoUnitarioSoporte(cuentasLinea.getCostoUnitarioSoporte())
                .cargoAdicionalGarantia(cuentasLinea.getTotalAdicionalGarantia())
                .cargoAdicionalSoporte(cuentasLinea.getTotalAdicionalSoporte())
                .cantidadPrestacion(lineaPedidoDto.getCantidadPrestacion())
                .yearsGarantia(garantia)
                .fechaLinea(lineaPedidoDto.getFecha())
                .costoLinea(cuentasLinea.getCostoTotalLiena())
                .detalleImpuestos(detallesLinea)
                .descuento(descuentoLinea)
                .build();
    }

    public LineaPedido save(LineaPedido lineaPedido) {
        return lineaPedidoRepository.save(lineaPedido);
    }

    public void validateLineaPedido(LineaPedidoDto lineaPedidoDto) {

        if (lineaPedidoDto.getIdPrestacion() == null) {
            throw new LineaPedidoException("Id prestacion es requerido");
        }

        if (lineaPedidoDto.getCantidadPrestacion() == null) {
            throw new LineaPedidoException("Cantidad de prestacion es requerida");
        }

        if (lineaPedidoDto.getCantidadPrestacion() <= 0) {
            throw new LineaPedidoException("Cantidad de prestacion tiene que ser mayor a 0");
        }
    }

    public LineaPedido findById(Long id) {

        if (id == null) {
            throw new LineaPedidoException("Id es requerido");
        }

        return lineaPedidoRepository.findById(id)
                .orElseThrow(
                        () -> new LineaPedidoException("Linea con Id: " +
                                id + " no se cuentra.")
                );
    }

    public void deleteById(Long lineaId) {
        LineaPedido lineaPedido = findById(lineaId);
        lineaPedidoRepository.delete(lineaPedido);
    }

    public List<LineaPedido> findServiciosByIdPedido(Long idPedido) {

        return lineaPedidoRepository.findByPedidoIdAndTipoPrestacion(
                idPedido,
                "Servicio");
    }
}
