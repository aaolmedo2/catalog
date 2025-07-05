package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.enums.EstadoGeneralEnum;
import com.banquito.core.loan.catalog.enums.TipoSeguroEnum;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.model.Seguros;
import com.banquito.core.loan.catalog.repository.SegurosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SegurosService {

    private final SegurosRepository segurosRepository;

    public SegurosService(SegurosRepository segurosRepository) {
        this.segurosRepository = segurosRepository;
    }

    @Transactional(readOnly = true)
    public List<Seguros> findAll() {
        log.info("Obteniendo todos los seguros activos");
        return this.segurosRepository.findByEstado(EstadoGeneralEnum.ACTIVO.getValor());
    }

    @Transactional(readOnly = true)
    public Seguros findById(String id) {
        log.info("Buscando seguro con ID: {}", id);
        return this.segurosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seguro", "No se encontró el seguro con id: " + id));
    }

    @Transactional
    public Seguros create(Seguros seguro) {
        log.info("Creando nuevo seguro: {}", seguro);
        try {
            // Validar si el tipo de seguro es válido
            boolean tipoValido = false;
            for (TipoSeguroEnum tipo : TipoSeguroEnum.values()) {
                if (tipo.getValor().equals(seguro.getTipoSeguro())) {
                    tipoValido = true;
                    break;
                }
            }

            if (!tipoValido) {
                throw new CreateException("Seguro", "El tipo de seguro no es válido");
            }

            seguro.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            seguro.setVersion(1L);

            return this.segurosRepository.save(seguro);
        } catch (Exception e) {
            log.error("Error al crear seguro: {}", e.getMessage());
            throw new CreateException("Seguro", "Error al crear seguro: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        log.info("Eliminando lógicamente el seguro con ID: {}", id);
        try {
            Seguros seguro = this.findById(id);
            seguro.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            seguro.setVersion(seguro.getVersion() + 1);

            this.segurosRepository.save(seguro);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar seguro: {}", e.getMessage());
            throw new DeleteException("Seguro", "Error al eliminar seguro: " + e.getMessage());
        }
    }
}
