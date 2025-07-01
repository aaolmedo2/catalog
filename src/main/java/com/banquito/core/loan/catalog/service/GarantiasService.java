package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.dto.GarantiaDto;
import com.banquito.core.loan.catalog.mapper.GarantiaMapper;
import com.banquito.core.loan.catalog.model.Garantias;
import com.banquito.core.loan.catalog.repository.GarantiasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarantiasService {

    private final GarantiasRepository repository;
    private final GarantiaMapper mapper;

    public GarantiasService(GarantiasRepository repository, GarantiaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Obtener garantías activas - para mostrar catálogo de garantías disponibles
     */
    public List<GarantiaDto> findActiveGarantias() {
        return repository.findByEstado("ACTIVO")
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener garantías por tipo - para filtrar por tipo específico
     */
    public List<GarantiaDto> findByTipoAndEstado(String tipoGarantia, String estado) {
        return repository.findByTipoGarantiaAndEstado(tipoGarantia, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener una garantía específica por ID
     */
    public Optional<GarantiaDto> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    /**
     * Crear nueva garantía
     */
    public GarantiaDto create(GarantiaDto dto) {
        Garantias entity = mapper.toEntity(dto);
        entity.setVersion(java.math.BigDecimal.ONE);

        Garantias saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Actualizar garantía existente
     */
    public GarantiaDto update(String id, GarantiaDto dto) {
        Optional<Garantias> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Garantía no encontrada con ID: " + id);
        }

        Garantias existing = existingOpt.get();
        Garantias updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setVersion(existing.getVersion().add(java.math.BigDecimal.ONE));

        Garantias saved = repository.save(updated);
        return mapper.toDto(saved);
    }

    /**
     * Cambiar estado de una garantía
     */
    public GarantiaDto changeStatus(String id, String estado) {
        Optional<Garantias> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Garantía no encontrada con ID: " + id);
        }

        Garantias existing = existingOpt.get();
        existing.setEstado(estado);
        existing.setVersion(existing.getVersion().add(java.math.BigDecimal.ONE));

        Garantias saved = repository.save(existing);
        return mapper.toDto(saved);
    }
}
