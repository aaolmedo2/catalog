package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.enums.EstadoGeneralEnum;
import com.banquito.core.loan.catalog.enums.TipoCalculoComisionEnum;
import com.banquito.core.loan.catalog.enums.TipoComisionEnum;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import com.banquito.core.loan.catalog.repository.TiposComisionesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TiposComisionesService {

    private final TiposComisionesRepository tiposComisionesRepository;

    public TiposComisionesService(TiposComisionesRepository tiposComisionesRepository) {
        this.tiposComisionesRepository = tiposComisionesRepository;
    }

    @Transactional(readOnly = true)
    public List<TiposComisiones> findAll() {
        log.info("Obteniendo todos los tipos de comisiones activos");
        return this.tiposComisionesRepository.findByEstado(EstadoGeneralEnum.ACTIVO.getValor());
    }

    @Transactional(readOnly = true)
    public TiposComisiones findById(String id) {
        log.info("Buscando tipo de comisión con ID: {}", id);
        return this.tiposComisionesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TipoComisión",
                        "No se encontró el tipo de comisión con id: " + id));
    }

    @Transactional
    public TiposComisiones create(TiposComisiones tipoComision) {
        log.info("Creando nuevo tipo de comisión: {}", tipoComision);
        try {
            // Validar si el tipo de comisión es válido
            boolean tipoValido = false;
            for (TipoComisionEnum tipo : TipoComisionEnum.values()) {
                if (tipo.getValor().equals(tipoComision.getTipo())) {
                    tipoValido = true;
                    break;
                }
            }

            if (!tipoValido) {
                throw new CreateException("TipoComisión", "El tipo de comisión no es válido");
            }

            // Validar si el tipo de cálculo es válido
            boolean tipoCalculoValido = false;
            for (TipoCalculoComisionEnum tipoCalculo : TipoCalculoComisionEnum.values()) {
                if (tipoCalculo.getValor().equals(tipoComision.getTipoCalculo())) {
                    tipoCalculoValido = true;
                    break;
                }
            }

            if (!tipoCalculoValido) {
                throw new CreateException("TipoComisión", "El tipo de cálculo no es válido");
            }

            tipoComision.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            tipoComision.setVersion(1L);

            return this.tiposComisionesRepository.save(tipoComision);
        } catch (Exception e) {
            log.error("Error al crear tipo de comisión: {}", e.getMessage());
            throw new CreateException("TipoComisión", "Error al crear tipo de comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        log.info("Eliminando lógicamente el tipo de comisión con ID: {}", id);
        try {
            TiposComisiones tipoComision = this.findById(id);
            tipoComision.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            tipoComision.setVersion(tipoComision.getVersion() + 1);

            this.tiposComisionesRepository.save(tipoComision);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar tipo de comisión: {}", e.getMessage());
            throw new DeleteException("TipoComisión", "Error al eliminar tipo de comisión: " + e.getMessage());
        }
    }
}
