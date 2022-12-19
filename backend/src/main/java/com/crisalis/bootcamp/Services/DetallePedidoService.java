package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.ProductNotFoundException;
import com.crisalis.bootcamp.model.dto.DetallePedidoDto;
import com.crisalis.bootcamp.model.entities.DetallePedido;
import com.crisalis.bootcamp.model.entities.Producto;
import com.crisalis.bootcamp.repositories.DetallePedidoRepository;
import com.crisalis.bootcamp.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DetallePedidoService {
    @Autowired
    DetallePedidoRepository detallePedidoRepository;
    @Autowired
    ProductoRepository productoRepository;

    private static final float PORCENTAJE_GARANTIA = 0.02F;

    public Set<DetallePedido> createDetallePedido(Set<DetallePedidoDto> detalles) {

        Set<DetallePedido> detallePedidos = new HashSet<>();

        for( DetallePedidoDto detalle: detalles ){

            DetallePedido detallePedido = createDetallePedido(detalle);
            detallePedido.calculateAndSetCargoAdicionalGarantia(PORCENTAJE_GARANTIA);
            detallePedido.calculateAndSetCostoLinea();

            detallePedidos.add(detallePedido);
        }

        return detallePedidos;
    }

    public Producto getProductById(Long id ){

        Producto producto=  productoRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product whit id: " + id + " not found.")
                );
        return producto;
    }


    public DetallePedido createDetallePedido(DetallePedidoDto detalle){

        DetallePedido detallePedido = new DetallePedido();
        Producto producto = getProductById(detalle.getIdProducto());
        detallePedido.setProducto(producto);
        detallePedido.setCantidadProducto(detalle.getCantidadProducto());
        detallePedido.setYearsGarantia(detalle.getYearsGarantia());
        detallePedido.setImpuestos(detalle.getImpuestos());
        return detallePedido;

    }




}
