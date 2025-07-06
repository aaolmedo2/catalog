package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.PrestamosDTO;
import com.banquito.core.loan.catalog.model.Prestamos;
import com.banquito.core.loan.catalog.model.Seguros;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrestamosMapper {

    public static PrestamosDTO mapToDTO(Prestamos entity) {
        log.info("Mapeando entidad Prestamos a DTO: {}", entity);
        return PrestamosDTO.builder()
                .id(entity.getId())
                .idTipoPrestamo(entity.getIdTipoPrestamo())
                .idMoneda(entity.getIdMoneda())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .fechaModificacion(entity.getFechaModificacion())
                .baseCalculo(entity.getBaseCalculo())
                .tasaInteres(entity.getTasaInteres())
                .montoMinimo(entity.getMontoMinimo())
                .montoMaximo(entity.getMontoMaximo())
                .plazoMinimoMeses(entity.getPlazoMinimoMeses())
                .plazoMaximoMeses(entity.getPlazoMaximoMeses())
                .tipoAmortizacion(entity.getTipoAmortizacion())
                .idSeguro(entity.getIdSeguro())
                .idTipoComision(entity.getIdTipoComision())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }

    public static PrestamosDTO mapToDTOWithRelations(
            Prestamos entity,
            TiposPrestamos tipoPrestamo,
            Seguros seguro,
            TiposComisiones tipoComision) {
        log.info("Mapeando entidad Prestamos a DTO con relaciones incluidas: {}", entity);
        PrestamosDTO dto = mapToDTO(entity);

        log.info("Datos relacionados - tipoPrestamo: {}, seguro: {}, tipoComision: {}",
                tipoPrestamo != null ? tipoPrestamo.getId() : "null",
                seguro != null ? seguro.getId() : "null",
                tipoComision != null ? tipoComision.getId() : "null");

        // Mapear tipo de préstamo con garantía si está disponible
        if (tipoPrestamo != null) {
            try {
                dto.setTipoPrestamo(TiposPrestamosMapper.mapToDTO(tipoPrestamo));
                log.info("TipoPrestamo mapeado correctamente: {}", dto.getTipoPrestamo().getId());
            } catch (Exception e) {
                log.error("Error al mapear TipoPrestamo: {}", e.getMessage(), e);
            }
        }

        // Mapear seguro
        if (seguro != null) {
            try {
                dto.setSeguro(SegurosMapper.mapToDTO(seguro));
                log.info("Seguro mapeado correctamente: {}", dto.getSeguro().getId());
            } catch (Exception e) {
                log.error("Error al mapear Seguro: {}", e.getMessage(), e);
            }
        }

        // Mapear tipo de comisión
        if (tipoComision != null) {
            try {
                dto.setTipoComision(TiposComisionesMapper.mapToDTO(tipoComision));
                log.info("TipoComision mapeado correctamente: {}", dto.getTipoComision().getId());
            } catch (Exception e) {
                log.error("Error al mapear TipoComision: {}", e.getMessage(), e);
            }
        }

        return dto;
    }

    public static Prestamos mapToEntity(PrestamosDTO dto) {
        log.info("Mapeando DTO Prestamos a entidad: {}", dto);
        Prestamos entity = new Prestamos();
        entity.setId(dto.getId());
        entity.setIdTipoPrestamo(dto.getIdTipoPrestamo());
        entity.setIdMoneda(dto.getIdMoneda());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFechaModificacion(dto.getFechaModificacion());
        entity.setBaseCalculo(dto.getBaseCalculo());
        entity.setTasaInteres(dto.getTasaInteres());
        entity.setMontoMinimo(dto.getMontoMinimo());
        entity.setMontoMaximo(dto.getMontoMaximo());
        entity.setPlazoMinimoMeses(dto.getPlazoMinimoMeses());
        entity.setPlazoMaximoMeses(dto.getPlazoMaximoMeses());
        entity.setTipoAmortizacion(dto.getTipoAmortizacion());
        entity.setIdSeguro(dto.getIdSeguro());
        entity.setIdTipoComision(dto.getIdTipoComision());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());
        return entity;
    }
}
