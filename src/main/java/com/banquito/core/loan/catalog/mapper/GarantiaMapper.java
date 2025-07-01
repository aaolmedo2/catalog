package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.GarantiaDto;
import com.banquito.core.loan.catalog.model.Garantias;
import org.springframework.stereotype.Component;

@Component
public class GarantiaMapper {

    public GarantiaDto toDto(Garantias entity) {
        if (entity == null) {
            return null;
        }

        GarantiaDto dto = new GarantiaDto();
        dto.setId(entity.getId());
        dto.setTipoGarantia(entity.getTipoGarantia());
        dto.setDescripcion(entity.getDescripcion());
        dto.setValor(entity.getValor());
        dto.setEstado(entity.getEstado());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    public Garantias toEntity(GarantiaDto dto) {
        if (dto == null) {
            return null;
        }

        Garantias entity = new Garantias();
        entity.setId(dto.getId());
        entity.setTipoGarantia(dto.getTipoGarantia());
        entity.setDescripcion(dto.getDescripcion());
        entity.setValor(dto.getValor());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());

        return entity;
    }
}
