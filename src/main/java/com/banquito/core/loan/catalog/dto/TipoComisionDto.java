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
public class TipoComisionDto {

    private String id;
    private String tipo;
    private String nombre;
    private String descripcion;
    private String tipoCalculo;
    private BigDecimal monto;
    private String estado;
    private BigDecimal version;
}
