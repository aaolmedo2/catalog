package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.dto.SeguroDto;
import com.banquito.core.loan.catalog.mapper.SeguroMapper;
import com.banquito.core.loan.catalog.model.Seguros;
import com.banquito.core.loan.catalog.repository.SegurosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SegurosService {

    private final SegurosRepository repository;
    private final SeguroMapper mapper;

    public SegurosService(SegurosRepository repository, SeguroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Obtener seguros activos - para mostrar catálogo de seguros disponibles
     */
    public List<SeguroDto> findActiveSeguros() {
        return repository.findByEstado("ACTIVO")
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener seguros por tipo - para filtrar por tipo específico
     */
    public List<SeguroDto> findByTipoAndEstado(String tipoSeguro, String estado) {
        return repository.findByTipoSeguroAndEstado(tipoSeguro, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener seguros vigentes - que no han expirado
     */
    public List<SeguroDto> findVigentes() {
        LocalDate today = LocalDate.now();
        return repository.findByFechaFinAfterAndEstado(today, "ACTIVO")
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener seguros por compañía aseguradora
     */
    public List<SeguroDto> findByCompaniaAndEstado(String compania, String estado) {
        return repository.findByCompaniaAndEstado(compania, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un seguro específico por ID
     */
    public Optional<SeguroDto> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    /**
     * Crear nuevo seguro
     */
    public SeguroDto create(SeguroDto dto) {
        Seguros entity = mapper.toEntity(dto);
        entity.setVersion(java.math.BigDecimal.ONE);

        Seguros saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Actualizar seguro existente
     */
    public SeguroDto update(String id, SeguroDto dto) {
        Optional<Seguros> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Seguro no encontrado con ID: " + id);
        }

        Seguros existing = existingOpt.get();
        Seguros updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setVersion(existing.getVersion().add(java.math.BigDecimal.ONE));

        Seguros saved = repository.save(updated);
        return mapper.toDto(saved);
    }

    /**
     * Cambiar estado de un seguro
     */
    public SeguroDto changeStatus(String id, String estado) {
        Optional<Seguros> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Seguro no encontrado con ID: " + id);
        }

        Seguros existing = existingOpt.get();
        existing.setEstado(estado);
        existing.setVersion(existing.getVersion().add(java.math.BigDecimal.ONE));

        Seguros saved = repository.save(existing);
        return mapper.toDto(saved);
    }
}
