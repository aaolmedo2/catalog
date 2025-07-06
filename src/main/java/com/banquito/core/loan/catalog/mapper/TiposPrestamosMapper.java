package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.TiposPrestamosDTO;
import com.banquito.core.loan.catalog.model.Garantias;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TiposPrestamosMapper {

    public static TiposPrestamosDTO mapToDTO(TiposPrestamos entity) {
        log.info("Mapeando entidad TiposPrestamos a DTO: {}", entity);
        return TiposPrestamosDTO.builder()
                .id(entity.getId())
                .idMoneda(entity.getIdMoneda())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .requisitos(entity.getRequisitos())
                .tipoCliente(entity.getTipoCliente())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaModificacion(entity.getFechaModificacion())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .esquemaAmortizacion(entity.getEsquemaAmortizacion())
                .idGarantia(entity.getIdGarantia())
                .build();
    }

    public static TiposPrestamosDTO mapToDTOWithGarantia(TiposPrestamos entity, Garantias garantia) {
        log.info("Mapeando entidad TiposPrestamos a DTO con garantía incluida: {}", entity);
        TiposPrestamosDTO dto = mapToDTO(entity);
        dto.setGarantia(GarantiasMapper.mapToDTO(garantia));
        return dto;
    }

    public static TiposPrestamos mapToEntity(TiposPrestamosDTO dto) {
        log.info("Mapeando DTO TiposPrestamos a entidad: {}", dto);
        TiposPrestamos entity = new TiposPrestamos();
        entity.setId(dto.getId());
        entity.setIdMoneda(dto.getIdMoneda());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setRequisitos(dto.getRequisitos());
        entity.setTipoCliente(dto.getTipoCliente());
        entity.setFechaCreacion(dto.getFechaCreacion());
        entity.setFechaModificacion(dto.getFechaModificacion());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());
        entity.setEsquemaAmortizacion(dto.getEsquemaAmortizacion());
        entity.setIdGarantia(dto.getIdGarantia());
        return entity;
    }
}
