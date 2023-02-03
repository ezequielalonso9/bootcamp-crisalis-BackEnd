package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.model.dto.PrestacionDto;
import com.crisalis.bootcamp.model.entities.Prestacion;
import com.crisalis.bootcamp.model.entities.Producto;
import com.crisalis.bootcamp.model.entities.Servicio;
import com.crisalis.bootcamp.repositories.PrestacionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrestacionServicioTest {

    @Autowired
    PrestacionService prestacionService;
    @Autowired
    PrestacionRepository prestacionRepository;


//    @Test
//    @Order(1)
//    public void createProduct(){
//        prestacionRepository.deleteAll();
//
//        Prestacion product1 = Producto
//                .builder()
//                .costo(100F)
//                .nombre("product1")
//                .detalle("product test")
//                .build();
//        prestacionService.create(product1);
//    }

//    @Test
//    @Order(2)
//    public void updateProduct(){
//        Prestacion product1 = Producto
//                .builder()
//                .costo(200F)
//                .nombre("product1")
//                .detalle("Actualizado, el costo era $100")
//                .build();
//        prestacionService.updatePrestacionById(1L ,product1);
//
//    }

    @Test
    @Order(3)
    public void deleteProductByIdg(){
        prestacionService.deletePrestacionById(1L);
        boolean exist = prestacionRepository.existsById(1L);
        Assertions.assertFalse(exist);
    }


//    @Test
//    @Order(4)
//    public void createServicio(){
//        Prestacion servicio1 = Servicio
//                .builder()
//                .nombre("servicio1")
//                .detalle("servicio test")
//                .costo(50F)
//                .cargo_adicional_soporte(10F)
//                .build();
//        Prestacion creado = prestacionService.create(servicio1);
//        Assertions.assertNotNull(creado.getId());
//    }

//    @Test
//    @Order(5)
//    public void updateService(){
//        Prestacion servicio1 = Servicio
//                .builder()
//                .nombre("servicio1")
//                .detalle("Antes costo $50, y soporte $10")
//                .costo(100F)
//                .cargo_adicional_soporte(5F)
//                .build();
//
//        Prestacion updated = prestacionService.updatePrestacionById(2L, servicio1);
//        Assertions.assertEquals(2L, updated.getId());
//    }

    @Test
    public void findAll(){
        List<Prestacion> prestaciones = prestacionRepository.findAll();
        List<PrestacionDto> prestacionesDto = new ArrayList<>();
        prestaciones.forEach(prestacion ->
                prestacionesDto.add( prestacion.toDto())
        );
        System.out.println(prestacionesDto);
        System.out.println(prestaciones);
    }
}