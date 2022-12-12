package com.crisalis.bootcamp.repositories;

import com.crisalis.bootcamp.model.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface EmpresaRepository extends JpaRepository<Empresa,Long>, QueryByExampleExecutor<Empresa> {
}
