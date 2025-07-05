package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.TiposComisionesDTO;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TiposComisionesMapper {

    public static TiposComisionesDTO mapToDTO(TiposComisiones entity) {
        log.info("Mapeando entidad TiposComisiones a DTO: {}", entity);
        return TiposComisionesDTO.builder()
                .id(entity.getId())
                .tipo(entity.getTipo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .tipoCalculo(entity.getTipoCalculo())
                .monto(entity.getMonto())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }

    public static TiposComisiones mapToEntity(TiposComisionesDTO dto) {
        log.info("Mapeando DTO TiposComisiones a entidad: {}", dto);
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
