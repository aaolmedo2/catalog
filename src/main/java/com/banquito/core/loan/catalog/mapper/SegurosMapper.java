package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.SegurosDTO;
import com.banquito.core.loan.catalog.model.Seguros;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SegurosMapper {

    public static SegurosDTO mapToDTO(Seguros entity) {
        log.info("Mapeando entidad Seguros a DTO: {}", entity);
        return SegurosDTO.builder()
                .id(entity.getId())
                .tipoSeguro(entity.getTipoSeguro())
                .compania(entity.getCompania())
                .montoAsegurado(entity.getMontoAsegurado())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }

    public static Seguros mapToEntity(SegurosDTO dto) {
        log.info("Mapeando DTO Seguros a entidad: {}", dto);
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
