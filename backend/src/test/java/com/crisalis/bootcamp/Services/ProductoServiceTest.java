package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.ProductException;
import com.crisalis.bootcamp.model.dto.ProductoDto;
import com.crisalis.bootcamp.model.entities.Producto;
import com.crisalis.bootcamp.repositories.ProductoRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoServiceTest {

    @Autowired
    ProductoService productoService;
    @Autowired
    ProductoRepository productoRepository;

    static Long idProductCreated;


    @Test
    @Order(1)
    void createProducto() {

        int quantity = productoRepository.findAll().size();

        ProductoDto productoDto = ProductoDto
                .builder()
                .nombre("Router")
                .costo(250F)
                .idTipoProducto(1L)
                .build();

        Producto productoCreated =  productoService.createProducto(productoDto);
        idProductCreated  = productoCreated.getId();
        Integer quantityAfterCreate = productoRepository.findAll().size();

        AssertionsForClassTypes.assertThat(productoCreated.getId()).isNotNull();
        Assertions.assertEquals(quantity + 1, quantityAfterCreate );
    }

    /**
     * Tiene que arrojar un RolException si falta algun campo
     */
    @Test
    @Order(2)
    public void createProductWithOutField(){

        ProductoDto productoDto = ProductoDto
                .builder()
                .nombre("Router")
                .idTipoProducto(1L)
                .build();

        Exception exception = Assertions.assertThrows(
                ProductException.class,
                () -> productoService.createProducto(productoDto));

        Assertions.assertEquals("Costo is required", exception.getMessage());
    }

    /**
     * Update product created in Test 1
     */
    @Test
    @Order(3)
    public void updateProduct(){
        ProductoDto productoDto = ProductoDto
                .builder()
                .nombre("Cable")
                .costo(50F)
                .idTipoProducto(2L)
                .build();

        Producto productoUpdated = productoService.updateProductById(idProductCreated, productoDto);

        Assertions.assertEquals(idProductCreated, productoUpdated.getId());
        Assertions.assertEquals("Cable", productoUpdated.getNombre());

    }

    @Test
    @Order(3)
    public void deleteProductById(){

        int totalProduct = productoRepository.findAll().size();

        Boolean deleteOk = productoService.deleteProductById(idProductCreated);

        int totalProductAfterDelete = productoRepository.findAll().size();

        Assertions.assertEquals( totalProduct - 1 , totalProductAfterDelete );
        Assertions.assertEquals(true, deleteOk );
    }

}