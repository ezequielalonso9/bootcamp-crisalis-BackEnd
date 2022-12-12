package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.ProductException;
import com.crisalis.bootcamp.exceptions.custom.ProductNotFoundException;
import com.crisalis.bootcamp.model.dto.ProductoDto;
import com.crisalis.bootcamp.model.entities.Producto;
import com.crisalis.bootcamp.model.entities.TipoProducto;
import com.crisalis.bootcamp.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;


@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public Producto createProducto(ProductoDto productoDto){
        validateProductoDto(productoDto);
        return productoRepository.save(productoDto.toProducto());
    };

    public Producto updateProductById(Long id, ProductoDto newProduct){
        if( id == null ) throw new ProductException("Product Id is required");

        validateProductoDto(newProduct);

        TipoProducto tipoProductoUpdated = TipoProducto
                .builder()
                .id(newProduct.getIdTipoProducto())
                .build();

        return productoRepository.findById(id)
                .map( product -> {
                    product.setNombre( newProduct.getNombre() );
                    product.setCosto( newProduct.getCosto() );
                    product.setCargoAdicional( newProduct.getCargoAdicional() );
                    product.setCargoAdicional( newProduct.getCargoAdicional() );
                    product.setTipoProducto( tipoProductoUpdated );
                    return productoRepository.save(product);
                }).orElseThrow(
                        () -> new ProductNotFoundException("Product with id " + id + " not found")
                );
    }

    public void validateProductoDto(ProductoDto product){
        if( product == null ) throw new ProductException("Product is required");

        if( product.getNombre() == null || product.getNombre().isEmpty() )
            throw new ProductException("Nombre is required");

        if( product.getCosto() == null ) throw new ProductException("Costo is required");

        if( product.getIdTipoProducto() == null ) throw new ProductException("IdTipoProducto is required");
    }


    public Boolean deleteProductById(Long id){
        if( id == null ){
            throw new ProductException("ProductId is required");
        }
        productoRepository.deleteById(id);
        return !productoRepository.existsById(id);
    }

//    public Boolean deleteProductoById(){Long id}

}
