package com.banquito.core.loan.catalog.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GarantiasDTO {

    private String id;

    @NotEmpty(message = "Tipo de garantía es requerido")
    private String tipoGarantia;

    @NotEmpty(message = "Descripción es requerida")
    private String descripcion;

    @NotNull(message = "Valor es requerido")
    @Positive(message = "Valor debe ser mayor a cero")
    private BigDecimal valor;

    private String estado;
    private Long version;
}
