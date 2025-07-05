package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.enums.BaseCalculoEnum;
import com.banquito.core.loan.catalog.enums.EstadoGeneralEnum;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.exception.UpdateException;
import com.banquito.core.loan.catalog.model.Prestamos;
import com.banquito.core.loan.catalog.model.Seguros;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import com.banquito.core.loan.catalog.repository.PrestamosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PrestamosService {

    private final PrestamosRepository prestamosRepository;
    private final TiposPrestamosService tiposPrestamosService;
    private final SegurosService segurosService;
    private final TiposComisionesService tiposComisionesService;

    public PrestamosService(PrestamosRepository prestamosRepository,
            TiposPrestamosService tiposPrestamosService,
            SegurosService segurosService,
            TiposComisionesService tiposComisionesService) {
        this.prestamosRepository = prestamosRepository;
        this.tiposPrestamosService = tiposPrestamosService;
        this.segurosService = segurosService;
        this.tiposComisionesService = tiposComisionesService;
    }

    @Transactional(readOnly = true)
    public List<Prestamos> findAll() {
        log.info("Obteniendo todos los préstamos activos");
        return this.prestamosRepository.findByEstado(EstadoGeneralEnum.ACTIVO.getValor());
    }

    @Transactional(readOnly = true)
    public Prestamos findById(String id) {
        log.info("Buscando préstamo con ID: {}", id);
        return this.prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo", "No se encontró el préstamo con id: " + id));
    }

    @Transactional
    public Prestamos create(Prestamos prestamo) {
        log.info("Creando nuevo préstamo: {}", prestamo);
        try {
            // Validar si el tipo de préstamo existe
            TiposPrestamos tipoPrestamo;
            try {
                tipoPrestamo = this.tiposPrestamosService.findById(prestamo.getIdTipoPrestamo());
                if (!EstadoGeneralEnum.ACTIVO.getValor().equals(tipoPrestamo.getEstado())) {
                    throw new CreateException("Préstamo", "El tipo de préstamo está inactivo");
                }
            } catch (EntityNotFoundException e) {
                throw new CreateException("Préstamo", "El tipo de préstamo especificado no existe");
            }

            // Validar si el seguro existe
            Seguros seguro;
            try {
                seguro = this.segurosService.findById(prestamo.getIdSeguro());
                if (!EstadoGeneralEnum.ACTIVO.getValor().equals(seguro.getEstado())) {
                    throw new CreateException("Préstamo", "El seguro está inactivo");
                }
            } catch (EntityNotFoundException e) {
                throw new CreateException("Préstamo", "El seguro especificado no existe");
            }

            // Validar si el tipo de comisión existe
            TiposComisiones tipoComision;
            try {
                tipoComision = this.tiposComisionesService.findById(prestamo.getIdTipoComision());
                if (!EstadoGeneralEnum.ACTIVO.getValor().equals(tipoComision.getEstado())) {
                    throw new CreateException("Préstamo", "El tipo de comisión está inactivo");
                }
            } catch (EntityNotFoundException e) {
                throw new CreateException("Préstamo", "El tipo de comisión especificado no existe");
            }

            // Validar si la base de cálculo es válida
            boolean baseCalculoValida = false;
            for (BaseCalculoEnum baseCalculo : BaseCalculoEnum.values()) {
                if (baseCalculo.getValor().equals(prestamo.getBaseCalculo())) {
                    baseCalculoValida = true;
                    break;
                }
            }

            if (!baseCalculoValida) {
                throw new CreateException("Préstamo", "La base de cálculo no es válida");
            }

            // Setear fechas y estado
            prestamo.setFechaModificacion(LocalDateTime.now());
            prestamo.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            prestamo.setVersion(1L);

            return this.prestamosRepository.save(prestamo);
        } catch (CreateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al crear préstamo: {}", e.getMessage());
            throw new CreateException("Préstamo", "Error al crear préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public Prestamos update(String id, Prestamos prestamo) {
        log.info("Actualizando préstamo con ID {}: {}", id, prestamo);
        try {
            Prestamos prestamoExistente = this.findById(id);

            // Validar si el tipo de préstamo existe
            if (!prestamoExistente.getIdTipoPrestamo().equals(prestamo.getIdTipoPrestamo())) {
                TiposPrestamos tipoPrestamo;
                try {
                    tipoPrestamo = this.tiposPrestamosService.findById(prestamo.getIdTipoPrestamo());
                    if (!EstadoGeneralEnum.ACTIVO.getValor().equals(tipoPrestamo.getEstado())) {
                        throw new UpdateException("Préstamo", "El tipo de préstamo está inactivo");
                    }
                } catch (EntityNotFoundException e) {
                    throw new UpdateException("Préstamo", "El tipo de préstamo especificado no existe");
                }
            }

            // Validar si el seguro existe
            if (!prestamoExistente.getIdSeguro().equals(prestamo.getIdSeguro())) {
                Seguros seguro;
                try {
                    seguro = this.segurosService.findById(prestamo.getIdSeguro());
                    if (!EstadoGeneralEnum.ACTIVO.getValor().equals(seguro.getEstado())) {
                        throw new UpdateException("Préstamo", "El seguro está inactivo");
                    }
                } catch (EntityNotFoundException e) {
                    throw new UpdateException("Préstamo", "El seguro especificado no existe");
                }
            }

            // Validar si el tipo de comisión existe
            if (!prestamoExistente.getIdTipoComision().equals(prestamo.getIdTipoComision())) {
                TiposComisiones tipoComision;
                try {
                    tipoComision = this.tiposComisionesService.findById(prestamo.getIdTipoComision());
                    if (!EstadoGeneralEnum.ACTIVO.getValor().equals(tipoComision.getEstado())) {
                        throw new UpdateException("Préstamo", "El tipo de comisión está inactivo");
                    }
                } catch (EntityNotFoundException e) {
                    throw new UpdateException("Préstamo", "El tipo de comisión especificado no existe");
                }
            }

            // Validar si la base de cálculo es válida
            boolean baseCalculoValida = false;
            for (BaseCalculoEnum baseCalculo : BaseCalculoEnum.values()) {
                if (baseCalculo.getValor().equals(prestamo.getBaseCalculo())) {
                    baseCalculoValida = true;
                    break;
                }
            }

            if (!baseCalculoValida) {
                throw new UpdateException("Préstamo", "La base de cálculo no es válida");
            }

            // Actualizar campos
            prestamoExistente.setIdTipoPrestamo(prestamo.getIdTipoPrestamo());
            prestamoExistente.setIdMoneda(prestamo.getIdMoneda());
            prestamoExistente.setNombre(prestamo.getNombre());
            prestamoExistente.setDescripcion(prestamo.getDescripcion());
            prestamoExistente.setBaseCalculo(prestamo.getBaseCalculo());
            prestamoExistente.setTasaInteres(prestamo.getTasaInteres());
            prestamoExistente.setMontoMinimo(prestamo.getMontoMinimo());
            prestamoExistente.setMontoMaximo(prestamo.getMontoMaximo());
            prestamoExistente.setPlazoMinimoMeses(prestamo.getPlazoMinimoMeses());
            prestamoExistente.setPlazoMaximoMeses(prestamo.getPlazoMaximoMeses());
            prestamoExistente.setTipoAmortizacion(prestamo.getTipoAmortizacion());
            prestamoExistente.setIdSeguro(prestamo.getIdSeguro());
            prestamoExistente.setIdTipoComision(prestamo.getIdTipoComision());
            prestamoExistente.setFechaModificacion(LocalDateTime.now());
            prestamoExistente.setVersion(prestamoExistente.getVersion() + 1);

            return this.prestamosRepository.save(prestamoExistente);
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar préstamo: {}", e.getMessage());
            throw new UpdateException("Préstamo", "Error al actualizar préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        log.info("Eliminando lógicamente el préstamo con ID: {}", id);
        try {
            Prestamos prestamo = this.findById(id);
            prestamo.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            prestamo.setFechaModificacion(LocalDateTime.now());
            prestamo.setVersion(prestamo.getVersion() + 1);

            this.prestamosRepository.save(prestamo);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar préstamo: {}", e.getMessage());
            throw new DeleteException("Préstamo", "Error al eliminar préstamo: " + e.getMessage());
        }
    }
}
