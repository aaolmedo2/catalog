package com.banquito.core.loan.catalog.repository;

import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.Garantias;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface GarantiasRepository extends MongoRepository<Garantias, String> {
}
