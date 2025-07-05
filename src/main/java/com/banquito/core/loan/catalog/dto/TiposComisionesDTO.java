package com.banquito.core.loan.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TiposComisionesDTO {

    private String id;

    @NotEmpty(message = "Tipo es requerido")
    private String tipo;

    @NotEmpty(message = "Nombre es requerido")
    private String nombre;

    @NotEmpty(message = "Descripción es requerida")
    private String descripcion;

    @NotEmpty(message = "Tipo de cálculo es requerido")
    private String tipoCalculo;

    @NotNull(message = "Monto es requerido")
    @Positive(message = "Monto debe ser mayor a cero")
    private BigDecimal monto;

    private String estado;
    private Long version;
}
