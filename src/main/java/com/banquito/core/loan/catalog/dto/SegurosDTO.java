package com.banquito.core.loan.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SegurosDTO {

    private String id;

    @NotEmpty(message = "Tipo de seguro es requerido")
    private String tipoSeguro;

    @NotEmpty(message = "Compañía es requerida")
    private String compania;

    @NotNull(message = "Monto asegurado es requerido")
    @Positive(message = "Monto asegurado debe ser mayor a cero")
    private BigDecimal montoAsegurado;

    @NotNull(message = "Fecha de inicio es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "Fecha de fin es requerida")
    private LocalDate fechaFin;

    private String estado;
    private Long version;
}
