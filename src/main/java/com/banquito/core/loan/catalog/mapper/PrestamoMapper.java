package com.banquito.core.loan.catalog.mapper;

import com.banquito.core.loan.catalog.dto.PrestamoDto;
import com.banquito.core.loan.catalog.model.Prestamos;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PrestamoMapper {

    public PrestamoDto toDto(Prestamos entity) {
        if (entity == null) {
            return null;
        }

        PrestamoDto dto = new PrestamoDto();
        dto.setId(entity.getId());
        dto.setIdTipoPrestamo(entity.getIdTipoPrestamo());
        dto.setIdMoneda(entity.getIdMoneda());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaModificacion(entity.getFechaModificacion());
        dto.setBaseCalculo(entity.getBaseCalculo());
        dto.setTasaInteres(entity.getTasaInteres());
        dto.setMontoMinimo(entity.getMontoMinimo());
        dto.setMontoMaximo(entity.getMontoMaximo());
        dto.setPlazoMinimoMeses(entity.getPlazoMinimoMeses());
        dto.setPlazoMaximoMeses(entity.getPlazoMaximoMeses());
        dto.setTipoAmortizacion(entity.getTipoAmortizacion());
        dto.setIdSeguro(entity.getIdSeguro());
        dto.setIdTipoComision(entity.getIdTipoComision());
        dto.setTipoPrestamo(entity.getTipoPrestamo());
        dto.setEstado(entity.getEstado());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    public Prestamos toEntity(PrestamoDto dto) {
        if (dto == null) {
            return null;
        }

        Prestamos entity = new Prestamos();
        entity.setId(dto.getId());
        entity.setIdTipoPrestamo(dto.getIdTipoPrestamo());
        entity.setIdMoneda(dto.getIdMoneda());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setBaseCalculo(dto.getBaseCalculo());
        entity.setTasaInteres(dto.getTasaInteres());
        entity.setMontoMinimo(dto.getMontoMinimo());
        entity.setMontoMaximo(dto.getMontoMaximo());
        entity.setPlazoMinimoMeses(dto.getPlazoMinimoMeses());
        entity.setPlazoMaximoMeses(dto.getPlazoMaximoMeses());
        entity.setTipoAmortizacion(dto.getTipoAmortizacion());
        entity.setIdSeguro(dto.getIdSeguro());
        entity.setIdTipoComision(dto.getIdTipoComision());
        entity.setTipoPrestamo(dto.getTipoPrestamo());
        entity.setEstado(dto.getEstado());
        entity.setVersion(dto.getVersion());

        return entity;
    }
}
