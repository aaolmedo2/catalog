package com.banquito.core.loan.catalog.repository;

import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.Garantias;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface GarantiasRepository extends MongoRepository<Garantias, String> {

    List<Garantias> findByTipoGarantiaAndEstado(String tipoGarantia, String estado);

    List<Garantias> findByEstado(String estado);
}
