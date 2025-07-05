package com.banquito.core.loan.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.banquito.core.loan.catalog.model.Prestamos;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PrestamosRepository extends MongoRepository<Prestamos, String> {

    List<Prestamos> findByIdTipoPrestamoAndEstado(String idTipoPrestamo, String estado);

    List<Prestamos> findByEstado(String estado);

    List<Prestamos> findByIdMonedaAndEstado(String idMoneda, String estado);

    @Query("{'montoMinimo': {$lte: ?0}, 'montoMaximo': {$gte: ?0}, 'estado': ?1}")
    List<Prestamos> findByMontoRangeAndEstado(BigDecimal monto, String estado);

    @Query("{'plazoMinimoMeses': {$lte: ?0}, 'plazoMaximoMeses': {$gte: ?0}, 'estado': ?1}")
    List<Prestamos> findByPlazoRangeAndEstado(Integer plazoMeses, String estado);
}
