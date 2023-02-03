//package com.crisalis.bootcamp.Services;
//
//import com.crisalis.bootcamp.exceptions.custom.PrestacionException;
//import com.crisalis.bootcamp.model.dto.ProductDto;
//import com.crisalis.bootcamp.model.entities.Producto;
//import com.crisalis.bootcamp.repositories.ProductoRepository;
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ProductoServiceTest {
//
//    @Autowired
//    ProductoService productoService;
//    @Autowired
//    ProductoRepository productoRepository;
//
//    static Long idProductCreated;
//
//
//    @Test
//    @Order(1)
//    void createProducto() {
//
//        int quantity = productoRepository.findAll().size();
//
//        ProductDto prestacionDto = ProductDto
//                .builder()
//                .nombre("Router")
//                .costo(250F)
//                .build();
//
//        Producto productoCreated =  productoService.createProducto(prestacionDto);
//        idProductCreated  = productoCreated.getId();
//        Integer quantityAfterCreate = productoRepository.findAll().size();
//
//        AssertionsForClassTypes.assertThat(productoCreated.getId()).isNotNull();
//        Assertions.assertEquals(quantity + 1, quantityAfterCreate );
//    }
//
//    /**
//     * Tiene que arrojar un RolException si falta algun campo
//     */
//    @Test
//    @Order(2)
//    public void createProductWithOutField(){
//
//        ProductDto prestacionDto = ProductDto
//                .builder()
//                .nombre("Router")
//                .idTipoProducto(1L)
//                .build();
//
//        Exception exception = Assertions.assertThrows(
//                PrestacionException.class,
//                () -> productoService.createProducto(prestacionDto));
//
//        Assertions.assertEquals("Costo is required", exception.getMessage());
//    }
//
//    /**
//     * Update product created in Test 1
//     */
//    @Test
//    @Order(3)
//    public void updateProduct(){
//        ProductDto prestacionDto = ProductDto
//                .builder()
//                .nombre("Cable")
//                .costo(50F)
//                .idTipoProducto(2L)
//                .build();
//
//        Producto productoUpdated = productoService.updateProductById(idProductCreated, prestacionDto);
//
//        Assertions.assertEquals(idProductCreated, productoUpdated.getId());
//        Assertions.assertEquals("Cable", productoUpdated.getNombre());
//
//    }
//
//    @Test
//    @Order(3)
//    public void deleteProductById(){
//
//        int totalProduct = productoRepository.findAll().size();
//
//        Boolean deleteOk = productoService.deleteProductById(idProductCreated);
//
//        int totalProductAfterDelete = productoRepository.findAll().size();
//
//        Assertions.assertEquals( totalProduct - 1 , totalProductAfterDelete );
//        Assertions.assertEquals(true, deleteOk );
//    }
//
//}