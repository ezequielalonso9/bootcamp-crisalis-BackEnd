package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto,Long> {
    TipoProducto findByTipoProductoIgnoreCase(String tipoProducto);
}

