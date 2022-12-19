package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.model.dto.DetallePedidoDto;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.entities.*;
import com.crisalis.bootcamp.repositories.ClienteRepository;
import com.crisalis.bootcamp.repositories.DetallePedidoRepository;
import com.crisalis.bootcamp.repositories.ImpuestoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * Los datos utilizados para realizar los test
 * se cargan desde /backend/src/main/resources/data.sql
 */
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ImpuestoRepository impuestoRepository;
    Boolean estado = true;
    Cliente clienteTest;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;


//    @BeforeEach
//    void setUp() {
//        if (estado){
//            estado = false;
//
//            //Initialize Cliente
//            Persona personaTest = Persona
//                    .builder()
//                    .nombre("Test")
//                    .dni(99999999)
//                    .apellido("apellido")
//                    .build();
//
//            Cliente cliente = Cliente
//                    .builder()
//                    .estado(true)
//                    .persona(personaTest)
//                    .build();
//            clienteTest = clienteRepository.save(cliente);
//
//            //Initialize Impuestos
//            Impuesto iva = Impuesto
//                    .builder()
//                    .nombreImpuesto("IVA")
//                    .valorImpuesto(21F)
//                    .build();
//
//            Impuesto impuestoProvincial = Impuesto
//                    .builder()
//                    .nombreImpuesto("impuesto provincial")
//                    .valorImpuesto(10.3F)
//                    .build();
//            impuestoRepository.saveAll(List.of(impuestoProvincial,iva));
//
//
//            //Initialize Producto
//            Producto teclado = Producto
//                    .builder()
//                    .costo(10F)
////                    .tipoProducto(TipoProductoEnum.PRODUCTO)
//                    .build();
//        }
//
//    }


    @Test
    @Order(1)
    public void createPedido(){

        Optional<Impuesto> impuestoOptional1 = impuestoRepository.findById(1L);
        Optional<Impuesto> impuestoOptional2 = impuestoRepository.findById(2L);
        Impuesto impuesto1, impuesto2;
        Set<Impuesto> impuestos = new HashSet<>();

        if (impuestoOptional1.isPresent() ){
            impuesto1 = impuestoOptional1.get();
            impuestos.add(impuesto1);
        }
        if (impuestoOptional2.isPresent() ){
            impuesto2 = impuestoOptional2.get();
            impuestos.add(impuesto2);
        }

        DetallePedidoDto detalle1 = DetallePedidoDto
                .builder()
                .idProducto(1L)
                .cantidadProducto(2)
                .yearsGarantia(0)
                .impuestos(impuestos)
                .build();

        DetallePedidoDto detalle2 = DetallePedidoDto
                .builder()
                .idProducto(2L)
                .cantidadProducto(2)
                .yearsGarantia(0)
                .impuestos(impuestos)
                .build();
        Set<DetallePedidoDto> detalles = new HashSet<>(List.of(detalle1, detalle2));

        PedidoDto pedidoTest = PedidoDto
                .builder()
                .idCliente(1L)
                .detalles(detalles)
                .build();


        Pedido pedidoCreated = pedidoService.createAndSave(pedidoTest);

    }

    @Test
    @Order(2)
    public void deleteAllPeidos(){
        int cantidadClientes = clienteRepository.findAll().size();
        pedidoService.deleteAll();
        int cantidadClientesAfterDeletePedido = clienteRepository.findAll().size();
        Assertions.assertEquals(cantidadClientesAfterDeletePedido, cantidadClientes);
    }
    @Test
    public void validarCamposPedidoDto(){}
}