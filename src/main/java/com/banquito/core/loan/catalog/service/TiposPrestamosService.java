package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.enums.EstadoGeneralEnum;
import com.banquito.core.loan.catalog.enums.TipoClienteEnum;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.exception.UpdateException;
import com.banquito.core.loan.catalog.model.Garantias;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import com.banquito.core.loan.catalog.repository.TiposPrestamosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TiposPrestamosService {

    private final TiposPrestamosRepository tiposPrestamosRepository;
    private final GarantiasService garantiasService;

    public TiposPrestamosService(TiposPrestamosRepository tiposPrestamosRepository, GarantiasService garantiasService) {
        this.tiposPrestamosRepository = tiposPrestamosRepository;
        this.garantiasService = garantiasService;
    }

    @Transactional(readOnly = true)
    public List<TiposPrestamos> findAll() {
        log.info("Obteniendo todos los tipos de préstamos activos");
        return this.tiposPrestamosRepository.findByEstado(EstadoGeneralEnum.ACTIVO.getValor());
    }

    @Transactional(readOnly = true)
    public TiposPrestamos findById(String id) {
        log.info("Buscando tipo de préstamo con ID: {}", id);
        return this.tiposPrestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoPréstamo",
                        "No se encontró el tipo de préstamo con id: " + id));
    }

    @Transactional
    public TiposPrestamos create(TiposPrestamos tipoPrestamo) {
        log.info("Creando nuevo tipo de préstamo: {}", tipoPrestamo);
        try {
            // Validar si la garantía existe
            Garantias garantia;
            try {
                garantia = this.garantiasService.findById(tipoPrestamo.getIdGarantia());
                if (!EstadoGeneralEnum.ACTIVO.getValor().equals(garantia.getEstado())) {
                    throw new CreateException("TipoPréstamo", "La garantía está inactiva");
                }
            } catch (EntityNotFoundException e) {
                throw new CreateException("TipoPréstamo", "La garantía especificada no existe");
            }

            // Validar si el tipo de cliente es válido
            boolean tipoClienteValido = false;
            for (TipoClienteEnum tipo : TipoClienteEnum.values()) {
                if (tipo.getValor().equals(tipoPrestamo.getTipoCliente())) {
                    tipoClienteValido = true;
                    break;
                }
            }

            if (!tipoClienteValido) {
                throw new CreateException("TipoPréstamo", "El tipo de cliente no es válido");
            }

            // Setear el esquema de amortización a FRANCES
            tipoPrestamo.setEsquemaAmortizacion("FRANCES");

            // Setear fechas y estado
            LocalDateTime now = LocalDateTime.now();
            tipoPrestamo.setFechaCreacion(now);
            tipoPrestamo.setFechaModificacion(now);
            tipoPrestamo.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            tipoPrestamo.setVersion(1L);

            return this.tiposPrestamosRepository.save(tipoPrestamo);
        } catch (CreateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al crear tipo de préstamo: {}", e.getMessage());
            throw new CreateException("TipoPréstamo", "Error al crear tipo de préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public TiposPrestamos update(String id, TiposPrestamos tipoPrestamo) {
        log.info("Actualizando tipo de préstamo con ID {}: {}", id, tipoPrestamo);
        try {
            TiposPrestamos tipoPrestamoExistente = this.findById(id);

            // Validar si la garantía existe
            if (!tipoPrestamoExistente.getIdGarantia().equals(tipoPrestamo.getIdGarantia())) {
                Garantias garantia;
                try {
                    garantia = this.garantiasService.findById(tipoPrestamo.getIdGarantia());
                    if (!EstadoGeneralEnum.ACTIVO.getValor().equals(garantia.getEstado())) {
                        throw new UpdateException("TipoPréstamo", "La garantía está inactiva");
                    }
                } catch (EntityNotFoundException e) {
                    throw new UpdateException("TipoPréstamo", "La garantía especificada no existe");
                }
            }

            // Validar si el tipo de cliente es válido
            boolean tipoClienteValido = false;
            for (TipoClienteEnum tipo : TipoClienteEnum.values()) {
                if (tipo.getValor().equals(tipoPrestamo.getTipoCliente())) {
                    tipoClienteValido = true;
                    break;
                }
            }

            if (!tipoClienteValido) {
                throw new UpdateException("TipoPréstamo", "El tipo de cliente no es válido");
            }

            // Actualizar campos
            tipoPrestamoExistente.setIdMoneda(tipoPrestamo.getIdMoneda());
            tipoPrestamoExistente.setNombre(tipoPrestamo.getNombre());
            tipoPrestamoExistente.setDescripcion(tipoPrestamo.getDescripcion());
            tipoPrestamoExistente.setRequisitos(tipoPrestamo.getRequisitos());
            tipoPrestamoExistente.setTipoCliente(tipoPrestamo.getTipoCliente());
            tipoPrestamoExistente.setIdGarantia(tipoPrestamo.getIdGarantia());
            tipoPrestamoExistente.setFechaModificacion(LocalDateTime.now());
            tipoPrestamoExistente.setVersion(tipoPrestamoExistente.getVersion() + 1);

            return this.tiposPrestamosRepository.save(tipoPrestamoExistente);
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar tipo de préstamo: {}", e.getMessage());
            throw new UpdateException("TipoPréstamo", "Error al actualizar tipo de préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        log.info("Eliminando lógicamente el tipo de préstamo con ID: {}", id);
        try {
            TiposPrestamos tipoPrestamo = this.findById(id);
            tipoPrestamo.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            tipoPrestamo.setFechaModificacion(LocalDateTime.now());
            tipoPrestamo.setVersion(tipoPrestamo.getVersion() + 1);

            this.tiposPrestamosRepository.save(tipoPrestamo);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar tipo de préstamo: {}", e.getMessage());
            throw new DeleteException("TipoPréstamo", "Error al eliminar tipo de préstamo: " + e.getMessage());
        }
    }
}
