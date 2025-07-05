package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.GarantiasDTO;
import com.banquito.core.loan.catalog.model.Garantias;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GarantiasMapper {

    public static GarantiasDTO mapToDTO(Garantias entity) {
        log.info("Mapeando entidad Garantias a DTO: {}", entity);
        return GarantiasDTO.builder()
                .id(entity.getId())
                .tipoGarantia(entity.getTipoGarantia())
                .descripcion(entity.getDescripcion())
                .valor(entity.getValor())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }

    public static Garantias mapToEntity(GarantiasDTO dto) {
        log.info("Mapeando DTO Garantias a entidad: {}", dto);
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
