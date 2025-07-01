package com.banquito.core.loan.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.Seguros;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SegurosRepository extends MongoRepository<Seguros, String> {

    List<Seguros> findByTipoSeguroAndEstado(String tipoSeguro, String estado);

    List<Seguros> findByEstado(String estado);

    List<Seguros> findByFechaFinAfterAndEstado(LocalDate fecha, String estado);

    List<Seguros> findByCompaniaAndEstado(String compania, String estado);
}
