package com.crisalis.bootcamp.helper;

import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.entities.*;
import com.crisalis.bootcamp.repositories.DetalleLineaPedidoRepository;
import com.crisalis.bootcamp.repositories.ImpuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CalculatorPedido {

    @Autowired
    ImpuestoRepository impuestoRepository;
    @Autowired
    private DetalleLineaPedidoRepository detalleLineaPedidoRepository;


    public CuentasPedido calculateResumenPedidoByLineaPedido(LineaPedido lineaPedido) {
        CuentasPedido resumenPedido = new CuentasPedido();
        Optional<Impuesto> impuestoIva = impuestoRepository.findByNombreImpuestoIgnoreCase("iva");
        Optional<Impuesto> impuestoIbb = impuestoRepository.findByNombreImpuestoIgnoreCase("ibb");
        Long idImpuestoIva = impuestoIva.get().getId();
        Long idImpuestoIbb = impuestoIbb.get().getId();

        Set<DetalleLineaPedido> detallesImpuestoLinea = lineaPedido.getDetalleImpuestos();

        detallesImpuestoLinea.forEach(detalleLineaPedido -> {
            Long idImpuesto = detalleLineaPedido.getIdImpuesto();
            boolean isImpuestoIva = Objects.equals(idImpuesto, idImpuestoIva);
            boolean isImpuestoIbb = Objects.equals(idImpuesto, idImpuestoIbb);

            if (isImpuestoIva) {
                resumenPedido.setImpuestosIva(detalleLineaPedido.getValorAdicional());
            }

            if (isImpuestoIbb) {
                resumenPedido.setImpuestosIbb(detalleLineaPedido.getValorAdicional());
            }

            if (!isImpuestoIbb && !isImpuestoIva) {
                resumenPedido.setOtrosImpuestos(detalleLineaPedido.getValorAdicional());
            }
        });

        float costoLinea = lineaPedido.getCostoLinea();
        float totalIva = resumenPedido.getImpuestosIva();
        float totalIbb = resumenPedido.getImpuestosIbb();
        float totalOtrosImpuestos = resumenPedido.getOtrosImpuestos();
        float totalImpuestos = totalIbb + totalIva + totalOtrosImpuestos;

        float totalDescuento = lineaPedido.getDescuento();
        if (totalDescuento > 2500 ){
            totalDescuento = 2500;
        }
        resumenPedido.setDescuentos(totalDescuento);

        Float totalPedido = costoLinea + totalImpuestos - totalDescuento;
        resumenPedido.setTotalPedido(totalPedido);

        return resumenPedido;
    }

    public CuentasPedido calculateResumenByPedido(Pedido pedido) {

        Long idPedido = pedido.getId();


        float totalIva = calculateImpuestoIvaByIdPedido(idPedido);
        float totalIbb = calculateImpuestoIbbByIdPedido(idPedido);
        float totalOtrosImpuestos = calculateOtherImpuestoByIdPedido(idPedido);
        float totalImpuestos = totalIbb + totalIva + totalOtrosImpuestos;
        float totalDescuento = calculateTotalDescuentoByPedido(pedido);
        float subTotal = calculateSubTotalPedidoByPedido(pedido);
        Float totalPedido = subTotal + totalImpuestos - totalDescuento;


        return CuentasPedido
                .builder()
                .impuestosIva(totalIva)
                .impuestosIbb(totalIbb)
                .otrosImpuestos(totalOtrosImpuestos)
                .descuentos(totalDescuento)
                .subTotal(subTotal)
                .totalPedido(totalPedido)
                .build();

    }

    public Float calculateSubTotalPedidoByPedido(Pedido pedido) {
        Set<LineaPedido> lineas = pedido.getLineasPedido();
        AtomicReference<Float> subTotal = new AtomicReference<>(0F);
        lineas.forEach(linea ->
                subTotal.updateAndGet(v -> v + linea.getCostoLinea())
        );
        return subTotal.get();
    }


    public Float calculateImpuestoIva() {
        Optional<Impuesto> impuestoIva = impuestoRepository.findByNombreImpuestoIgnoreCase("iva");
        if (impuestoIva.isEmpty())
            return 0F;
        return impuestoIva.get().getValorImpuesto();
    }

    public Float calculateImpuestoIvaByIdPedido(Long idPedido) {
        AtomicReference<Float> costoImpuestoIva = new AtomicReference<>(0F);

        Optional<Impuesto> impuestoIva = impuestoRepository.findByNombreImpuestoIgnoreCase("IVA");

        if (impuestoIva.isPresent()) {
            List<DetalleLineaPedido> impuestosIvaOfIdPedido = detalleLineaPedidoRepository
                    .findByIdPedidoAndIdImpuesto(idPedido, impuestoIva.get().getId());
            impuestosIvaOfIdPedido.forEach(detalle -> {
                Float valorAdicionalIva = detalle.getValorAdicional();
                costoImpuestoIva.updateAndGet(v -> v + valorAdicionalIva);
            });

        }

        return costoImpuestoIva.get();
    }

    public Float calculateImpuestoIbbByIdPedido(Long idPedido) {
        AtomicReference<Float> costoImpuestoIbb = new AtomicReference<>(0F);


        Optional<Impuesto> impuestoIbb = impuestoRepository.findByNombreImpuestoIgnoreCase("IBB");

        if (impuestoIbb.isPresent()) {
            List<DetalleLineaPedido> impuestosIbbOfIdPedido = detalleLineaPedidoRepository
                    .findByIdPedidoAndIdImpuesto(idPedido, impuestoIbb.get().getId());
            //calcular costoImpuestoIbb
            impuestosIbbOfIdPedido.forEach(detalle -> {
                Float valorAdicionalIbb = detalle.getValorAdicional();
                costoImpuestoIbb.updateAndGet(v -> v + valorAdicionalIbb);
            });

        }
        return costoImpuestoIbb.get();
    }

    public Float calculateOtherImpuestoByIdPedido(Long idPedido) {
        AtomicReference<Float> costoOtrosImpuestos = new AtomicReference<>(0F);


        Optional<Impuesto> impuestoIbb = impuestoRepository.findByNombreImpuestoIgnoreCase("IBB");
        Optional<Impuesto> impuestoIva = impuestoRepository.findByNombreImpuestoIgnoreCase("IVA");

        List<DetalleLineaPedido> othersImpuesto = detalleLineaPedidoRepository
                .findByIdPedidoAndIdImpuestoNotAndIdImpuestoNot(
                        idPedido,
                        impuestoIva.get().getId(),
                        impuestoIbb.get().getId());

        othersImpuesto.forEach(detalle -> {
            Float valorAdicionalOtrosImpuestos = detalle.getValorAdicional();
            costoOtrosImpuestos.updateAndGet(v -> v + valorAdicionalOtrosImpuestos);
        });

        return costoOtrosImpuestos.get();
    }

    public float calculateAdicionalGarantia(LineaPedidoDto lineaPedidoDto,
                                            Prestacion prestacion) {
        float adicionalGarantia = 0F;
        Integer añosGarantia = lineaPedidoDto.getAñosGarantia();

        if (añosGarantia != null) {
            float costoPrestacion = prestacion.getCosto();
            adicionalGarantia = (float) (añosGarantia * costoPrestacion * 0.02);
        }

        return adicionalGarantia;
    }

    public float calculateAdicionalSoporte(Servicio prestacion) {

        float adicionalSoporte = 0F;

        Float cargoAdicionalSoporte = prestacion.getCargoAdicionalSoporte();
        if (cargoAdicionalSoporte != null) {
            adicionalSoporte += cargoAdicionalSoporte;
        }

        return adicionalSoporte;
    }

    public Float calculateTotalLinea(Integer cantidadPrestacion,
                                     Float costoPrestacion,
                                     Float cargoAdicional) {
        float costoTotalLinea = cantidadPrestacion * costoPrestacion;
        if (cargoAdicional == null) {
            return costoTotalLinea;
        }
        return costoTotalLinea + cargoAdicional;
    }

    public Float calculateTotalDescuentoByPedido(Pedido pedido) {
        Set<LineaPedido> lineasPedido = pedido.getLineasPedido();
        AtomicReference<Float> totalDescuento = new AtomicReference<>(0f);
        lineasPedido.forEach(linea -> {
            totalDescuento.updateAndGet(v -> v + linea.getDescuento());
        });
        return totalDescuento.get();
    }

    public CuentasLinea calculateLinea(Prestacion prestacion, LineaPedidoDto lineaPedidoDto) {


        Integer cantidadPrestacion = lineaPedidoDto.getCantidadPrestacion();
        float adicionalSoporte = 0, adicionalGarantia = 0;
        float totalAdicionalSoporte = 0, totalAdicionalGarantia = 0;


        if (prestacion.getClass() == Servicio.class) {
            adicionalSoporte = calculateAdicionalSoporte((Servicio) prestacion);
            totalAdicionalSoporte = adicionalSoporte * cantidadPrestacion;

        } else {
            adicionalGarantia = calculateAdicionalGarantia(lineaPedidoDto, prestacion);
            totalAdicionalGarantia = adicionalGarantia * cantidadPrestacion;
        }

        float adicional = totalAdicionalSoporte + totalAdicionalGarantia;
        Float totalLinea = calculateTotalLinea(
                cantidadPrestacion,
                prestacion.getCosto(),
                adicional
        );

        return CuentasLinea
                .builder()
                .costoUnitarioSoporte(adicionalSoporte)
                .costoUnitarioGarantia(adicionalGarantia)
                .totalAdicionalSoporte(totalAdicionalSoporte)
                .totalAdicionalGarantia(totalAdicionalGarantia)
                .costoTotalLiena(totalLinea)
                .build();
    }


    public Float calculateDescuentoLinea(float costoProducto, float cantidad) {

        float descuento = 0.10F;
        return descuento * costoProducto * cantidad;
    }
}
