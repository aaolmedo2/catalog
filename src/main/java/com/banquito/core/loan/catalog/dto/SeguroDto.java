package com.banquito.core.loan.catalog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SeguroDto {

    private String id;
    private String tipoSeguro;
    private String compania;
    private BigDecimal montoAsegurado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private BigDecimal version;
}
