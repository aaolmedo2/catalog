package com.banquito.core.loan.catalog.service;

import com.banquito.core.loan.catalog.dto.PrestamoDto;
import com.banquito.core.loan.catalog.mapper.PrestamoMapper;
import com.banquito.core.loan.catalog.model.Prestamos;
import com.banquito.core.loan.catalog.repository.PrestamosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamosService {

    private final PrestamosRepository repository;
    private final PrestamoMapper mapper;

    public PrestamosService(PrestamosRepository repository, PrestamoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Obtener préstamos por tipo de préstamo y estado - método real para mostrar
     * productos disponibles
     */
    public List<PrestamoDto> findByTipoPrestamoAndEstado(Integer idTipoPrestamo, String estado) {
        return repository.findByIdTipoPrestamoAndEstado(idTipoPrestamo, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener préstamos activos - para mostrar catálogo general
     */
    public List<PrestamoDto> findActiveLoans() {
        return repository.findByEstado("ACTIVO")
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener préstamos por moneda - para filtrar por divisa
     */
    public List<PrestamoDto> findByMonedaAndEstado(String idMoneda, String estado) {
        return repository.findByIdMonedaAndEstado(idMoneda, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Buscar préstamos por monto disponible - para evaluar eligibilidad
     */
    public List<PrestamoDto> findByMontoRange(BigDecimal monto, String estado) {
        return repository.findByMontoRangeAndEstado(monto, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Buscar préstamos por plazo disponible - para evaluar opciones de plazo
     */
    public List<PrestamoDto> findByPlazoRange(Integer plazoMeses, String estado) {
        return repository.findByPlazoRangeAndEstado(plazoMeses, estado)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un préstamo específico por ID
     */
    public Optional<PrestamoDto> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    /**
     * Crear nuevo préstamo
     */
    public PrestamoDto create(PrestamoDto dto) {
        Prestamos entity = mapper.toEntity(dto);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setVersion(1L);

        Prestamos saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Actualizar préstamo existente
     */
    public PrestamoDto update(String id, PrestamoDto dto) {
        Optional<Prestamos> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Préstamo no encontrado con ID: " + id);
        }

        Prestamos existing = existingOpt.get();
        Prestamos updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setFechaModificacion(LocalDateTime.now());
        updated.setVersion(existing.getVersion() + 1);

        Prestamos saved = repository.save(updated);
        return mapper.toDto(saved);
    }

    /**
     * Cambiar estado de un préstamo
     */
    public PrestamoDto changeStatus(String id, String estado) {
        Optional<Prestamos> existingOpt = repository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Préstamo no encontrado con ID: " + id);
        }

        Prestamos existing = existingOpt.get();
        existing.setEstado(estado);
        existing.setFechaModificacion(LocalDateTime.now());
        existing.setVersion(existing.getVersion() + 1);

        Prestamos saved = repository.save(existing);
        return mapper.toDto(saved);
    }
}
