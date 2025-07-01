package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.dto.TiposPrestamoDto;
import com.banquito.core.loan.catalog.mapper.TiposPrestamoMapper;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import com.banquito.core.loan.catalog.repository.TiposPrestamosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TiposPrestamosService {

    private final TiposPrestamosRepository repository;
    private final TiposPrestamoMapper mapper;

    public TiposPrestamosService(TiposPrestamosRepository repository, TiposPrestamoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Obtener tipos de préstamo activos - método real para mostrar catálogo al
     * cliente
     */
    public List<TiposPrestamoDto> findByEstado(String estado) {
        return repository.findByEstado(estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener tipos de préstamo por tipo de cliente - método real para filtrar por
     * perfil
     */
    public List<TiposPrestamoDto> findByTipoCliente(String tipoCliente) {
        return repository.findByTipoCliente(tipoCliente)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener tipos de préstamo activos por tipo de cliente - caso más común
     */
    public List<TiposPrestamoDto> findByTipoClienteAndEstado(String tipoCliente, String estado) {
        return repository.findByTipoClienteAndEstado(tipoCliente, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un tipo de préstamo específico por ID
     */
    public Optional<TiposPrestamoDto> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    /**
     * Crear nuevo tipo de préstamo
     */
    public TiposPrestamoDto create(TiposPrestamoDto dto) {
        TiposPrestamos entity = mapper.toEntity(dto);
        entity.setFechaCreacion(LocalDateTime.now());
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setVersion(1L);

        TiposPrestamos saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Actualizar tipo de préstamo existente
     */
    public TiposPrestamoDto update(String id, TiposPrestamoDto dto) {
        Optional<TiposPrestamos> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Tipo de préstamo no encontrado con ID: " + id);
        }

        TiposPrestamos existing = existingOpt.get();
        TiposPrestamos updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setFechaCreacion(existing.getFechaCreacion());
        updated.setFechaModificacion(LocalDateTime.now());
        updated.setVersion(existing.getVersion() + 1);

        TiposPrestamos saved = repository.save(updated);
        return mapper.toDto(saved);
    }

    /**
     * Cambiar estado de un tipo de préstamo (activar/desactivar)
     */
    public TiposPrestamoDto changeStatus(String id, String estado) {
        Optional<TiposPrestamos> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Tipo de préstamo no encontrado con ID: " + id);
        }

        TiposPrestamos existing = existingOpt.get();
        existing.setEstado(estado);
        existing.setFechaModificacion(LocalDateTime.now());
        existing.setVersion(existing.getVersion() + 1);

        TiposPrestamos saved = repository.save(existing);
        return mapper.toDto(saved);
    }
}
