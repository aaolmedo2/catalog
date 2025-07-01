package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.dto.TipoComisionDto;
import com.banquito.core.loan.catalog.mapper.TipoComisionMapper;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import com.banquito.core.loan.catalog.repository.TiposComisionesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TiposComisionesService {

    private final TiposComisionesRepository repository;
    private final TipoComisionMapper mapper;

    public TiposComisionesService(TiposComisionesRepository repository, TipoComisionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Obtener tipos de comisión activos - para mostrar catálogo de comisiones
     * disponibles
     */
    public List<TipoComisionDto> findActiveComisiones() {
        return repository.findByEstado("ACTIVO")
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener tipos de comisión por tipo - para filtrar por tipo específico
     */
    public List<TipoComisionDto> findByTipoAndEstado(String tipo, String estado) {
        return repository.findByTipoAndEstado(tipo, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener tipos de comisión por método de cálculo
     */
    public List<TipoComisionDto> findByTipoCalculoAndEstado(String tipoCalculo, String estado) {
        return repository.findByTipoCalculoAndEstado(tipoCalculo, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un tipo de comisión específico por ID
     */
    public Optional<TipoComisionDto> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    /**
     * Crear nuevo tipo de comisión
     */
    public TipoComisionDto create(TipoComisionDto dto) {
        TiposComisiones entity = mapper.toEntity(dto);
        entity.setVersion(java.math.BigDecimal.ONE);

        TiposComisiones saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Actualizar tipo de comisión existente
     */
    public TipoComisionDto update(String id, TipoComisionDto dto) {
        Optional<TiposComisiones> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Tipo de comisión no encontrado con ID: " + id);
        }

        TiposComisiones existing = existingOpt.get();
        TiposComisiones updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setVersion(existing.getVersion().add(java.math.BigDecimal.ONE));

        TiposComisiones saved = repository.save(updated);
        return mapper.toDto(saved);
    }

    /**
     * Cambiar estado de un tipo de comisión
     */
    public TipoComisionDto changeStatus(String id, String estado) {
        Optional<TiposComisiones> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Tipo de comisión no encontrado con ID: " + id);
        }

        TiposComisiones existing = existingOpt.get();
        existing.setEstado(estado);
        existing.setVersion(existing.getVersion().add(java.math.BigDecimal.ONE));

        TiposComisiones saved = repository.save(existing);
        return mapper.toDto(saved);
    }
}
