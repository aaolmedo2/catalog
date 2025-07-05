package com.banquito.core.loan.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.TiposComisiones;

import java.util.List;

@Repository
public interface TiposComisionesRepository extends MongoRepository<TiposComisiones, String> {

    List<TiposComisiones> findByTipoAndEstado(String tipo, String estado);

    List<TiposComisiones> findByEstado(String estado);

    List<TiposComisiones> findByTipoCalculoAndEstado(String tipoCalculo, String estado);
}
