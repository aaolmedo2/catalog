package com.banquito.core.loan.catalog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GarantiaDto {

    private String id;
    private String tipoGarantia;
    private String descripcion;
    private BigDecimal valor;
    private String estado;
    private BigDecimal version;
}
