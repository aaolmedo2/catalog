package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.SeguroDto;
import com.banquito.core.loan.catalog.model.Seguros;
import org.springframework.stereotype.Component;

@Component
public class SeguroMapper {

    public SeguroDto toDto(Seguros entity) {
        if (entity == null) {
            return null;
        }

        SeguroDto dto = new SeguroDto();
        dto.setId(entity.getId());
        dto.setTipoSeguro(entity.getTipoSeguro());
        dto.setCompania(entity.getCompania());
        dto.setMontoAsegurado(entity.getMontoAsegurado());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setEstado(entity.getEstado());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    public Seguros toEntity(SeguroDto dto) {
        if (dto == null) {
            return null;
        }

        Seguros entity = new Seguros();
        entity.setId(dto.getId());
        entity.setTipoSeguro(dto.getTipoSeguro());
        entity.setCompania(dto.getCompania());
        entity.setMontoAsegurado(dto.getMontoAsegurado());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());

        return entity;
    }
}
