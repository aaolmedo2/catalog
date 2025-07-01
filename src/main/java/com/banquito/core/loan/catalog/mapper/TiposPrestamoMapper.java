package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.TiposPrestamoDto;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TiposPrestamoMapper {

    public TiposPrestamoDto toDto(TiposPrestamos entity) {
        if (entity == null) {
            return null;
        }

        TiposPrestamoDto dto = new TiposPrestamoDto();
        dto.setId(entity.getId());
        dto.setIdMoneda(entity.getIdMoneda());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setRequisitos(entity.getRequisitos());
        dto.setTipoCliente(entity.getTipoCliente());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaModificacion(entity.getFechaModificacion());
        dto.setEstado(entity.getEstado());
        dto.setVersion(entity.getVersion());
        dto.setEsquemaAmortizacion(entity.getEsquemaAmortizacion());
        dto.setIdGarantia(entity.getIdGarantia());

        return dto;
    }

    public TiposPrestamos toEntity(TiposPrestamoDto dto) {
        if (dto == null) {
            return null;
        }

        TiposPrestamos entity = new TiposPrestamos();
        entity.setId(dto.getId());
        entity.setIdMoneda(dto.getIdMoneda());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setRequisitos(dto.getRequisitos());
        entity.setTipoCliente(dto.getTipoCliente());
        entity.setFechaCreacion(dto.getFechaCreacion());
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());
        entity.setEsquemaAmortizacion(dto.getEsquemaAmortizacion());
        entity.setIdGarantia(dto.getIdGarantia());

        return entity;
    }
}
