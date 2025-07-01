package com.banquito.core.loan.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.TiposComisiones;

@Repository
public interface TiposComisionesRepository extends MongoRepository<TiposComisiones, String> {
}
