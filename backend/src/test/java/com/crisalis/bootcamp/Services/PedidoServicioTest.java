package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.entities.*;
import com.crisalis.bootcamp.repositories.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Los datos utilizados para realizar los test
 * se cargan desde /backend/src/main/resources/data.sql
 */
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PedidoServicioTest {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ImpuestoRepository impuestoRepository;
    Boolean estado = true;
    Cliente clienteTest;
    @Autowired
    private LineaPedidoRepository lineaPedidoRepository;
    @Autowired
    private PrestacionRepository prestacionRepository;
    @Autowired
    private PrestacionImpuestoRepository prestacionImpuestoRepository;


    @Test
    @Order(1)
    public void createPedido(){

        LineaPedidoDto linea1 = LineaPedidoDto
                .builder()
//                .idPrestacion(1L) // Producto:teclado
                .idPrestacion(5L) //Servicio: PepitoServer con costo de soporte
                .cantidadPrestacion(5)
                .build();

        PedidoDto pedidoDto = PedidoDto
                .builder()
                .idCliente(222222L)
                .tipoCliente("EMPRESA")
                .linea(linea1)
                .build();


        pedidoService.save(pedidoDto);

    }

    @Test
    @Order(2)
    public void deleteAllPeidos(){
        int cantidadClientes = clienteRepository.findAll().size();
        int cantidadPrestaciones = prestacionRepository.findAll().size();
        int cantidadImpuestos = impuestoRepository.findAll().size();

        pedidoService.deleteAll();

        int cantidadClientesAfterDeletePedidos = clienteRepository.findAll().size();
        int cantidadProductosAfterDeletePedidos = prestacionRepository.findAll().size();
        int cantidadImpuestosAfterDeletePedidos = impuestoRepository.findAll().size();
        int cantidadDetallePedidoImpuestos = prestacionImpuestoRepository.findAll().size();

        Assertions.assertEquals(cantidadClientesAfterDeletePedidos, cantidadClientes);
        Assertions.assertEquals(cantidadProductosAfterDeletePedidos, cantidadPrestaciones);
        Assertions.assertEquals(cantidadImpuestosAfterDeletePedidos, cantidadImpuestos);
        Assertions.assertEquals(0, cantidadDetallePedidoImpuestos);

    }
    @Test
    public void validarCamposPedidoDto(){}
}