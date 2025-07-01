package com.banquito.core.loan.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.TiposPrestamos;

@Repository
public interface TiposPrestamosRepository extends MongoRepository<TiposPrestamos, String> {
}
