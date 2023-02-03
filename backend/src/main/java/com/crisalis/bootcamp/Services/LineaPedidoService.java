package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.LineaPedidoException;
import com.crisalis.bootcamp.helper.CalculatorPedido;
import com.crisalis.bootcamp.helper.CuentasPedido;
import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.entities.DetalleLineaPedido;
import com.crisalis.bootcamp.model.entities.Impuesto;
import com.crisalis.bootcamp.model.entities.LineaPedido;
import com.crisalis.bootcamp.model.entities.Prestacion;
import com.crisalis.bootcamp.repositories.LineaPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import java.util.HashSet;
import java.util.Set;

@Service
public class LineaPedidoService {

    @Autowired
    PrestacionService prestacionService;

    @Autowired
    CalculatorPedido calculator;
    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;


    public LineaPedido createLineaPedido(LineaPedidoDto lineaPedidoDto){

        validateLineaPedido(lineaPedidoDto);

        Prestacion prestacion = prestacionService
                .findPrestacionById(lineaPedidoDto.getIdPrestacion());

        Float adicionalGarantia = calculator.calculateAdicionalGarantia(lineaPedidoDto, prestacion);
        Float adicionalSoporte = calculator.calculateAdicionalSoporte(prestacion);
        float adicional = adicionalSoporte + adicionalGarantia;
        Float totalLinea = calculator.calculateTotalLinea(
                lineaPedidoDto.getCantidadPrestacion(),
                prestacion.getCosto(),
                adicional
                );

        Set<DetalleLineaPedido> detallesLinea = new HashSet<>();
        Set<Impuesto> impuestos = prestacion.getImpuestos();
        for (Impuesto impuesto : impuestos) {
            Float valorImpuesto = impuesto.getValorImpuesto();
            Float valorAdicional = valorImpuesto * totalLinea / 100;
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



        return LineaPedido
                .builder()
                .prestacion(prestacion)
                .tipoPrestacion(prestacion.getClass().getSimpleName())
                .cargoAdicionalGarantia(adicionalGarantia)
                .cantidadPrestacion(lineaPedidoDto.getCantidadPrestacion())
                .yearsGarantia(lineaPedidoDto.getAñosGarantia())
                .fechaLinea(lineaPedidoDto.getFecha())
                .costoLinea(totalLinea)
                .detalleImpuestos(detallesLinea)
                .descuento(0f)//calcular descuento
                .build();
    }

    public LineaPedido save(LineaPedido lineaPedido){
        return lineaPedidoRepository.save(lineaPedido);
    }

    public void validateLineaPedido(LineaPedidoDto lineaPedidoDto){

        if( lineaPedidoDto.getIdPrestacion() == null ){
            throw new LineaPedidoException("Id prestacion es requerido");
        }

        if( lineaPedidoDto.getCantidadPrestacion() == null ){
            throw new LineaPedidoException("Cantidad de prestacion es requerida");
        }

        if( lineaPedidoDto.getCantidadPrestacion() <= 0 ){
            throw new LineaPedidoException("Cantidad de prestacion tiene que ser mayor a 0");
        }
    }

}
