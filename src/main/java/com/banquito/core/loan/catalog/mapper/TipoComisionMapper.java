package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.TipoComisionDto;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import org.springframework.stereotype.Component;

@Component
public class TipoComisionMapper {

    public TipoComisionDto toDto(TiposComisiones entity) {
        if (entity == null) {
            return null;
        }

        TipoComisionDto dto = new TipoComisionDto();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setTipoCalculo(entity.getTipoCalculo());
        dto.setMonto(entity.getMonto());
        dto.setEstado(entity.getEstado());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    public TiposComisiones toEntity(TipoComisionDto dto) {
        if (dto == null) {
            return null;
        }

        TiposComisiones entity = new TiposComisiones();
        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setTipoCalculo(dto.getTipoCalculo());
        entity.setMonto(dto.getMonto());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());

        return entity;
    }
}
