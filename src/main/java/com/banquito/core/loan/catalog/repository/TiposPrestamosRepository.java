package com.banquito.core.loan.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.TiposPrestamos;

import java.util.List;

@Repository
public interface TiposPrestamosRepository extends MongoRepository<TiposPrestamos, String> {

    List<TiposPrestamos> findByEstado(String estado);

    List<TiposPrestamos> findByTipoCliente(String tipoCliente);

    List<TiposPrestamos> findByTipoClienteAndEstado(String tipoCliente, String estado);
}
