package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.enums.EstadoGeneralEnum;
import com.banquito.core.loan.catalog.enums.TipoGarantiaEnum;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.model.Garantias;
import com.banquito.core.loan.catalog.repository.GarantiasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class GarantiasService {

    private final GarantiasRepository garantiasRepository;

    public GarantiasService(GarantiasRepository garantiasRepository) {
        this.garantiasRepository = garantiasRepository;
    }

    @Transactional(readOnly = true)
    public List<Garantias> findAll() {
        log.info("Obteniendo todas las garantías activas");
        return this.garantiasRepository.findByEstado(EstadoGeneralEnum.ACTIVO.getValor());
    }

    @Transactional(readOnly = true)
    public Garantias findById(String id) {
        log.info("Buscando garantía con ID: {}", id);
        return this.garantiasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garantía", "No se encontró la garantía con id: " + id));
    }

    @Transactional
    public Garantias create(Garantias garantia) {
        log.info("Creando nueva garantía: {}", garantia);
        try {
            // Validar si el tipo de garantía es válido
            boolean tipoValido = false;
            for (TipoGarantiaEnum tipo : TipoGarantiaEnum.values()) {
                if (tipo.getValor().equals(garantia.getTipoGarantia())) {
                    tipoValido = true;
                    break;
                }
            }

            if (!tipoValido) {
                throw new CreateException("Garantía", "El tipo de garantía no es válido");
            }

            garantia.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            garantia.setVersion(1L);

            return this.garantiasRepository.save(garantia);
        } catch (Exception e) {
            log.error("Error al crear garantía: {}", e.getMessage());
            throw new CreateException("Garantía", "Error al crear garantía: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        log.info("Eliminando lógicamente la garantía con ID: {}", id);
        try {
            Garantias garantia = this.findById(id);
            garantia.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            garantia.setVersion(garantia.getVersion() + 1);

            this.garantiasRepository.save(garantia);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar garantía: {}", e.getMessage());
            throw new DeleteException("Garantía", "Error al eliminar garantía: " + e.getMessage());
        }
    }
}
